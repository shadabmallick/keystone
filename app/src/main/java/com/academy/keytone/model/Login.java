package com.academy.keytone.model;

public class Login {
    private String status;
    private String user_image_url;
    UserDetails User_detailsObject;
    private String message;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getUser_image_url() {
        return user_image_url;
    }

    public UserDetails getUser_details() {
        return User_detailsObject;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public void setUser_details(UserDetails user_detailsObject) {
        this.User_detailsObject = user_detailsObject;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
