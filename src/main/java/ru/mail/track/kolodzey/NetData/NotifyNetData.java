package ru.mail.track.kolodzey.NetData;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class NotifyNetData extends NetData {
    public NotifyNetData() {
        this.message = null;
        this.sender = null;
        this.requestedAction = null;
    }
    public NotifyNetData(String message, Sender sender) {
        this.requestedAction = Action.NOTIFY;
        this.sender = sender;
        this.message = message;
    }
    public String message;

    @Override
    public String toString() {
        return super.toString() + " " + this.message;
    }
}
