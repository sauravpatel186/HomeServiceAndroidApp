package com.example.customer;

public class feedbackdata {
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String pid;
    String uid;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String rating;

    @Override
    public String toString() {
        return "feedbackdata{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", rating='" + rating + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

}
