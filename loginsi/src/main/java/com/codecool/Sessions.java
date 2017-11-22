package com.codecool;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sessions {

    public static boolean checkSession(String session) {

        String sqlQuery = String.format("SELECT * FROM sessions WHERE session_id like '%s'",session);
        ResultSet resultSet;

        try {
            Connection c = DBConnection.getC();
            Statement stmt = c.createStatement();
            resultSet = stmt.executeQuery(sqlQuery);

            if(resultSet.next()){
                if(resultSet.getString("session_id").equals(session)){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void redirect(HttpExchange httpExchange) throws IOException {

        httpExchange.getResponseHeaders().set("Location", "/login-page");
        httpExchange.sendResponseHeaders(302, -1);
    }
}
