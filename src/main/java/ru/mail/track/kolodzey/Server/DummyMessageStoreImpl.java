package ru.mail.track.kolodzey.Server;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class DummyMessageStoreImpl implements MessageStore {
    private List<Chat> chats = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

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
        Chat chat = new Chat(chats.size(), participants);
        chats.add(chat);
        return chat;
    }
}
