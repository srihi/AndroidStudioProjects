package com.textanddrive.expensemanager.models;

/**
 * Created by Niraj on 02-03-2017.
 */

public class Expense {
    String id;
    String date;
    String paymentmode;
    int paymentIndex;
    String category;
    int categoryIndex;
    String amount;

    public Expense() {
    }

    public Expense(String date, String paymentmode, int paymentIndex, String category, int categoryIndex, String amount) {
        this.date = date;
        this.paymentmode = paymentmode;
        this.paymentIndex = paymentIndex;
        this.category = category;
        this.categoryIndex = categoryIndex;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public int getPaymentIndex() {
        return paymentIndex;
    }

    public void setPaymentIndex(int paymentIndex) {
        this.paymentIndex = paymentIndex;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", paymentmode='" + paymentmode + '\'' +
                ", paymentIndex=" + paymentIndex +
                ", category='" + category + '\'' +
                ", categoryIndex=" + categoryIndex +
                ", amount='" + amount + '\'' +
                '}';
    }
}
