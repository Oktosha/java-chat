package ru.mail.track.kolodzey.Server.store;

import org.postgresql.ds.PGPoolingDataSource;

/**
 * Created by DKolodzey on 21.01.16.
 */
public class SQLStoreProvider {

    private PGPoolingDataSource source;

    public SQLStoreProvider() {
        try {
            Class.forName("org.postgresql.Driver");
            source = new PGPoolingDataSource();
            source.setDataSourceName("My DB");
            source.setServerName("178.62.140.149");
            source.setDatabaseName("oktosha");
            source.setUser("senthil");
            source.setPassword("ubuntu");
            source.setMaxConnections(10);
        } catch (ClassNotFoundException e) {
            System.err.println("SQLStoreProvider unable to load sql");
        }
    }
    public UserStore getUserStore() {
        return new SQLUserStoreImpl(source);
    }
    public MessageStore getMessageStore() {
        return null;
    }
}
