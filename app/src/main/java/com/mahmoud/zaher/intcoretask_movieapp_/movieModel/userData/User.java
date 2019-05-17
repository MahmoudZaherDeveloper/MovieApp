package com.mahmoud.zaher.intcoretask_movieapp_.movieModel.userData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("phone")
    @Expose
    private String phone;

    public User(String id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }
}
