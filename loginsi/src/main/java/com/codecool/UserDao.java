package com.codecool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDao {

    private Connection c = null;
    private Statement stmt = null;

    public UserDao(){
        c = DBConnection.getC();
    }

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM users;" );

        while ( rs.next() ) {
            Integer id = rs.getInt("id");
            String login = rs.getString("login");
            String password = rs.getString("password");

            User newUser = new User(login, password, id);
            users.add(newUser);
        }
        stmt.close();
        return users;
    }
}
