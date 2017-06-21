package com.textanddrive.assesmentdemo.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.textanddrive.assesmentdemo.R;
import com.textanddrive.assesmentdemo.SQLiteDatabaseHelper;
import com.textanddrive.assesmentdemo.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 23-05-2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    static List<User> userList;
    static Context context;

    public UserAdapter(Context context, List<User> userList) {
        this.userList = new ArrayList<User>();
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private AppCompatButton btnEditUser;
        private AppCompatButton btnDeleteUser;

        public UserViewHolder(View itemView) {
            super(itemView);
            btnEditUser = (AppCompatButton) itemView.findViewById(R.id.btn_edit_user);
            btnDeleteUser = (AppCompatButton) itemView.findViewById(R.id.btn_delete_user);
        }
    }
}
