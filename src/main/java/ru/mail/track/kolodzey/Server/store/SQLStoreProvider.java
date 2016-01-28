package ru.mail.track.kolodzey.Server.store;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            init();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLStoreProvider unable to load sql");
        }
    }

    private void init() {
        try (Connection c = source.getConnection()) {

            Statement stmt;
            String sql;

            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS users"
                    + "("
                    + "id       SERIAL PRIMARY KEY    ,"
                    + "login    TEXT   UNIQUE NOT NULL,"
                    + "password TEXT   NOT NULL"
                    + ")";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS chats"
                    + "("
                    + "id   SERIAL PRIMARY KEY,"
                    + "type VARCHAR(10) NOT NULL    "
                    + ")";
            stmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS messages"
                    + "("
                    + "id       SERIAL    PRIMARY KEY,"
                    + "text     TEXT      NOT NULL   ,"
                    + "time     TIMESTAMP NOT NULL   ,"
                    + "senderID INTEGER   NOT NULL   ,"
                    + "chatID   INTEGER   NOT NULL   ,"
                    + "foreign key (senderID) references users (id),"
                    + "foreign key (chatID)   references chats (id)"
                    + ")";
            stmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS dialogs"
                    + "("
                    + "chatID INTEGER UNIQUE NOT NULL,"
                    + "user1  INTEGER NOT NULL,"
                    + "user2  INTEGER NOT NULL,"
                    + "foreign key (chatID) references chats (id),"
                    + "foreign key (user1)  references users (id),"
                    + "foreign key (user2)  references users (id),"
                    + "UNIQUE(user1, user2),"
                    + "CHECK(user1 <= user2)"
                    + ")";
            stmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS polylogs"
                    + "("
                    + "chatID INTEGER NOT NULL,"
                    + "userID INTEGER NOT NULL,"
                    + "foreign key (chatID) references chats (id),"
                    + "foreign key (userID) references users (id),"
                    + "UNIQUE(chatID, userID)"
                    + ")";
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println("Database troubles: " + e.getMessage());
        }
    }
    public UserStore getUserStore() {
        return new SQLUserStoreImpl(source);
    }
    public MessageStore getMessageStore() {
        return new SQLMessageStoreImpl(source);
    }
}
