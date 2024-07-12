package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class custdetail{
    String fname;
    String mail;

    @Override
    public String toString() {
        return "custdetail{" +
                "fname='" + fname + '\'' +
                ", mail='" + mail + '\'' +
                ", mobile='" + mobile + '\'' +

                ", address='" + address + '\'' +
                '}';
    }

    String mobile;
    String dob;
    String address;


    public custdetail(){

    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
