package ru.mail.track.kolodzey.Server.store;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                message.timestamp = resultSet.getTime("time").toInstant();
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
