package com.example.se_2019;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Room implements Parcelable
{
    private String name;
    private ArrayList<String> info;
    private String date;

    public Room(String name) {
        this.name = name;
        info = new ArrayList<String>();
    }

    protected Room(Parcel in) {
        name = in.readString();
        info = in.createStringArrayList();
        date = in.readString();
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
        dest.writeString(name);
        dest.writeStringList(info);
        dest.writeString(date);
    }

    public String getName() {
        return name;
    }

    public String getmenu1()
    {
        return info.get(0);
    }
    public String getmenu2()
    {
        return info.get(1);
    }
    public String getmenu3()
    {
        return info.get(2);
    }


    public String getDate() {
        return date;
    }

    public String printmenu(){
        String str = info.get(0) + ", " + info.get(1) + ", " +info.get(2);
        return str;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenu(String item) {
        info.add(item);
    }

    public void setDate(String date) {
        this.date = date;
    }


}
