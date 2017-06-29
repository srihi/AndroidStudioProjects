package com.textanddrive.sqlitecrudoperations;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.textanddrive.sqlitecrudoperations.models.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddStudentActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    private AppCompatEditText etName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPhoneNumber;
    private AppCompatEditText etBirthDate;
    private AppCompatRadioButton rbMale;
    private AppCompatRadioButton rbFemale;
    private AppCompatTextView tvFile;
    private AppCompatImageView imageView;
    private Button btnSelect;
    private Button btnSave;
    String name;
    String email;
    String phoneNumber;
    String birthDate;
    String gender;
    Drawable image;
    SQLiteDatabaseHelper helper;
    private boolean isFromEdit;
    Bitmap bitmap = null;

    private void initView() {
        etName = (AppCompatEditText) findViewById(R.id.et_name);
        etEmail = (AppCompatEditText) findViewById(R.id.et_email);
        etPhoneNumber = (AppCompatEditText) findViewById(R.id.et_phone);
        etBirthDate = (AppCompatEditText) findViewById(R.id.et_birthdate);
        rbMale = (AppCompatRadioButton) findViewById(R.id.rb_male);
        rbFemale = (AppCompatRadioButton) findViewById(R.id.rb_female);
        tvFile = (AppCompatTextView) findViewById(R.id.tv_file);
        btnSelect = (Button) findViewById(R.id.btn_select);
        imageView = (AppCompatImageView) findViewById(R.id.imageView);
        btnSave = (Button) findViewById(R.id.btn_save);
    }

    private void setStudent(Intent i) {
        etName.setText(i.getStringExtra("name"));
        etEmail.setText(i.getStringExtra("email"));
        etPhoneNumber.setText(i.getStringExtra("phone"));
        etBirthDate.setText(i.getStringExtra("birthdate"));
        gender = i.getStringExtra("gender");
        if (gender.equals("male")) {
            rbMale.setChecked(true);
        } else if (gender.equals("female")) {
            rbFemale.setChecked(true);
        }
        byte[] outImage = i.getByteArrayExtra("image");
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        imageView.setImageBitmap(theImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);

        helper = new SQLiteDatabaseHelper(this);
        initView();

        Intent i1 = getIntent();
        if (i1.hasExtra("isFoundEdit")) {
            isFromEdit = true;
            btnSave.setText("Update");
            setTitle("Update Student");
            setStudent(i1);
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
            String myFormat = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            etBirthDate.setText(sdf.format(myCalendar.getTime()));
        }
    };

    public void onClickDate(View view) {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickSave(View view) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                stream);
        byte imageInByte[] = stream.toByteArray();
        name = etName.getText().toString();
        email = etEmail.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        birthDate = etBirthDate.getText().toString();
        gender = "";

        if (name.equals("") || !(email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) || phoneNumber.length() != 10 || birthDate.equals("") || (!rbMale.isChecked() && !rbFemale.isChecked()) || tvFile.equals("")) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (rbMale.isChecked()) {
                gender = "male";
            } else if (rbFemale.isChecked()) {
                gender = "female";
            }

            Student student = new Student(name, email, phoneNumber, birthDate, gender, imageInByte);

            if (isFromEdit) {
                helper.updateStudent(Integer.parseInt(getIntent().getStringExtra("id")), name, email, phoneNumber, birthDate, gender, imageInByte);
                //   Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                Log.e("TEST", "updated");
                startActivity(new Intent(this, ViewStudentActivity.class));
                finish();
            } else {
                helper.insertStudent(student);
                //  Toast.makeText(this, "New Record Added", Toast.LENGTH_SHORT).show();
                Log.e("TEST", "inserted");
                finish();
            }
        }
        List<Student> list = helper.viewStudent();
        for (Student student1 : list) {
            Log.e("TEST", "Record : " + student1.toString());
        }
    }

    public void onClickBrowse(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, final Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(selectedImageUri);
                Log.i("TEST", "Image Path : " + path);
                tvFile.setText(path);
                imageView.setImageURI(selectedImageUri);
            }
        }

        try {
            bitmap = getThumbnail(data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private Bitmap getThumbnail(Uri uri) throws FileNotFoundException,
            IOException {

        double THUMBNAIL_SIZE = 100;
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;// optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1)
                || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
                : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE)
                : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;// optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0)
            return 1;
        else
            return k;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFromEdit) {
            startActivity(new Intent(this, ViewStudentActivity.class));
        }
    }
}
