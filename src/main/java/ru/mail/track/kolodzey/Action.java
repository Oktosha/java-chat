package ru.mail.track.kolodzey;

public class Action {
	public enum Type {
		LOGIN, CHAT_HISTORY, CHAT_CREATE, CHAT_SEND, NOTIFY
	}
	public Type type;
	public String args;
}