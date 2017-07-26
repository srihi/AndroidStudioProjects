package com.textanddrive.jsonparsingdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.textanddrive.jsonparsingdemo.models.User;
import com.textanddrive.jsonparsingdemo.models.UserResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://reqres.in/api/";
    SQLiteDatabaseHelper helper;
    private ProgressBar progressBar;
    private UserResponse userResponse;
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        helper = new SQLiteDatabaseHelper(this);
    }


    public void onClickDownload(View view) {
        helper.cleanDB();
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(BASE_URL + "users?page=2");
    }

    public void onClickClear(View view) {
        helper.cleanDB();
        Toast.makeText(HomeActivity.this, "DB Cleared.", Toast.LENGTH_SHORT).show();
    }

    public String getData(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onClickView(View view) {
        startActivity(new Intent(HomeActivity.this, ViewActivity.class));
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return getData(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String json) {
            //  super.onPostExecute(json);
            //   Log.e("TEST", "Json Data : " + json);
            progressBar.setVisibility(View.GONE);
            if (TextUtils.isEmpty(json)) {
                Toast.makeText(HomeActivity.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    userResponse = new UserResponse();
                    JSONObject mainObject = new JSONObject(json);
                    String page = mainObject.getString("page");
                    int per_page = mainObject.getInt("per_page");
                    int total = mainObject.getInt("total");
                    int total_pages = mainObject.getInt("total_pages");
                    //   Log.e("TEST", "Pages : " + page + "\nPer Page : " + per_page + "\nTotal : " + total + "\nTotal Pages : " + total_pages);
                    userResponse.setPage(page);
                    userResponse.setPerPage(per_page);
                    userResponse.setTotal(total);
                    userResponse.setTotalPages(total_pages);
                    ArrayList<User> users = new ArrayList<>();
                    JSONArray jsonArray = mainObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject obj = jsonArray.getJSONObject(i);
                        int id = obj.getInt("id");
                        String first_name = obj.getString("first_name");
                        String last_name = obj.getString("last_name");
                        String avatar = obj.getString("avatar");
                        User user = new User(id, first_name, last_name, avatar);
                        helper.insertUser(user);
                        userList.add(user);
                    }
                 /*   for (User user1 : userList) {
                        Log.e("TEST", "FROM SERVER : " + user1.toString());
                    }
                */
                    Toast.makeText(HomeActivity.this, "Data Downloaded.", Toast.LENGTH_SHORT).show();
                    userResponse.setUserArrayList(userList);
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        }
    }
}
