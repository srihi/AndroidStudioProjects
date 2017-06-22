package com.textanddrive.projecttextanddrive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CustomMsgActivity extends AppCompatActivity {

    private AppCompatTextView tvListMsg;
    Toolbar toolbarCustomMsg;
    String customMsg = "";
    private ListView listCustomMessage;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    SharedPreferences preferences1;
    SharedPreferences.Editor editor1;

    Set<String> Custommsgset = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_msg);
        toolbarCustomMsg = (Toolbar) findViewById(R.id.toolbar_custom_msg);
        toolbarCustomMsg.setTitle("");
        setSupportActionBar(toolbarCustomMsg);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add_white_36dp);
        toolbarCustomMsg.setOverflowIcon(drawable);
        listCustomMessage = (ListView) findViewById(R.id.lv_custom_msg);


        SharedPreferences pref = getSharedPreferences("MyMsg", MODE_PRIVATE);
        // String mesg = pref.getString("customMsg",null);
        //Set<String> s. pref.getStringSet("customMsg",null);
        Set<String> click = pref.getStringSet("customMsg", new HashSet<String>());

        String[] msgList;

        int n = 0;

        msgList = new String[click.size()];

        if (click != null) {

            Iterator<String> iterator = click.iterator();

            while (iterator.hasNext()) {
                msgList[n] = iterator.next();
                n++;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, msgList);


        listCustomMessage.setAdapter(adapter);


        listCustomMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String msg1 = listCustomMessage.getItemAtPosition(i).toString();
                Toast.makeText(CustomMsgActivity.this, "Msg :" + msg1, Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builderMsg = new AlertDialog.Builder(CustomMsgActivity.this);
                builderMsg.setMessage("Do you want to set this Message?");
                builderMsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CustomMsgActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                        preferences1 = getSharedPreferences("selectedMsg", MODE_PRIVATE);
                        editor1 = preferences1.edit();
                        editor1.putString("msg", msg1);
                        editor1.apply();
                        finish();
                    }
                });
                builderMsg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CustomMsgActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                });
                builderMsg.show();
            }
        });
    }

    //Create Menu list
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_message, menu);
        return true;
    }
    //Finish Create Menu list

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_custom_message:
                final AlertDialog alertDialog = new AlertDialog.Builder(CustomMsgActivity.this).create();
                View view = LayoutInflater.from(CustomMsgActivity.this).inflate(R.layout.popup_custom_msg, null);
                final AppCompatImageView imgBtn;
                imgBtn = (AppCompatImageView) view.findViewById(R.id.img_close);
                imgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                AppCompatButton btnAdd;
                final AppCompatEditText etCustomMsg;
                btnAdd = (AppCompatButton) view.findViewById(R.id.btn_add);
                etCustomMsg = (AppCompatEditText) view.findViewById(R.id.et_custom_message);
                adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        listItems);
                listCustomMessage.setAdapter(adapter);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etCustomMsg.getText().toString().length() == 0) {
                            alertDialog.dismiss();
                            final AlertDialog.Builder builderMsg = new AlertDialog.Builder(CustomMsgActivity.this);
                            builderMsg.setMessage("Please Enter Correct Message");
                            builderMsg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    builderMsg.create().dismiss();
                                    alertDialog.show();
                                    etCustomMsg.requestFocus();
                                }
                            });
                            builderMsg.show();
                        } else {
                            if (listItems.size() == 3) {
                                alertDialog.dismiss();
                                final AlertDialog.Builder builderMsg = new AlertDialog.Builder(CustomMsgActivity.this);
                                builderMsg.setMessage("List is Full");
                                builderMsg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        builderMsg.create().dismiss();
                                    }
                                });
                                builderMsg.show();
                            } else {
                                preferences = getSharedPreferences("MyMsg", MODE_PRIVATE);
                                editor = preferences.edit();
                                customMsg = etCustomMsg.getText().toString();
                                listItems.add(customMsg);
                                etCustomMsg.setText("");
                                // editor.putString("customMsg",customMsg);


                                Custommsgset.add(customMsg);
                                editor.putStringSet("customMsg", Custommsgset);
                                editor.apply();
                                adapter.notifyDataSetChanged();


                                listCustomMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        final String msg = listItems.get(i).toString().trim();
                                        Toast.makeText(CustomMsgActivity.this, "Msg :" + msg, Toast.LENGTH_SHORT).show();
                                        final AlertDialog.Builder builderMsg = new AlertDialog.Builder(CustomMsgActivity.this);
                                        builderMsg.setMessage("Do you want to set this Message?");
                                        builderMsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(CustomMsgActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                                                preferences1 = getSharedPreferences("selectedMsg", MODE_PRIVATE);
                                                editor1 = preferences1.edit();
                                                editor1.putString("msg", msg);
                                                editor1.apply();
                                                finish();
                                            }
                                        });
                                        builderMsg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(CustomMsgActivity.this, "No", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        builderMsg.show();
                                    }
                                });
                                listCustomMessage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        final AlertDialog.Builder builderMsg = new AlertDialog.Builder(CustomMsgActivity.this);
                                        builderMsg.setMessage("Do you want to delete?");
                                        final int deleteId = i;
                                        builderMsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                listItems.remove(deleteId);
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                        builderMsg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        builderMsg.show();

                                        return false;
                                    }
                                });
                            }
                        }
                    }
                });

                alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
                alertDialog.setView(view);
                alertDialog.show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}