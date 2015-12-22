package ru.mail.track.kolodzey.Client;

import ru.mail.track.kolodzey.NetData.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DKolodzey on 01.12.15.
 */
public class InputHandler {

    public static class NoSuchCommandException extends Exception {
        private String commandName;
        public NoSuchCommandException(String commandName) {
            this.commandName = commandName;
        }
        public String getCommandName() {
            return commandName;
        }
    }


    public static class InvalidArgsFormatForCommandException extends Exception {
        private String message;
        public InvalidArgsFormatForCommandException(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    public NetData parse(String encodedCommand) throws NoSuchCommandException, InvalidArgsFormatForCommandException {
        String[] tokens = encodedCommand.split("\\s+", 2);
        String name = tokens[0];
        String args = tokens.length > 1 ? tokens[1]: " ";

        switch (name) {
            case "/login":
                return parseLogin(args);
            case "/chat_history":
                return parseChatHistory(args);
            case "/chat_create":
                return parseChatCreate(args);
            case "/chat_send":
                return parseChatSend(args);
            case "/sign_in":
                return parseSignIn(args);
            default:
                throw new NoSuchCommandException(name);
        }
    }

    private SignInNetData parseSignIn(String args) throws InvalidArgsFormatForCommandException {
        String[] tokens = args.split("\\s+");
        if (tokens.length != 2) {
            throw new InvalidArgsFormatForCommandException("Expected username and password split by space.\n"
                    + "Found " + tokens.length + " arguments split by space.");
        }
        return new SignInNetData(tokens[0], tokens[1], NetData.Sender.CLIENT);
    }

    public LoginNetData parseLogin(String args) throws InvalidArgsFormatForCommandException {
        String[] tokens = args.split("\\s+");
        if (tokens.length != 2) {
            throw new InvalidArgsFormatForCommandException("Expected username and password split by space.\n"
                                                            + "Found " + tokens.length + " arguments split by space.");
        }
        return new LoginNetData(tokens[0], tokens[1], NetData.Sender.CLIENT);
    }

    public ChatCreateNetData parseChatCreate(String args) throws InvalidArgsFormatForCommandException {
        String[] tokens = args.split("\\s*,\\s*");
        List<Integer> users = new ArrayList<>();
        if (tokens.length < 1)
            throw new InvalidArgsFormatForCommandException("Too few users for chat.\n"
                                                            + "You need at least one user to create a chat with.\n"
                                                            + "Enter chat participants userIds separated by commas.");
        for (String token : tokens) {
            try {
                users.add(Integer.valueOf(token.trim()));
            } catch (NumberFormatException e) {
                throw new InvalidArgsFormatForCommandException("Can't parse [" + token + "] as userId.\n"
                                                                + "UserIds should be integers.");
            }
        }
        return new ChatCreateNetData(users, NetData.Sender.CLIENT);
    }

    public ChatSendNetData parseChatSend(String args) throws InvalidArgsFormatForCommandException {
        String[] tokens = args.split("\\s+", 2);
        try {
            Integer chatId = Integer.valueOf(tokens[0].trim());
            return new ChatSendNetData(chatId, tokens[1], Instant.now(), NetData.Sender.CLIENT);
        } catch (NumberFormatException e) {
            throw new InvalidArgsFormatForCommandException("Can't parse [" + tokens[0] + "] as valid integer chatID\n");
        }
    }

    public ChatHistoryNetData parseChatHistory(String args) throws InvalidArgsFormatForCommandException {
        try {
            Integer chatId = Integer.valueOf(args.trim());
            return new ChatHistoryNetData(chatId, NetData.Sender.CLIENT);
        } catch (NumberFormatException e) {
            throw new InvalidArgsFormatForCommandException("Can't parse [" + args + "] as valid integer chatID\n");
        }
    }
}
