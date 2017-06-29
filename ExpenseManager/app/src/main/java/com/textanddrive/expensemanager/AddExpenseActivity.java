package com.textanddrive.expensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.textanddrive.expensemanager.models.Expense;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etDate;
    private Spinner spnPaymentmode;
    private Spinner spnCategory;
    private EditText etAmount;
    private Button btnSave;
    private boolean isFromEdit;
    SQLiteDatabaseHelper helper;

    private void initView() {
        etDate = (EditText) findViewById(R.id.et_date);
        spnPaymentmode = (Spinner) findViewById(R.id.spn_paymentmode);
        spnCategory = (Spinner) findViewById(R.id.spn_category);
        etAmount = (EditText) findViewById(R.id.et_amount);
        btnSave = (Button) findViewById(R.id.btn_save);
    }

    private void setExpense(Intent mIntent) {
        etDate.setText(mIntent.getStringExtra("date"));
        etAmount.setText(mIntent.getStringExtra("amount"));
        spnCategory.setSelection(mIntent.getIntExtra("category", 0));
        spnPaymentmode.setSelection(mIntent.getIntExtra("paymentMode", 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        helper = new SQLiteDatabaseHelper(this);
        initView();

        Intent i = getIntent();
        if (i.hasExtra("isFoundEdit")) {
            isFromEdit = true;
            btnSave.setText("Update");
            setExpense(i);
        } else {
            isFromEdit = false;
            btnSave.setText("Save");
        }
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {

        EditText editText = (EditText) findViewById(R.id.et_date);
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public void onClickDate(View view) {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickSave(View view) {

        String date = etDate.getText().toString();
        String paymentMode = spnPaymentmode.getSelectedItem().toString();
        int paymentIndex = spnPaymentmode.getSelectedItemPosition();
        String category = spnCategory.getSelectedItem().toString();
        int categoryIndex = spnCategory.getSelectedItemPosition();
        String amount = etAmount.getText().toString();
        if (date.equals("") || paymentMode.equals("") || category.equals("") || amount.equals("")) {
            Toast.makeText(AddExpenseActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            Expense expense = new Expense(date, paymentMode, paymentIndex, category, categoryIndex, amount);
            if (isFromEdit) {
                helper.updateExpense(Integer.parseInt(getIntent().getStringExtra("id")), expense);
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                Log.e("TEST", "updated");
                notify();
                finish();
            } else {
                helper.insertExpense(expense);
                Toast.makeText(this, "New Expense Added", Toast.LENGTH_SHORT).show();
                Log.e("TEST", "inserted");
                finish();
            }
        }
        etDate.setText("");
        spnPaymentmode.setSelection(0);
        spnCategory.setSelection(0);
        etAmount.setText("");

        List<Expense> list = helper.getDataFromDB();
        for (Expense expense : list) {
            Log.e("TEST", "Record : " + expense.toString());
        }
    }
}
