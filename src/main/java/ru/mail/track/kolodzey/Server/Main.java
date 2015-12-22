package ru.mail.track.kolodzey.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.NetDataHandler;
import ru.mail.track.kolodzey.Protocol;
import ru.mail.track.kolodzey.Server.handlers.SignInHandler;
import ru.mail.track.kolodzey.Server.store.DummyMessageStoreImpl;
import ru.mail.track.kolodzey.Server.store.DummyUserStoreImpl;

public class Main {

    public static final int PORT = 19000;
    private static Map<NetData.Action, NetDataHandler> handlers = new HashMap<>();
    public static void main(String[] args) {

        ServerSocket sSocket = null;
        try {
            sSocket = new ServerSocket(PORT);

            System.out.println("Started, waiting for connection");

            Socket socket = sSocket.accept();

            System.out.println("Accepted. " + socket.getInetAddress());
            Context context = new Context(new DummyUserStoreImpl(), new DummyMessageStoreImpl());
            handlers.put(NetData.Action.SIGN_IN, new SignInHandler(context));

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                Protocol protocol = new Protocol();
                while(true) {
                    byte[] buf = new byte[32 * 1024];
                    in.read(buf);
                    NetData netData = protocol.decode(buf);
                    System.out.println(netData.toString());
                    NetData answer = null;
                    if (handlers.containsKey(netData.requestedAction)) {
                        answer = handlers.get(netData.requestedAction).handle(netData);
                    } else {
                        answer = new NotifyNetData("I hear you but don't know what to do", NetData.Sender.SERVER);
                    }
                    out.write(protocol.encode(answer));
                    out.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(sSocket);
        }
    }
}