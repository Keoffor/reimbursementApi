package com.example.reimbursementApi.utils;

public class LoginResponse {
    private boolean isError;
    private String authToken;
    private String messge;

    public LoginResponse(boolean isError, String authToken, String messge) {
        this.isError = isError;
        this.authToken = authToken;
        this.messge = messge;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }
}
