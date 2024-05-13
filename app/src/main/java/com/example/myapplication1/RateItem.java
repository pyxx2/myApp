package com.example.myapplication1;

public class RateItem {
    private int id;
    private String cname;
    private String cval;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCval() {
        return cval;
    }

    public void setCval(String cval) {
        this.cval = cval;
    }
    public RateItem(){
        cname="";
        cval="0";
    }
    public  RateItem(String cname,String cval){
        this.cname=cname;
        this.cval=cval;
    }
}
