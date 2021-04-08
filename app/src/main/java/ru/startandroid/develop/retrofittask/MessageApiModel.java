package ru.startandroid.develop.retrofittask;

import com.google.gson.annotations.SerializedName;

public class MessageApiModel {

    private int userId;
    private int id;
    private String title;

    @SerializedName("body")
    private String text;

    /*public MessageApiModel(int userId, String title, String text){
        this.userId = userId;
        this.title = title;
        this.text = text;
    }*/

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;

    }

    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text = text;
    }



}
