package com.example.se_2019;

import java.util.ArrayList;

public class Post {
    //목록에 띄워질 수 있게 만든 클래스
    private String name;
    private String write_date;
    private String title;
    private String content;
    private ArrayList<String> chklist;
    private String Dday;
    private int posi;
    private int num;
///전체 총괄할 수 있게 만들기



    public String getName() {
        return name;
    }

    public Post(String name, String write_date, String title, String content, ArrayList<String> chklist, String Dday, int posi, int num) {
        this.name = name;
        this.write_date = write_date;
        this.title = title;
        this.content = content;
        this.chklist = chklist;
        this.Dday = Dday;
        this.posi = posi;
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }


    public int getNum() {
        return num;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getChklist() {
        return chklist;
    }

    public void setChklist(ArrayList<String> chklist) {
        this.chklist = chklist;
    }

    public String getDday() {
        return Dday;
    }

    public void setDday(String dday) {
        Dday = dday;
    }



}
