package com.billing.software.dto;

public class LoginResponseDTO {
    private String jwttoken;
    private String message;

    public LoginResponseDTO(String jwttoken, String message) {
        this.jwttoken = jwttoken;
        this.message = message;
    }

    public LoginResponseDTO() {
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "jwttoken='" + jwttoken + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
