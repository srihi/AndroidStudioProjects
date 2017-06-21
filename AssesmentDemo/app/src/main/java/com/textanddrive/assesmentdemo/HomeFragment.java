package com.textanddrive.assesmentdemo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;

public class HomeFragment extends Fragment {

    private Context context;
    private AppCompatButton btnManageStudent;
    private AppCompatButton btnTakeAttendence;
    private AppCompatButton btnSaveAttendence;
    private AppCompatButton btnViewAttendence;
    private AppCompatButton btnRateStudent;
    private AppCompatButton btnViewFaculty;
    private AppCompatButton btnRateFaculty;
    private Boolean isTeacher;
    LinearLayoutCompat lls;
    LinearLayoutCompat llt;

    private void initViews(View view) {
        btnManageStudent = (AppCompatButton) view.findViewById(R.id.btn_manage_student);
        btnTakeAttendence = (AppCompatButton) view.findViewById(R.id.btn_take_attendence);
        btnSaveAttendence = (AppCompatButton) view.findViewById(R.id.btn_save_attendence);
        btnViewAttendence = (AppCompatButton) view.findViewById(R.id.btn_view_attendence);
        btnRateStudent = (AppCompatButton) view.findViewById(R.id.btn_rate_student);
        btnViewFaculty = (AppCompatButton) view.findViewById(R.id.btn_view_faculty);
        btnRateFaculty = (AppCompatButton) view.findViewById(R.id.btn_rate_faculty);
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        Intent intent = getActivity().getIntent();
        lls = (LinearLayoutCompat) view.findViewById(R.id.linear_layout_student);
        llt = (LinearLayoutCompat) view.findViewById(R.id.linear_layout_teacher);
        if (intent.hasExtra("isTeacher")) {
            lls.setVisibility(View.INVISIBLE);
        }
        if (intent.hasExtra("isStudent")) {
            llt.setVisibility(View.INVISIBLE);
        }
        btnManageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnTakeAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSaveAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnViewAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                final SmileRating smileRating = new SmileRating(context);
                alertDialog.setTitle("Rate Student");
                alertDialog.setView(smileRating);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int rate = smileRating.getSelectedSmile();
                        Toast.makeText(context, "Rating Submitted : " + smileRating.getSmileName(rate), Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btnViewFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRateFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                final SmileRating smileRating = new SmileRating(context);
                alertDialog.setTitle("Rate Faculty");
                alertDialog.setView(smileRating);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int rate = smileRating.getSelectedSmile();
                        Toast.makeText(context, "Rating Submitted : " + smileRating.getSmileName(rate), Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }
}
