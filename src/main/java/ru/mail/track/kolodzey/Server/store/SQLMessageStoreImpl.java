package ru.mail.track.kolodzey.Server.store;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
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
        return null;
    }

    @Override
    public Message getMessageById(Integer id) {
        try (Connection c = dataSource.getConnection()) {
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM messages WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Message message = new Message();
                message.senderId = resultSet.getInt("senderID");
                message.chatId = resultSet.getInt("chatID");
                message.text = resultSet.getString("text");
                message.timestamp = resultSet.getTimestamp("time").toInstant();
                message.id = id;
                return message;
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.toString());
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
        return null;
    }

    @Override
    public Chat getDialogByParticipants(Set<Integer> participantIds) {
        return null;
    }
}
