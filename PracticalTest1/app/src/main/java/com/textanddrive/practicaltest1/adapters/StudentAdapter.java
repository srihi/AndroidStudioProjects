package com.textanddrive.practicaltest1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.textanddrive.practicaltest1.R;
import com.textanddrive.practicaltest1.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 25-05-2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    static List<Student> studentList = new ArrayList<>();
    static Context context;
    private boolean activate;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.studentList = studentList;
        this.context = context;
    }

    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, null);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentViewHolder holder, int position) {

        final int pos = position;
        Intent intent = ((Activity) context).getIntent();
        if (intent.hasExtra("viewStudent")) {
            holder.mCbAttendance.setVisibility(View.INVISIBLE);
        } else if (intent.hasExtra("takeAttendance")) {
            holder.mTvBirthday.setVisibility(View.INVISIBLE);
            holder.mCbAttendance.setVisibility(View.VISIBLE);
        }
        if (activate) {
            holder.mTvBirthday.setVisibility(View.VISIBLE);
            holder.mCbAttendance.setVisibility(View.INVISIBLE);
        }
        holder.mTvId.setText(studentList.get(position).getId());
        holder.mTvEnrolment.setText(studentList.get(position).getEnrolment());
        holder.mTvName.setText(studentList.get(position).getName());
        holder.mTvBirthday.setText(studentList.get(position).getBirthday());
        holder.mCbAttendance.setChecked(studentList.get(position).getAttendance());
        holder.mCbAttendance.setTag(studentList.get(position));
        holder.mCbAttendance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppCompatCheckBox cb = (AppCompatCheckBox) v;
                Student student = (Student) cb.getTag();
                student.setAttendance(cb.isChecked());
                studentList.get(pos).setAttendance(cb.isChecked());
            }
        });
    }
    public void activateButtons(boolean activate) {
        this.activate = activate;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public List<Student> getStudentist() {
        return studentList;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView mTvId;
        private AppCompatTextView mTvEnrolment;
        private AppCompatTextView mTvName;
        private AppCompatTextView mTvBirthday;
        private AppCompatCheckBox mCbAttendance;

        public StudentViewHolder(View itemView) {
            super(itemView);
            mTvId = (AppCompatTextView) itemView.findViewById(R.id.tv_student_id);
            mTvEnrolment = (AppCompatTextView) itemView.findViewById(R.id.tv_student_enrolment);
            mTvName = (AppCompatTextView) itemView.findViewById(R.id.tv_student_name);
            mTvBirthday = (AppCompatTextView) itemView.findViewById(R.id.tv_student_birthdate);
            mCbAttendance = (AppCompatCheckBox) itemView.findViewById(R.id.cb_student_attendance);
        }
    }
}
