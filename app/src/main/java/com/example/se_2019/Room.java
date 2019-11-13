package com.example.se_2019;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Room implements Parcelable
{
    private String roomID;
    private String userID;
    private String roomName;
    private String subName;
    private ArrayList info;

    public Room(String name) {
        this.roomName = name;

    }

    protected Room(Parcel in) {
        roomID = in.readString();
        userID = in.readString();
        roomName = in.readString();
        subName = in.readString();
    }
    public Room(String roomID, String userID, String roomName, String subName){
        roomID = this.roomID;
        userID = this.userID;
        roomName = this.roomName;
        subName  = this.subName;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }
        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roomID);
        dest.writeString(userID);
        dest.writeString(roomName);
        dest.writeString(subName);

    }

    public void setName(String roomname) {
        this.roomName = roomname;
    }
    public void setSubName(String subname) { this.subName = subname; }
    public void setUserID(String userid){this.userID = userid;}
    public void setCode(String code){this.roomID = code;}


    public String getRoomID(){return roomID;}
    public String getUserID(){return userID;}
    public String getRoomName() {
        return roomName;
    }
    public String getSubName(){return subName;}

}
