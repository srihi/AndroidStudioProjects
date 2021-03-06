package com.textanddrive.sqlite1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.textanddrive.sqlite1.AddStudentActivity;
import com.textanddrive.sqlite1.R;
import com.textanddrive.sqlite1.SQLiteDatabaseHelper;
import com.textanddrive.sqlite1.models.Student;

import java.util.ArrayList;
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
        holder.mTvId.setText(studentList.get(position).getId());
        holder.mTvName.setText(studentList.get(position).getName());
        holder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddStudentActivity.class);
                intent.putExtra("isFoundEdit", "isFoundEdit");
                intent.putExtra("id", studentList.get(position).getId());
                intent.putExtra("name", studentList.get(position).getName());
                context.startActivity(intent);
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
        private Button mBtnRemove;
        private Button mBtnEdit;

        public StudentViewHolder(View itemView) {
            super(itemView);
            mTvId = (TextView) itemView.findViewById(R.id.tv_stud_id);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mBtnRemove = (Button) itemView.findViewById(R.id.btn_delete);
            mBtnEdit = (Button) itemView.findViewById(R.id.btn_edit);
        }
    }
}
