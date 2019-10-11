package com.example.se_2019;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper { //클래스 상속 상속 미
    //데이터베이스의 생성 및 버전 관리를 관리하도록 도와주는 클래스이다.
    static final String DATABASE_NAME = "test.db";
    //static final int DATABASE_VERSION = 2;

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }
/*
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }//기존의 생성자 근데 위의 생성자는 그냥 테이블이 하나라고 생각해서 매개변수로 int만을 넘겨서 오류를 최대한 줄임
*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tableName ( name TEXT, info TEXT);");
    } //데이터베이스가 처음 생성될 때 호출된다. 테이블의 생성이 일어난다.
    // 열 항목명, 데이터형(숫자 데이터형으로 하려면, num INTEGER 이렇게 만든다.)

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tableName");
        onCreate(db);
    } // onCreate에서 버전을 업그레이드하면 얘가 실행된다. 단 얘가 실행되면 앞에 있는 거 ByeBye

}
