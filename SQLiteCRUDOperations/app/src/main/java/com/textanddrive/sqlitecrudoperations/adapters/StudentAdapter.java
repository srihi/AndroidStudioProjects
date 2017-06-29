package com.textanddrive.sqlitecrudoperations.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.textanddrive.sqlitecrudoperations.AddStudentActivity;
import com.textanddrive.sqlitecrudoperations.R;
import com.textanddrive.sqlitecrudoperations.SQLiteDatabaseHelper;
import com.textanddrive.sqlitecrudoperations.ViewStudentActivity;
import com.textanddrive.sqlitecrudoperations.models.Student;
import com.textanddrive.sqlitecrudoperations.AddStudentActivity;
import com.textanddrive.sqlitecrudoperations.SQLiteDatabaseHelper;
import com.textanddrive.sqlitecrudoperations.models.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Niraj on 21-03-2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    static List<Student> studentList;
    static Context context;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.studentList = new ArrayList<Student>();
        this.studentList = studentList;
        this.context = context;
    }

    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, null);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, final int position) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat S = new SimpleDateFormat("dd/MM/yy");
        String date = S.format(c.getTime());

        holder.mTvId.setText(studentList.get(position).getId());
        holder.mTvName.setText(studentList.get(position).getName());
        holder.mTvEmail.setText(studentList.get(position).getEmail());
        holder.mTvPhone.setText(studentList.get(position).getPhoneNumber());
        holder.mTvBirthdate.setText(studentList.get(position).getBirthdate());
        holder.mTvGender.setText(studentList.get(position).getGender());
        byte[] outImage = studentList.get(position).getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.mIvDp.setImageBitmap(theImage);
        holder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddStudentActivity.class);
                intent.putExtra("isFoundEdit", "isFoundEdit");
                intent.putExtra("id", studentList.get(position).getId());
                intent.putExtra("name", studentList.get(position).getName());
                intent.putExtra("email", studentList.get(position).getEmail());
                intent.putExtra("phone", studentList.get(position).getPhoneNumber());
                intent.putExtra("birthdate", studentList.get(position).getBirthdate());
                intent.putExtra("gender", studentList.get(position).getGender());
                byte[] outImage = studentList.get(position).getImage();
                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                theImage.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("image", _bs.toByteArray());
                Log.d("TEST", "onClick: " + _bs.toByteArray());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
        holder.mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper helper = new SQLiteDatabaseHelper(context);
                helper.deleteStudent(studentList.get(position).getId());
                Student itemLabel = studentList.get(position);
                studentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, studentList.size());
                Toast.makeText(context, "Removed : " + itemLabel, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvId;
        private TextView mTvName;
        private TextView mTvEmail;
        private TextView mTvPhone;
        private TextView mTvBirthdate;
        private TextView mTvGender;
        private Button mBtnRemove;
        private Button mBtnEdit;
        private ImageView mIvDp;
        private RelativeLayout mRl;

        public StudentViewHolder(View itemView) {
            super(itemView);
            mTvId = (TextView) itemView.findViewById(R.id.tv_stud_id);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            mTvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            mTvBirthdate = (TextView) itemView.findViewById(R.id.tv_birthdate);
            mTvGender = (TextView) itemView.findViewById(R.id.tv_gender);
            mBtnRemove = (Button) itemView.findViewById(R.id.btn_delete);
            mBtnEdit = (Button) itemView.findViewById(R.id.btn_edit);
            mIvDp = (ImageView) itemView.findViewById(R.id.iv_dp);
            mRl = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
        }
    }
}
