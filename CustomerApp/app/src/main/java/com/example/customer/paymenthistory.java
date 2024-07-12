package com.example.customer;

public class paymenthistory
{
    String uid;
    String fname;
    String date;
    String amount;
    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    String mail;
    String pdesc;

    @Override
    public String toString() {
        return "paymenthistory{" +
                "uid='" + uid + '\'' +
                ", fname='" + fname + '\'' +
                ", date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", category='" + category + '\'' +
                ", mail='" + mail + '\'' +
                ", pdesc='" + pdesc + '\'' +
                ", pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }

    String pid;
    String pname;




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public paymenthistory(String uid, String fname, String date, String amount) {
        this.uid = uid;
        this.fname = fname;
        this.date = date;
        this.amount = amount;
    }

   public paymenthistory()
    {

    }
}
