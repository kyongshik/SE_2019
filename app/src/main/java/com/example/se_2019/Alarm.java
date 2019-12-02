package com.example.se_2019;

public class Alarm {
    String time;
    String content;//제목
    String roomcode;
    String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Alarm(String time, String content, String roomcode){
        this.time = time;
        this.content = content;
        this.roomcode = roomcode;
    }
    public Alarm(String user, String time){
        this.user = user;
        this.time = time;
    }
}
