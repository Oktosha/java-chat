package ru.mail.track.kolodzey.Server;
import java.time.Instant;
/**
 * Created by DKolodzey on 14.12.15.
 */
public class Message {
    public Long id;
    public String message;
    public Instant timestamp;
    public Long chatId;
    public Long senderId;
}
