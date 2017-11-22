package com.codecool;

public class User {
    private String login;
    private String password;
    private Integer id;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, Integer id) {
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }
}
