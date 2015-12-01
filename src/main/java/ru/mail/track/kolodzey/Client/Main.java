package ru.mail.track.kolodzey.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.ChatCreateNetData;
import ru.mail.track.kolodzey.Protocol;

public class Main {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                //NetData netData = new LoginNetData("admin", "111", NetData.Sender.CLIENT);
                NetData netData = new ChatCreateNetData(Arrays.asList(2, 3, 5), NetData.Sender.CLIENT);
                Protocol protocol = new Protocol();
                out.write(protocol.encode(netData));
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }



}