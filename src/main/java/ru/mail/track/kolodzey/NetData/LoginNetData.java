package ru.mail.track.kolodzey.NetData;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DKolodzey on 27.11.15.
 */

public class LoginNetData extends NetData {
    @JsonCreator
    public LoginNetData(@JsonProperty("login") String login,
                        @JsonProperty("password") String password,
                        @JsonProperty("sender") Sender sender) {
        this.requestedAction = Action.LOGIN;
        this.sender = sender;
        this.login = login;
        this.password = password;
    }
    public String login;
    public String password;

    @Override
    public String toString() {
        return super.toString() + " " + this.login + " " + this.password;
    }
}
