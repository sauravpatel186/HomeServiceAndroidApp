package com.example.customer;

public class Notify {
    String Date_Time;
    String category;
    String pdesc;
    String pname;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "Date_Time='" + Date_Time + '\'' +
                ", category='" + category + '\'' +
                ", pdesc='" + pdesc + '\'' +
                ", pname='" + pname + '\'' +
                ", status='" + status + '\'' +
                ", tech_name='" + tech_name + '\'' +
                '}';
    }

    String status;

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }

    String tech_name;
    public String getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(String Date_Time) {
        this.Date_Time = Date_Time;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Notify()
    {

    }
    public Notify(String Date_Time,String category,String pdesc,String pname,String tname)
    {
        this.category = category;
        this.Date_Time = Date_Time;
        this.pname = pname;
        this.pdesc = pdesc;
    }
}
