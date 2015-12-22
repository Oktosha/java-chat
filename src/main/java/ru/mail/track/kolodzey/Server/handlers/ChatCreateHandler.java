package ru.mail.track.kolodzey.Server.handlers;

import ru.mail.track.kolodzey.NetData.ChatCreateNetData;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.NetDataHandler;
import ru.mail.track.kolodzey.Server.Context;
import ru.mail.track.kolodzey.Server.store.Chat;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DKolodzey on 22.12.15.
 */
public class ChatCreateHandler extends NetDataHandler {
    private Context context;
    public ChatCreateHandler(Context context) {
        this.context = context;
    }
    @Override
    public NetData handle(NetData data) {
        if (context.user == null) {
            return new NotifyNetData("You should login before creating chats", NetData.Sender.SERVER);
        }
        ChatCreateNetData chatData = (ChatCreateNetData) data;
        Set<Integer> participantsSet = new HashSet<>(chatData.participantIds);
        if (participantsSet.size() < 1) {
            return new NotifyNetData("To few participants to create chat", NetData.Sender.SERVER);
        }
        participantsSet.add(context.user.id);

        Chat chat;

        if (participantsSet.size() <= 2) {
            chat = context.messageStore.getDialogByParticipants(participantsSet);
            if (chat != null) {
                return new NotifyNetData("Chat isn't created because "
                        + "dialogs which consist of 2 or less users aren't recreated\n"
                        + "the existing dialog between " + participantsSet.toString()
                        + " has id " + chat.id, NetData.Sender.SERVER);
            }
        }

        chat = context.messageStore.createChat(participantsSet);
        return new NotifyNetData("Successfully created a new chat with id " + chat.id + "\n"
                + "the participants are " + chat.participants.toString(), NetData.Sender.SERVER);
    }
}
