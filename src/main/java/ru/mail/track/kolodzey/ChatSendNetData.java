package ru.mail.track.kolodzey;

/**
 * Created by DKolodzey on 01.12.15.
 */
public class ChatSendNetData extends NetData {
    public int chatID;
    public String message;
    ChatSendNetData() {
        this(-1, null, null);
    }
    ChatSendNetData(int chatID, String message, Sender sender) {
        this.requestedAction = Action.CHAT_SEND;
        this.sender = sender;
        this.message = message;
        this.chatID = chatID;
    }
    @Override
    public String toString() {
        return super.toString() + " " + chatID + " " + message;
    }
}
