package com.textanddrive.assesmentdemo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedbackFragment extends Fragment {

    private Context context;
    private AppCompatEditText etSenderName;
    private AppCompatEditText etSenderEmail;
    private AppCompatEditText etFeedback;
    private AppCompatButton btnSendFeedback;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    private void initViews(View view) {
        etSenderName = (AppCompatEditText) view.findViewById(R.id.et_sender_name);
        etSenderEmail = (AppCompatEditText) view.findViewById(R.id.et_sender_email);
        etFeedback = (AppCompatEditText) view.findViewById(R.id.et_feedback);
        btnSendFeedback = (AppCompatButton) view.findViewById(R.id.btn_send_feedback);
    }

    public FeedbackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        sharedpreferences = this.getActivity().getSharedPreferences("loginPrefs", context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String email = sharedpreferences.getString("email", "");
        etSenderEmail.setText(email);

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSenderName.getText().toString().isEmpty() || etSenderEmail.getText().toString().isEmpty() || etFeedback.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context
                    );
                    builder.setMessage("Enter All Details");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    String feedback = etFeedback.getText().toString();
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"niraj.prp6895@gmail.com"});
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback On THIS App");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, feedback);
                    startActivity(Intent.createChooser(intent, "Send Feedback"));
                }
            }
        });
    }
}
