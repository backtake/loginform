package com.codecool;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/login", new Login());
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        server.start();
    }
}
