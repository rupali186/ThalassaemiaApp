package com.example.rupali.thalassaemiaapp;

public class NotificationContent {
    private String imageUri;
    private String title;
    private  String message;
    private long id;

    public NotificationContent(long id,String imageUri, String title, String message) {
        this.imageUri = imageUri;
        this.title = title;
        this.message = message;
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
