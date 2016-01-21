package ru.mail.track.kolodzey.Server.store;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by DKolodzey on 18.01.16.
 */
public class SQLUserStoreImpl implements UserStore {
    private DataSource dataSource;

    SQLUserStoreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getUserByLogin(String login) {
        try (Connection c = dataSource.getConnection()) {
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE login = ?;");
            stmt.setString(1, login);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt("ID"), resultSet.getString("login"), resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.toString());
        }
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        try (Connection c = dataSource.getConnection()) {
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("ID"), resultSet.getString("login"), resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.toString());
        }
        return null;
    }

    @Override
    public User createUser(String login, String password) {
        try (Connection c = dataSource.getConnection()) {
            String sqlInsert = "INSERT INTO users (login, password) VALUES (?, ?)";
            PreparedStatement stmt = c.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, login);
            stmt.setString(2, password);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return getUserById(id);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.toString());
        }
        return null;
    }
}
