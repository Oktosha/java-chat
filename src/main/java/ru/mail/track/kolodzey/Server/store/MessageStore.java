package ru.mail.track.kolodzey.Server.store;

import java.time.Instant;
import java.util.Set;

/**
 * Created by DKolodzey on 14.12.15.
 */
public interface MessageStore {
    Chat getChatById(Integer id);
    Message getMessageById(Integer id);
    Message createMessage(String text, Instant timestamp, Integer chatId, Integer senderId);
    Chat createChat(Set<Integer> participants);
    Chat getDialogByParticipants(Set<Integer> participantIds);
}
