package com.textanddrive.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.textanddrive.expensemanager.models.Expense;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 02-03-2017.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 1;
    private static final String EXPENSE_TABLE = "expense";
    Context context;
    private static final String CREATE_EXPENSE_TABLE = "create table " + EXPENSE_TABLE
            + " ( "
            + "id integer primary key autoincrement not null, "
            + "date text,"
            + "paymentMode text,"
            + "paymentIndex integer,"
            + "category text,"
            + "categoryIndex integer,"
            + "amount text"
            + " ) ";


    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE);
        onCreate(db);
    }

    public void insertExpense(Expense expense) {
        Log.d("insert", "before insert");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", expense.getDate());
        values.put("paymentMode", expense.getPaymentmode());
        values.put("paymentIndex", expense.getPaymentIndex());
        values.put("category", expense.getCategory());
        values.put("categoryIndex", expense.getCategoryIndex());
        values.put("amount", expense.getAmount());
        db.insert(EXPENSE_TABLE, null, values);
        db.close();
    }

    public List<Expense> getDataFromDB() {
        List<Expense> modelList = new ArrayList<Expense>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + EXPENSE_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(cursor.getString(0));
                expense.setDate(cursor.getString(1));
                expense.setPaymentmode(cursor.getString(2));
                expense.setPaymentIndex(cursor.getInt(3));
                expense.setCategory(cursor.getString(4));
                expense.setCategoryIndex(cursor.getInt(5));
                expense.setAmount(cursor.getString(6));
                modelList.add(expense);
            } while (cursor.moveToNext());
        }
        return modelList;
    }

    public int updateExpense(int id, Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", expense.getDate());
        values.put("paymentMode", expense.getPaymentmode());
        values.put("paymentIndex", expense.getPaymentIndex());
        values.put("category", expense.getCategory());
        values.put("categoryIndex", expense.getCategoryIndex());
        values.put("amount", expense.getAmount());
        return db.update(EXPENSE_TABLE, values,"id"+ " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXPENSE_TABLE, "id" + "=" + expense.getId(), null);
        db.close();
    }
}
