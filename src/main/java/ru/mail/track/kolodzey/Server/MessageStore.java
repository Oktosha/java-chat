package ru.mail.track.kolodzey.Server;

import java.util.Set;

/**
 * Created by DKolodzey on 14.12.15.
 */
public interface MessageStore {
    Chat getChatById(Long id);
    Message getMessageById(Long id);
    Message createMessage(Long chatId, Long senderId, String text);
    Chat createChat(Set<User> participants);
}
