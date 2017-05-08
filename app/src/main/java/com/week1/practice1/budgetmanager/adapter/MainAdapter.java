package com.week1.practice1.budgetmanager.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.week1.practice1.budgetmanager.BudgetItem;
import com.week1.practice1.budgetmanager.MainActivity;
import com.week1.practice1.budgetmanager.R;
import com.week1.practice1.budgetmanager.data.dataContract;
import com.week1.practice1.budgetmanager.data.dataContractDbHelper;

import java.util.List;


/**
 * Created by dev7 on 3.5.17..
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<BudgetItem> items;
    private int itemLayout;
    private Context context;
    private SQLiteDatabase mDb;

    public MainAdapter(List<BudgetItem> items, int itemLayout, Context context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }



    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        BudgetItem item = items.get(position);
        holder.projName.setText(item.getName());
        holder.money.setText(item.outOf());
        holder.pbar.setProgress(item.getPct());
        holder.pbarlabel.setText(Integer.toString(item.getPct()));


        final String projectName = holder.projName.getText().toString();
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate("DELETE",projectName);
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


    private void operate (String op, String prName){
        dataContractDbHelper helper = new dataContractDbHelper(context);
        mDb = helper.getWritableDatabase();

        switch(op){
            case "DELETE":
                mDb.delete(dataContract.dataEntry.TABLE_NAME, dataContract.dataEntry.COLUMN_PROJECT_NAME + "=" + "'" + prName + "'",null);
                Intent rel = new Intent("callReloadDB");
                rel.putExtra("message","RELOAD");
                LocalBroadcastManager.getInstance(context).sendBroadcast(rel);
                break;
            case "UPDATE":
                break;
            case "FOCUS":
                break;
        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView projName;
        public TextView money;
        public ProgressBar pbar;
        public Button btnDelete;
        public Button btnFocus;
        public Button btnEdit;
        public TextView pbarlabel;


        public ViewHolder(View itemView) {
            super(itemView);
            projName = (TextView)itemView.findViewById(R.id.tvProjectName);
            money = (TextView)itemView.findViewById(R.id.tvMoney);
            pbar = (ProgressBar)itemView.findViewById(R.id.progressBar);
            pbar.setRotation(-90f);
            pbar.setScaleY(-1f);
            pbarlabel = (TextView)itemView.findViewById(R.id.progressBarLabel);
            btnDelete = (Button)itemView.findViewById(R.id.buttonDelete);
            btnFocus = (Button)itemView.findViewById(R.id.buttonFocus);
            btnEdit = (Button)itemView.findViewById(R.id.buttonEdit);
        }
    }
}

