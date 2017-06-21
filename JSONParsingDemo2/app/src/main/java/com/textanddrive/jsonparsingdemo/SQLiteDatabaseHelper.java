package com.textanddrive.jsonparsingdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.textanddrive.jsonparsingdemo.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 15-06-2017.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "user.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_USER = "user";
    public static final String CREATE_TABLE_USER = "create table " + TABLE_NAME_USER
            + " ( "
            + "id integer, "
            + "firstName text, "
            + "lastName text, "
            + "avatar text"
            + " ) ";
    Context context;

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        Log.d("TEST", "CREATED ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabaseHelper = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("avatar", user.getAvatar());
        sqLiteDatabaseHelper.insert(TABLE_NAME_USER, null, values);
        sqLiteDatabaseHelper.close();
    }

    public List<User> viewUser() {
        List<User> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME_USER, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setAvatar(cursor.getString(3));
                tempList.add(user);
            } while (cursor.moveToNext());
        }
        return tempList;
    }

    public void cleanDB() {
        SQLiteDatabase sqLiteDatabaseHelper = this.getWritableDatabase();
        sqLiteDatabaseHelper.delete(TABLE_NAME_USER, null, null);
        sqLiteDatabaseHelper.close();
    }
}
