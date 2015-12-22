package ru.mail.track.kolodzey.Server.handlers;

import ru.mail.track.kolodzey.NetData.ChatSendNetData;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.NetDataHandler;
import ru.mail.track.kolodzey.Server.Context;
import ru.mail.track.kolodzey.Server.store.Chat;

/**
 * Created by DKolodzey on 22.12.15.
 */
public class ChatSendHandler extends NetDataHandler {
    private Context context;
    public ChatSendHandler(Context context) {
        this.context = context;
    }
    @Override
    public NetData handle(NetData data) {
        if (context.user == null) {
            return new NotifyNetData("You should login before sending messages", NetData.Sender.SERVER);
        }
        ChatSendNetData castedData = (ChatSendNetData) data;
        Chat chat = context.messageStore.getChatById(castedData.chatID);
        if (chat == null) {
            return new NotifyNetData("Message not send.\n"
                    + "Chat with id " + castedData.chatID + "doesn't exist", NetData.Sender.SERVER);
        }
        context.messageStore.createMessage(castedData.message, castedData.timestamp,
                castedData.chatID, context.user.id);
        return new NotifyNetData("Message sent successfully", NetData.Sender.SERVER);
    }
}
