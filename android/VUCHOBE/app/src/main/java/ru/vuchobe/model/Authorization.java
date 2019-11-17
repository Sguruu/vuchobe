package ru.vuchobe.model;

import androidx.annotation.NonNull;

public class Authorization {
    private String email = "";
    private String password = "";

    public Authorization() {
    }

    public Authorization(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public @NonNull
    String getEmail() {
        return (email != null) ? email : "";
    }

    public void setEmail(String email) {
        this.email = (email != null) ? email : "";
    }

    public @NonNull
    String getPassword() {
        return (password != null) ? password : "";
    }

    public void setPassword(String password) {
        this.password = (password != null) ? password : "";
    }
}
