package com.textanddrive.jsonparsingdemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Niraj on 10-03-2017.
 */

public class UserResponse implements Parcelable {
    String page;
    int perPage;
    int total;
    int totalPages;
    ArrayList<User> userArrayList = new ArrayList<>();

    public UserResponse(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "page='" + page + '\'' +
                ", perPage=" + perPage +
                ", total=" + total +
                ", totalPages=" + totalPages +
                ", userArrayList=" + userArrayList +
                '}';
    }

    public static Creator<UserResponse> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.page);
        dest.writeInt(this.perPage);
        dest.writeInt(this.total);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.userArrayList);
    }

    public UserResponse() {
    }

    protected UserResponse(Parcel in) {
        this.page = in.readString();
        this.perPage = in.readInt();
        this.total = in.readInt();
        this.totalPages = in.readInt();
        this.userArrayList = in.createTypedArrayList(User.CREATOR);
    }

    public static final Parcelable.Creator<UserResponse> CREATOR = new Parcelable.Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel source) {
            return new UserResponse(source);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };
}
