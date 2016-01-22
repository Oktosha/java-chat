package ru.mail.track.kolodzey.Server.store;
import java.time.Instant;
/**
 * Created by DKolodzey on 14.12.15.
 */
public class Message {
    public Message() {
        this.id = null;
        this.text = null;
        this.timestamp = null;
        this.chatId = null;
        this.senderId = null;
    }
    public Message(Integer id, String text, Instant timestamp, Integer chatId, Integer senderId) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.chatId = chatId;
        this.senderId = senderId;
    }
    public Integer id;
    public String text;
    public Instant timestamp;
    public Integer chatId;
    public Integer senderId;
}
