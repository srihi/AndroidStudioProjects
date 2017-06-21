package com.textanddrive.assesmentdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.textanddrive.assesmentdemo.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 23-05-2017.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_USER = "user";

    public static final String ID = "id";
    public static final String USER_NAME = "userName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirmPassword";
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String GENDER = "gender";
    public static final String IDENTITY = "identity";

    Context context;
    public static final String CREATE_TABLE_USER = "create table " + TABLE_NAME_USER
            + " ( "
            + ID + " integer primary key autoincrement not null,"
            + USER_NAME + " text,"
            + EMAIL + " text,"
            + PASSWORD + " text,"
            + CONFIRM_PASSWORD + " text,"
            + MOBILE_NUMBER + " text,"
            + GENDER + " text,"
            + IDENTITY + " text"
            + " ) ";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabaseHelper = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getUserName());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());
        values.put(CONFIRM_PASSWORD, user.getConfirmPassword());
        values.put(MOBILE_NUMBER, user.getMobileNumber());
        values.put(GENDER, user.getGender());
        values.put(IDENTITY, user.getIdentity());
        sqLiteDatabaseHelper.insert(TABLE_NAME_USER, null, values);
        sqLiteDatabaseHelper.close();
        Log.d("TEST", "insertUser: " + user.getGender());
    }

    public List<User> viewUser() {
        List<User> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME_USER, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getString(0));
                user.setUserName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setConfirmPassword(cursor.getString(4));
                user.setMobileNumber(cursor.getString(5));
                user.setGender(cursor.getString(6));
                user.setIdentity(cursor.getString(7));
                tempList.add(user);
            } while (cursor.moveToNext());
        }
        return tempList;
    }

    public int updateUser(int id, String user) {
        SQLiteDatabase sqLiteDatabaseHelper = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user);
        values.put(EMAIL, user);
        values.put(PASSWORD, user);
        values.put(CONFIRM_PASSWORD, user);
        values.put(MOBILE_NUMBER, user);
        values.put(GENDER, user);
        values.put(IDENTITY, user);
        Log.e("TEST", "id : " + id);
        return sqLiteDatabaseHelper.update(TABLE_NAME_USER, values, "id" + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteUser(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_USER, "id" + "=" + id, null);
        db.close();
    }

    public boolean isExist(String email_id, String password, String identity) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM user WHERE email = '" + email_id + "' AND password ='" + password + "' AND identity ='" + identity + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        db.close();
        return exist;
    }
}
