package ru.mail.track.kolodzey.Server.store;

import java.time.Instant;
import java.util.*;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class DummyMessageStoreImpl implements MessageStore {

    public static class UserPair {
        public Integer userId1;
        public Integer userId2;
        public int hashCode() {
            return userId1 + userId2;
        }
        public UserPair(Set<Integer> participants) {
            Integer[] participantArray = new Integer[2];
            participants.toArray(participantArray);
            this.userId1 = participantArray[0];
            this.userId2 = participantArray[1] != null ? participantArray[1] : participantArray[0];
        }

        public UserPair(Integer userId1, Integer userId2) {
            this.userId1 = userId1;
            this.userId2 = userId2;
        }

        public boolean equals(UserPair other) {
            return ((userId1 == other.userId1) && (userId2 == other.userId2))
                    || ((userId2 == other.userId1) && (userId1 == other.userId2));
        }
    }

    private List<Chat> chats = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private Map<UserPair, Integer> dialogs = new HashMap<>();

    @Override
    public Chat getChatById(Integer id) {
        if (id >= chats.size())
            return null;
        return chats.get(id);
    }

    @Override
    public Message getMessageById(Integer id) {
        if (id >= messages.size())
            return null;
        return messages.get(id);
    }

    @Override
    public Message createMessage(String text, Instant timestamp, Integer chatId, Integer senderId) {
        Message message = new Message(messages.size(), text, timestamp, chatId, senderId);
        if (getChatById(chatId) == null)
            return null;
        chats.get(chatId).messages.add(message.id);
        return message;
    }

    @Override
    public Chat createChat(Set<Integer> participants) {
        if (participants.size() < 1) {
            return null;
        }
        Chat chat = new Chat(chats.size(), participants);
        chats.add(chat);
        if (participants.size() <= 2) {
            dialogs.put(new UserPair(participants), chat.id);
        }
        return chat;
    }

    @Override
    public Chat getDialogByParticipants(Set<Integer> participantIds) {
        UserPair pair = new UserPair(participantIds);
        if (dialogs.containsKey(pair)) {
            return chats.get(dialogs.get(pair));
        }
        return null;
    }
}
