package ru.mail.track.kolodzey;

import ru.mail.track.kolodzey.NetData.NetData;
import sun.nio.ch.Net;

/**
 * Created by DKolodzey on 14.12.15.
 */
public abstract class Handler {
    public abstract NetData handle(NetData data);
}
