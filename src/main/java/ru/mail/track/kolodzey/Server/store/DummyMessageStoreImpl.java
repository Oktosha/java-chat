package ru.mail.track.kolodzey.Server.store;

import java.time.Instant;
import java.util.*;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class DummyMessageStoreImpl implements MessageStore {

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
        messages.add(message);
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
            try {
                dialogs.put(new UserPair(participants), chat.id);
            } catch (UserPair.InvalidNumberOfUsers e) {
                System.err.println(e.getMessage());
            }
        }
        return chat;
    }

    @Override
    public Chat getDialogByParticipants(Set<Integer> participantIds) {
        try {
            UserPair pair = new UserPair(participantIds);
            if (dialogs.containsKey(pair)) {
                return chats.get(dialogs.get(pair));
            }
        } catch (UserPair.InvalidNumberOfUsers e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
