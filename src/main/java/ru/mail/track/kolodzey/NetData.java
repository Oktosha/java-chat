package ru.mail.track.kolodzey;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")

public class NetData {
	public enum Action {
		LOGIN("/login"),
        CHAT_HISTORY("/chat_history"),
        CHAT_CREATE("/chat_create"),
        CHAT_SEND("/chat_send"),
        NOTIFY(null);

        private final String consoleCommand;
        Action(String consoleCommand) {
            this.consoleCommand = consoleCommand;
        }
        @Override
        public String toString() {
            return consoleCommand;
        }
	}
	public enum Sender {
        CLIENT("client"), SERVER("server");
        private final String stringRepresentation;
        Sender(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }
        @Override
        public String toString() {
            return stringRepresentation;
        }
    }
	public Action requestedAction;
	public Sender sender;
    @Override
    public String toString() {
        return this.sender.toString() + "> " + this.requestedAction;
    }
}