package ru.mail.track.kolodzey.NetData;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class SignInNetData extends NetData {
    @JsonCreator
    public SignInNetData(@JsonProperty("login") String login,
                        @JsonProperty("password") String password,
                        @JsonProperty("sender") Sender sender) {
        this.requestedAction = Action.SIGN_IN;
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
