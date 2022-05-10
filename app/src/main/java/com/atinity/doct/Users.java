package com.atinity.doct;

public class Users {

    String name;
    String email;
    String imageUrl;
    String uid;
    String status;


    public Users() { }

    public Users(String n, String e, String i, String id, String s) {
//        this.name = name;
//        this.email = email;
//        this.imageUrl = imageUrl;
//        this.uid = uid;
//        this.status = status;

        this.name = n;
        this.email = e;
        this.imageUrl = i;
        this.uid = id;
        this.status = s;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
