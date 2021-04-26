package com.example.alhugailproject191333;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mobilefinal.Firebase_Activity;

public class DataBaseHelper extends SQLiteOpenHelper {
    // the data base info :
    private static final String DATABASE_NAME="Userdata.db";
    public static  final String TABLE_NAME="Userinfo";
    public static final String COLUMN_UserID="User_id";
    public static final String COLUMN_FistNAME="First_Name";
    public static final String COLUMN_LastNAME="Last_Name";
    public static final String COLUMN_EMAIL="Email";
    public static final String COLUMN_PHONEnum="Phone_number";


    // -------------------------------------------//
    private SQLiteDatabase databaseb;
    public DataBaseHelperActivity(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
        databaseb =getWritableDatabase();
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                        + "(" + COLUMN_FistNAME + " TEXT NOT NULL,"
                        + COLUMN_LastNAME + " TEXT NOT NULL,"
                        + COLUMN_PHONEnum + " TEXT NOT NULL,"
                        + COLUMN_EMAIL + " TEXT NOT NULL,"
                        + COLUMN_UserID +" INTEGER PRIMARY KEY)");
    }


    // insert function
    public int insertdata(String fname,String lname,String phone,String email,int uid){
        ContentValues values = new ContentValues();

        values.put(COLUMN_FistNAME, fname);
        values.put(COLUMN_LastNAME, lname);
        values.put(COLUMN_PHONEnum, phone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_UserID,uid);

        return (int) databaseb.insert(TABLE_NAME, null, values);

    }
    public int insertdata(Firebase_Activity.User user){
        return insertdata(user.firstName,user.lastName,user.phoneNumber,user.emailAddress,user.userId);
    }



    public Cursor selectAll(){
        Cursor x= databaseb.rawQuery("SELECT * FROM "+ TABLE_NAME, null);

        if (x.moveToFirst()){
            return x;
        }else{
            return null;
        }
    }

// -------------------------------------------//

    // update user method
    public int updateUserinfo(String fist_name,String last_name,String phone_number,String email,int user_id){
        ContentValues values = new ContentValues();

        values.put(COLUMN_FistNAME, fist_name);
        values.put(COLUMN_LastNAME, last_name);
        values.put(COLUMN_PHONEnum, phone_number);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_UserID,user_id);
        String [] args={user_id+""};
        return databaseb.update(TABLE_NAME,values,COLUMN_UserID+"=?",args);

    }

    public Cursor selectByUserID(int uid){
        String [] args={uid+""};
        Cursor x= databaseb.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE "+COLUMN_UserID+"=?",args);
        if (x != null)
            x.moveToFirst();
        if (!x.moveToFirst()){
            return null;
        }
        return x;
    }
    public int  deleteByUserID(int userid){
        String [] args={userid+""};
        return databaseb.delete(TABLE_NAME, COLUMN_UserID+" = ?", args);
    }

// -------------------------------------------//

    // Every time the data Base is updated or upgraded
    @Override
    public void onUpgrade(SQLiteDatabase data, int oldVersion, int newVersion) {
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(data);
    }
}

}
