package com.textanddrive.jsonparsingdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.textanddrive.jsonparsingdemo.R;
import com.textanddrive.jsonparsingdemo.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Niraj on 19-03-2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context){
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.mTvId.setText(userList.get(position).getId()+"");
        holder.mTvName.setText(userList.get(position).getFirstName()+" "+userList.get(position).getLastName());
        Picasso.with(context).load(userList.get(position).getAvatar()).into(holder.mCivAvatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mCivAvatar;
        private TextView mTvId;
        private TextView mTvName;

        public UserViewHolder(View itemView) {
            super(itemView);
            mCivAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
            mTvId= (TextView) itemView.findViewById(R.id.tv_id);
            mTvName= (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}