public class Action {
	public enum Type {
		LOGIN, CHAT_HISTORY, CHAT_CREATE, CHAT_SEND, NOTIFY
	}
	Type type;
	String args;
}