package ru.mail.track.kolodzey.Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.Protocol;

public class Main {

    public static final int PORT = 19000;

    public static void main(String[] args) {

        ServerSocket sSocket = null;
        try {
            sSocket = new ServerSocket(PORT);

            System.out.println("Started, waiting for connection");

            Socket socket = sSocket.accept();

            System.out.println("Accepted. " + socket.getInetAddress());

            try (InputStream in = socket.getInputStream()) {

                Protocol protocol = new Protocol();
                byte[] buf = new byte[32 * 1024];
                in.read(buf);
                NetData netData = protocol.decode(buf);
                System.out.println(netData.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(sSocket);
        }
    }
}