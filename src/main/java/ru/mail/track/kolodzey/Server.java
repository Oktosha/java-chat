package ru.mail.track.kolodzey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class Server {

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
                Action action = protocol.decode(buf);
                System.out.printf("Client>%s", action.args);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(sSocket);
        }
    }
}