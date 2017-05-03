package com.week1.practice1.budgetmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.week1.practice1.budgetmanager.R;

/**
 * Created by dev7 on 3.5.17..
 */

public class SetViewHolder extends RecyclerView.ViewHolder {

    public SetViewHolder(View itemView) {
        super(itemView);
        TextView projName = (TextView)itemView.findViewById(R.id.tvProjectName);
        TextView money = (TextView)itemView.findViewById(R.id.tvMoney);
        ProgressBar progBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        Button btnFocus = (Button)itemView.findViewById(R.id.buttonFocus);
        Button btnEdit = (Button) itemView.findViewById(R.id.buttonEdit);
        Button btnDelete = (Button)itemView.findViewById(R.id.buttonDelete);
    }
}

