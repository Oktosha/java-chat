package ru.mail.track.kolodzey.Client;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.ChatCreateNetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.Protocol;

public class Main {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            Protocol protocol = new Protocol();
            Scanner sc = new Scanner(System.in);
            InputHandler inputHandler = new InputHandler();
            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                while(true) {
                    String inputString = sc.nextLine();
                    try {
                        NetData inputNetData = inputHandler.parse(inputString);
                        out.write(protocol.encode(inputNetData));
                        out.flush();
                        byte[] buffer = new byte[320]; //TODO: переиспользовать
                        in.read(buffer);
                        NetData answer = protocol.decode(buffer);
                        NotifyNetData castedAnswer = (NotifyNetData) answer;
                        System.out.println(castedAnswer.message);
                    } catch(InputHandler.NoSuchCommandException e) {
                        System.out.printf("Command %s doesn't exist \n", e.getCommandName());
                    } catch(InputHandler.InvalidArgsFormatForCommandException e) {
                        System.out.printf("%s\n", e.getMessage());
                    }
                    //TODO: поток, который слушает сообщения от сервера
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }



}