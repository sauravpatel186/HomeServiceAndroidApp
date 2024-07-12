package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class feedback2{
    String c_name;
    String comment;
    String pname;
    String rating;
    String tech_name;
    public feedback2(){
    }
    @Override
    public String toString() {
        return "feedback2{" +
                "c_name='" + c_name + '\'' +
                ", comment='" + comment + '\'' +
                ", pname='" + pname + '\'' +
                ", rating='" + rating + '\'' +
                ", tech_name='" + tech_name + '\'' +
                '}';
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }
}