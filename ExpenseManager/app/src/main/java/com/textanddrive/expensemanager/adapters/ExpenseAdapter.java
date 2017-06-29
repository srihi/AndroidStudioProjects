package com.textanddrive.expensemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.textanddrive.expensemanager.AddExpenseActivity;
import com.textanddrive.expensemanager.R;
import com.textanddrive.expensemanager.SQLiteDatabaseHelper;
import com.textanddrive.expensemanager.models.Expense;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niraj on 02-03-2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    static List<Expense> expenseList;
    static Context context;

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.expenseList = expenseList;
        this.expenseList = new ArrayList<Expense>();
        this.context = context;
    }

    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expense, null);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.ExpenseViewHolder holder, final int position) {
        final Expense expense = expenseList.get(position);
        holder.mTvId.setText(expense.getId());
        holder.mTvDate.setText(expense.getDate());
        holder.mTvPaymentMode.setText(expense.getPaymentmode());
        holder.mTvCategory.setText(expense.getCategory());
        holder.mTvAmount.setText(expense.getAmount());
        Log.d("TEST", "onBindViewHolder: " + expense.getId());
        holder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddExpenseActivity.class);
                intent.putExtra("isFoundEdit", "isFoundEdit");
                intent.putExtra("date", expense.getDate());
                intent.putExtra("paymentMode", expense.getPaymentIndex());
                intent.putExtra("category", expense.getCategoryIndex());
                intent.putExtra("amount", expense.getAmount());
                context.startActivity(intent);
            }
        });
        holder.mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseHelper helper = new SQLiteDatabaseHelper(context);
                helper.deleteExpense(expense);
                Expense itemLabel = expenseList.get(position);
                expenseList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, expenseList.size());
                Toast.makeText(context, "Removed : " + itemLabel, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvId;
        private TextView mTvDate;
        private TextView mTvPaymentMode;
        private TextView mTvCategory;
        private TextView mTvAmount;
        private Button mBtnRemove;
        private Button mBtnEdit;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            mTvId = (TextView) itemView.findViewById((R.id.tv_id));
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTvPaymentMode = (TextView) itemView.findViewById(R.id.tv_paymentmode);
            mTvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            mTvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            mBtnRemove = (Button) itemView.findViewById(R.id.btn_delete);
            mBtnEdit = (Button) itemView.findViewById(R.id.btn_edit);
        }
    }
}
