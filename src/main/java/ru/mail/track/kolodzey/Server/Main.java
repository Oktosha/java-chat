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
import ru.mail.track.kolodzey.Server.handlers.*;
import ru.mail.track.kolodzey.Server.store.DummyMessageStoreImpl;
import ru.mail.track.kolodzey.Server.store.DummyUserStoreImpl;
import ru.mail.track.kolodzey.Server.store.SQLStoreProvider;

public class Main {

    public static final int PORT = 19000;
    private static Map<NetData.Action, NetDataHandler> handlers = new HashMap<>();
    public static void main(String[] args) {
        //TODO: переименовать main в run, main наследовать от runnable
        //TODO: в другом main написать new thread this start
        ServerSocket sSocket = null;
        try {
            sSocket = new ServerSocket(PORT);

            System.out.println("Started, waiting for connection");

            Socket socket = sSocket.accept();
            //TODO: new thread.this.start который снова станет на accept
            System.out.println("Accepted. " + socket.getInetAddress());
            SQLStoreProvider storeProvider = new SQLStoreProvider();
            Context context = new Context(storeProvider.getUserStore(), storeProvider.getMessageStore());
            handlers.put(NetData.Action.SIGN_IN, new SignInHandler(context));
            handlers.put(NetData.Action.LOGIN, new LoginHandler(context));
            handlers.put(NetData.Action.CHAT_CREATE, new ChatCreateHandler(context));
            handlers.put(NetData.Action.CHAT_SEND, new ChatSendHandler(context));
            handlers.put(NetData.Action.CHAT_HISTORY, new ChatHistoryHandler(context));

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                Protocol protocol = new Protocol();
                while(true) {
                    byte[] buf = new byte[32 * 1024]; //TODO: переиспользовать и уменьшить
                    in.read(buf);
                    NetData netData = protocol.decode(buf);
                    System.out.println(netData.toString());
                    NetData answer = null;
                    if (handlers.containsKey(netData.requestedAction)) {
                        //TODO: не возвращать, а записать answer в очередь
                        //TODO: отдельный поток, для обработки отправки сообщений из этой очереди
                        answer = handlers.get(netData.requestedAction).handle(netData);
                    } else {
                        answer = new NotifyNetData("I hear you but don't know what to do", NetData.Sender.SERVER);
                    }
                    out.write(protocol.encode(answer));
                    out.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace(); //TODO: что-то осмысленное, лучше logger
        } finally {
            IOUtils.closeQuietly(sSocket);
        }
    }
}