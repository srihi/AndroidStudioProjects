package com.textanddrive.sqlitecrudoperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.textanddrive.sqlitecrudoperations.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 21-03-2017.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_STUDENT = "student";
    Context context;
    public static final String CREATE_TABLE_STUDENT = "create table " + TABLE_NAME_STUDENT
            + " ( "
            + "id integer primary key autoincrement not null, "
            + "name text, "
            + "email text, "
            + "phone text, "
            + "birthdate text, "
            + "gender text, "
            + "image blob"
            + " ) ";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        Log.d("TEST", "CREATED ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STUDENT);
        onCreate(db);
    }

    public void insertStudent(Student student) {
        SQLiteDatabase sqLiteDatabaseHelper = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("email", student.getEmail());
        values.put("phone", student.getPhoneNumber());
        values.put("birthdate", student.getBirthdate());
        values.put("gender", student.getGender());
        values.put("image", Utility.getBytes(student.getImage()));
        sqLiteDatabaseHelper.insert(TABLE_NAME_STUDENT, null, values);
        sqLiteDatabaseHelper.close();
    }

    public List<Student> viewStudent() {
        List<Student> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME_STUDENT, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getString(0));
                student.setName(cursor.getString(1));
                student.setEmail(cursor.getString(2));
                student.setPhoneNumber(cursor.getString(3));
                student.setBirthdate(cursor.getString(4));
                student.setGender(cursor.getString(5));
                student.setImage(Utility.getPhoto(cursor.getBlob(6)));
                tempList.add(student);
            } while (cursor.moveToNext());
        }
        return tempList;
    }

    public int updateStudent(int id, String name, String email, String phone, String birthdate, String gender, Bitmap image) {
        SQLiteDatabase sqLiteDatabaseHelper = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("birthdate", birthdate);
        values.put("gender", gender);
        values.put("image", Utility.getBytes(image));
        Log.e("TEST", "id : " + id);
        return sqLiteDatabaseHelper.update(TABLE_NAME_STUDENT, values, "id" + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_STUDENT, "id" + "=" + id, null);
        db.close();
    }
}