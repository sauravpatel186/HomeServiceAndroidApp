package com.example.customer;

public class Problem {
    public String userid;
    public String pname;
    public String pdesc;
    public String category;
    public String fname;
    public String address;
    public String mobile;
    public String mail;
    public String status;
    public String date;
    public String id;

    public Problem()
    {

    }

    @Override
    public String toString() {
        return "Problem{" +
                "userid='" + userid + '\'' +
                ", pname='" + pname + '\'' +
                ", pdesc='" + pdesc + '\'' +
                ", category='" + category + '\'' +
                ", fname='" + fname + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mail='" + mail + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Problem(String userid,String pname,String pdesc,String category,String fname,String address,String mobile,String mail,String status,String date,String id)
    {

        this.userid=userid;
        this.pname=pname;
        this.pdesc=pdesc;
        this.category=category;
        this.fname=fname;
        this.address=address;
        this.mail=mail;
        this.mobile=mobile;
        this.status=status;
        this.date=date;
        this.id=id;
    }
}
