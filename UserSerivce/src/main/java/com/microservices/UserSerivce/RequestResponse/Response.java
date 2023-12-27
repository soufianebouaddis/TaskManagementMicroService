package com.microservices.UserSerivce.RequestResponse;

import com.microservices.UserSerivce.entity.User;

import lombok.Data;

@Data
public class Response {
    private User user;
    private String accesstoken;
    private String refreshToken;

    public Response(User user, String accesstoken, String refreshToken) {
        this.user = user;
        this.accesstoken = accesstoken;
        this.refreshToken = refreshToken;
    }

    public Response(String accesstoken) {
        this.accesstoken = accesstoken;
        this.refreshToken = null;
    }

    public Response(String accesstoken, String refreshToken) {
        this.accesstoken = accesstoken;
        this.refreshToken = refreshToken;
    }

}