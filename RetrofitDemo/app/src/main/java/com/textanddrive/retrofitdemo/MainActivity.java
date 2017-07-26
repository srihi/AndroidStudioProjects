package com.textanddrive.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.textanddrive.retrofitdemo.models.MoviesResponse;
import com.textanddrive.retrofitdemo.models.Result;
import com.textanddrive.retrofitdemo.rest.ApiClient;
import com.textanddrive.retrofitdemo.rest.ApiInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final static String API_KEY = "87daf02cdfca72a5e6bf94fca8016d67";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Result> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                Log.d(TAG, "Movies : " + movies.toString());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
