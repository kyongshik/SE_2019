package com.example.se_2019;

public class Alarm {
    String time;
    String content;//제목
    String roomcode;

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
}
