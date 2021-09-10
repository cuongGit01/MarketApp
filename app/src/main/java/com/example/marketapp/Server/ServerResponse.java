package com.example.marketapp.Server;

import com.example.marketapp.models.User;

public class ServerResponse {
    private String result;
    private String message;
    private User user;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
