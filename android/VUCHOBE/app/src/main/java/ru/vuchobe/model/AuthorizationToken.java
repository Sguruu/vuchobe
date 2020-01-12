package ru.vuchobe.model;

public class AuthorizationToken {
    private String access_token;

    public AuthorizationToken() {
    }

    public AuthorizationToken(String accessToken) {
        this.access_token = access_token;
    }

    public String getAccessToken() {
        return (access_token != null) ? access_token : "";
    }

    public void setAccessToken(String accessToken) {
        this.access_token = (accessToken != null) ? accessToken : "";
    }
}
