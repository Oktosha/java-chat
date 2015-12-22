package ru.mail.track.kolodzey.NetData;

/**
 * Created by DKolodzey on 30.11.15.
 */
public class ChatHistoryNetData extends NetData {

    public Integer chatID;

    public ChatHistoryNetData() {
        this(null, null);
    }

    public ChatHistoryNetData(Integer chatID, Sender sender) {
        this.sender = sender;
        this.requestedAction = Action.CHAT_HISTORY;
        this.chatID = chatID;
    }

    @Override
    public String toString() {
        return super.toString() + " " + chatID;
    }
}
