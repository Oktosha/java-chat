package ru.mail.track.kolodzey.Server.handlers;

import ru.mail.track.kolodzey.NetData.NetData;
import ru.mail.track.kolodzey.NetData.NotifyNetData;
import ru.mail.track.kolodzey.NetData.SignInNetData;
import ru.mail.track.kolodzey.NetDataHandler;
import ru.mail.track.kolodzey.Server.Context;
import ru.mail.track.kolodzey.Server.store.User;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class SignInHandler extends NetDataHandler {

    Context context;

    SignInHandler(Context context) {
        this.context = context;
    }

    @Override
    public NetData handle(NetData data) {
        if (context.user != null) {
            return new NotifyNetData("user not created because you should log out before creating users\n"
                    + "you are logged in as " + context.user.login + "\n"
                    + "your id is " + context.user.id, NetData.Sender.SERVER);
        }
        SignInNetData signInData = (SignInNetData) data;
        User user = context.userStore.createUser(signInData.login, signInData.password);
        this.context.user = user;
        if (user == null)
            return new NotifyNetData("user not created because the login is already taken", NetData.Sender.SERVER);
        return new NotifyNetData("user successfully created\n"
                                + "you are logged in as " + user.login + "\n"
                                + "your id is " + user.id, NetData.Sender.SERVER);
    }
}
