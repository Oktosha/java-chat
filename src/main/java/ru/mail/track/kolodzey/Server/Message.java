package ru.mail.track.kolodzey.Server;
import java.time.Instant;
/**
 * Created by DKolodzey on 14.12.15.
 */
public class Message {
    public Message(Integer id, String text, Instant timestamp, Integer chatId, Integer senderId) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.chatId = chatId;
        this.senderId = senderId;
    }
    public final Integer id;
    public final String text;
    public final Instant timestamp;
    public final Integer chatId;
    public final Integer senderId;
}
