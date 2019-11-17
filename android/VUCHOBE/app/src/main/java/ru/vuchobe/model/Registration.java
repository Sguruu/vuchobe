package ru.vuchobe.model;

public class Registration {
    private String username = "";
    private String email = "";
    private String password = "";

    public Registration() {
    }

    public Registration(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return (username != null) ? username : "";
    }

    public void setUsername(String username) {
        this.username = (username != null) ? username : "";
    }

    public String getEmail() {
        return (email != null) ? email : "";
    }

    public void setEmail(String email) {
        this.email = (email != null) ? email : "";
    }

    public String getPassword() {
        return (password != null) ? password : "";
    }

    public void setPassword(String password) {
        this.password = (password != null) ? password : "";
    }
}
