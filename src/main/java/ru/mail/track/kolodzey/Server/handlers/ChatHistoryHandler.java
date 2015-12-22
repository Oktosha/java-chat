package ru.mail.track.kolodzey.Server.handlers;

import ru.mail.track.kolodzey.NetData.ChatHistoryNetData;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.NetDataHandler;
import ru.mail.track.kolodzey.Server.Context;
import ru.mail.track.kolodzey.Server.store.Chat;
import ru.mail.track.kolodzey.Server.store.Message;
import ru.mail.track.kolodzey.Server.store.User;
import ru.mail.track.kolodzey.Server.store.UserStore;

/**
 * Created by DKolodzey on 22.12.15.
 */
public class ChatHistoryHandler extends NetDataHandler {
    private Context context;
    public ChatHistoryHandler(Context context) {
        this.context = context;
    }

    @Override
    public NetData handle(NetData data) {
        if (context.user == null) {
            return new NotifyNetData("You should login before looking at chat history", NetData.Sender.SERVER);
        }
        ChatHistoryNetData castedData = (ChatHistoryNetData) data;
        Chat chat = context.messageStore.getChatById(castedData.chatID);
        if (chat == null) {
            return new NotifyNetData("Requested chat " + castedData.chatID + " doesn't exist", NetData.Sender.SERVER);
        }
        if (!chat.participants.contains(context.user.id)) {
            return new NotifyNetData("You can't see history of the chat " + chat.id + "\n"
                    + "You are not its participant", NetData.Sender.SERVER);
        }
        String message = convertChatToString(chat);
        return new NotifyNetData(message, NetData.Sender.SERVER);
    }

    private String convertChatToString(Chat chat) {
        StringBuilder builder = new StringBuilder("History of chat " + chat.id
                + " whose participants are " + chat.participants + "\n");
        for (Integer messageId : chat.messages) {
            Message message = context.messageStore.getMessageById(messageId);
            User author = context.userStore.getUserById(message.senderId);
            builder.append(author.id + " aka " + author.login + " [" + message.timestamp + "]: " + message.text + "\n");
        }
        return builder.toString();
    }

}
