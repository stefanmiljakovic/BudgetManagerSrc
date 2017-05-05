package com.week1.practice1.budgetmanager.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.week1.practice1.budgetmanager.BudgetItem;
import com.week1.practice1.budgetmanager.R;
import com.week1.practice1.budgetmanager.data.dataContractDbHelper;

import java.util.List;


/**
 * Created by dev7 on 3.5.17..
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<BudgetItem> items;
    private int itemLayout;
    Context mContext;


    public MainAdapter(List<BudgetItem> items, int itemLayout, Context context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.mContext = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    dataContractDbHelper DBHelper = new dataContractDbHelper(mContext);
    SQLiteDatabase mDb = DBHelper.getWritableDatabase();


    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        BudgetItem item = items.get(position);
        holder.projName.setText(item.getName());
        holder.money.setText(item.outOf());
        holder.pbar.setProgress(item.getPct());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public void remove(BudgetItem item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView projName;
        public TextView money;
        public ProgressBar pbar;
        public Button btnDelete;
        public Button btnFocus;
        public Button btnEdit;


        public ViewHolder(View itemView) {
            super(itemView);
            projName = (TextView)itemView.findViewById(R.id.tvProjectName);
            money = (TextView)itemView.findViewById(R.id.tvMoney);
            pbar = (ProgressBar)itemView.findViewById(R.id.progressBar);
            btnDelete = (Button)itemView.findViewById(R.id.buttonDelete);
            btnFocus = (Button)itemView.findViewById(R.id.buttonFocus);
            btnEdit = (Button)itemView.findViewById(R.id.buttonEdit);
        }
    }
}

