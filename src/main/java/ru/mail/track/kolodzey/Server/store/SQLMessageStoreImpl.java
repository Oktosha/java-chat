package ru.mail.track.kolodzey.Server.store;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by DKolodzey on 21.01.16.
 */
public class SQLMessageStoreImpl implements MessageStore {
    private DataSource dataSource;

    public SQLMessageStoreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Chat getChatById(Integer id) {
        Chat chat = new Chat();
        chat.id = id;
        try (Connection c = dataSource.getConnection()) {
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM chats WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String chatType = resultSet.getString("type");
                if (chatType.trim().toLowerCase().equals("dialog")) {
                    stmt = c.prepareStatement("SELECT * FROM dialogs WHERE chatID = ?;");
                    stmt.setInt(1, id);
                    resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        chat.participants.add(resultSet.getInt("user1"));
                        chat.participants.add(resultSet.getInt("user2"));
                    } else {
                        return null;
                    }
                } else {
                    stmt = c.prepareStatement("SELECT * FROM polylogs WHERE chatID = ?;");
                    stmt.setInt(1, id);
                    resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        chat.participants.add(resultSet.getInt("userID"));
                    }
                }
                stmt = c.prepareStatement("SELECT * FROM messages WHERE chatID = ?;");
                stmt.setInt(1, id);
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    chat.messages.add(resultSet.getInt("id"));
                }
                return chat;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    private Message unpackMessage(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.senderId = resultSet.getInt("senderID");
        message.chatId = resultSet.getInt("chatID");
        message.text = resultSet.getString("text");
        message.timestamp = resultSet.getTimestamp("time").toInstant();
        message.id = resultSet.getInt("id");
        return message;
    }

    @Override
    public Message getMessageById(Integer id) {
        try (Connection c = dataSource.getConnection()) {
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM messages WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return unpackMessage(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Message createMessage(String text, Instant timestamp, Integer chatId, Integer senderId) {
        try (Connection c = dataSource.getConnection()) {
            String sqlInsert = "INSERT INTO messages (text, time, chatId, senderId) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, text);
            stmt.setTimestamp(2, Timestamp.from(timestamp));
            stmt.setInt(3, chatId);
            stmt.setInt(4, senderId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating message failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return getMessageById(id);
                }
                else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Chat createChat(Set<Integer> participants) {
        try (Connection c = dataSource.getConnection()) {
            String sql = "INSERT INTO chats (type) VALUES (?)";
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (participants.size() > 2) {
                stmt.setString(1, "polylog");
            } else {
                stmt.setString(1, "dialog");
            }
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating chat failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    if (participants.size() > 2) {
                        sql = "INSERT INTO polylogs (chatID, userID) VALUES (?, ?)";
                        stmt = c.prepareStatement(sql);
                        for (Integer userId : participants) {
                            stmt.setInt(1, id);
                            stmt.setInt(2, userId);
                            stmt.execute();
                        }
                    } else {
                        UserPair userPair = new UserPair(participants);
                        sql = "INSERT INTO dialogs (chatID, user1, user2) VALUES (?, ?, ?)";
                        stmt = c.prepareStatement(sql);
                        stmt.setInt(1, id);
                        stmt.setInt(2, userPair.userId1);
                        stmt.setInt(3, userPair.userId2);
                        stmt.execute();
                    }
                    return getChatById(id);
                }
                else {
                    throw new SQLException("Creating chat failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to createChat because\n" + e.getMessage());
        }
        return null;
    }

    @Override
    public Chat getDialogByParticipants(Set<Integer> participantIds) {
        try (Connection c = dataSource.getConnection()) {
            UserPair userPair = new UserPair(participantIds);
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM dialogs WHERE user1 = ? AND user2 = ?");
            stmt.setInt(1, userPair.userId1);
            stmt.setInt(2, userPair.userId2);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return getChatById(resultSet.getInt("chatID"));
            }
        } catch (Exception e) {
            System.err.println("Failed to getDialogByParticipants because\n" + e.getMessage());
        }
        return null;
    }
}
