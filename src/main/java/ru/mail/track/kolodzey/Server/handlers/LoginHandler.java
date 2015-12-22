package ru.mail.track.kolodzey.Server.handlers;

import ru.mail.track.kolodzey.NetData.LoginNetData;
import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.NetDataHandler;
import ru.mail.track.kolodzey.Server.Context;
import ru.mail.track.kolodzey.Server.store.User;

/**
 * Created by DKolodzey on 22.12.15.
 */
public class LoginHandler extends NetDataHandler {
    private Context context;
    public LoginHandler(Context context) {
        this.context = context;
    }
    @Override
    public NetData handle(NetData data) {
        LoginNetData loginData = (LoginNetData) data;
        if (context.user != null) {
            return new NotifyNetData("You aren't logged in as " + loginData.login + "\n"
                    + "because you are already logged in as " + context.user.login + "\n"
                    + "your id is " + context.user.id + "\n"
                    + "Please log out before relogin\n", NetData.Sender.SERVER);
        }
        User user = context.userStore.getUserByLogin(loginData.login);
        if (user == null) {
            return new NotifyNetData("No user with login " + loginData.login, NetData.Sender.SERVER);
        }
        if (!loginData.password.equals(user.password)) {
            return new NotifyNetData("Password is incorrect", NetData.Sender.SERVER);
        }
        this.context.user = user;
        return new NotifyNetData("You successfully logged in as " + context.user.login + "\n"
                + "your id is " + context.user.id, NetData.Sender.SERVER);
    }
}
