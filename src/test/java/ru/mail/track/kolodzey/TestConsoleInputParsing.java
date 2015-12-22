package ru.mail.track.kolodzey;


import org.junit.Test;
import static org.junit.Assert.assertEquals;
import ru.mail.track.kolodzey.Client.InputHandler;
import ru.mail.track.kolodzey.NetData.*;

import java.util.List;

/**
 * Created by DKolodzey on 08.12.15.
 */
public class TestConsoleInputParsing {

    InputHandler handler = new InputHandler();

    @Test
    public void testParseLogin() throws InputHandler.InvalidArgsFormatForCommandException {
        LoginNetData parsed = handler.parseLogin("admin 123");
        assertEquals("admin", parsed.login);
        assertEquals("123", parsed.password);
        assertEquals(NetData.Sender.CLIENT, parsed.sender);
        assertEquals(NetData.Action.LOGIN, parsed.requestedAction);
    }

    @Test
    public void testParseChatCreate() throws InputHandler.InvalidArgsFormatForCommandException {
        ChatCreateNetData parsed = handler.parseChatCreate("2, 1,3 ");
        List<Integer> users = parsed.participantIds;
        assertEquals(3, users.size());
        assertEquals(new Integer(2), users.get(0));
        assertEquals(new Integer(1), users.get(1));
        assertEquals(new Integer(3), users.get(2));
        assertEquals(NetData.Sender.CLIENT, parsed.sender);
        assertEquals(NetData.Action.CHAT_CREATE, parsed.requestedAction);
    }

    @Test
    public void testParseChatSend() throws InputHandler.InvalidArgsFormatForCommandException {
        ChatSendNetData parsed = handler.parseChatSend("4 Hello, People!  ");
        assertEquals("Hello, People!  ", parsed.message);
        assertEquals(new Integer(4), parsed.chatID);
        assertEquals(NetData.Sender.CLIENT, parsed.sender);
        assertEquals(NetData.Action.CHAT_SEND, parsed.requestedAction);
    }

    @Test
    public void testParseChatHistory() throws InputHandler.InvalidArgsFormatForCommandException {
        ChatHistoryNetData parsed = handler.parseChatHistory("4");
        assertEquals(new Integer(4), parsed.chatID);
        assertEquals(NetData.Sender.CLIENT, parsed.sender);
        assertEquals(NetData.Action.CHAT_HISTORY, parsed.requestedAction);
    }

    @Test
    public void testParseOnChatCreate() throws InputHandler.InvalidArgsFormatForCommandException,
                                               InputHandler.NoSuchCommandException {
        NetData parsed = handler.parse("/chat_create 2, 3,1");
        assertEquals(NetData.Sender.CLIENT, parsed.sender);
        assertEquals(NetData.Action.CHAT_CREATE, parsed.requestedAction);
        assertEquals(parsed.getClass(), ChatCreateNetData.class);

        ChatCreateNetData castedData = (ChatCreateNetData) parsed;

        List<Integer> users = castedData.participantIds;
        assertEquals(3, users.size());
        assertEquals(new Integer(2), users.get(0));
        assertEquals(new Integer(3), users.get(1));
        assertEquals(new Integer(1), users.get(2));
    }

    @Test(expected = InputHandler.NoSuchCommandException.class)
    public void testParseOnNotExistingCommand() throws InputHandler.InvalidArgsFormatForCommandException,
                                                       InputHandler.NoSuchCommandException {
        NetData parsed = handler.parse("/prove p = np");
    }

    @Test(expected = InputHandler.InvalidArgsFormatForCommandException.class)
    public void testCommandWithNoArgs() throws InputHandler.InvalidArgsFormatForCommandException,
                                               InputHandler.NoSuchCommandException {
        NetData parsed = handler.parse("/login");
    }
}
