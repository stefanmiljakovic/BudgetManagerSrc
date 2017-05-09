package com.week1.practice1.budgetmanager.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private int selected = 0;

    public MainAdapter(List<BudgetItem> items, int itemLayout, Context context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }



    @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BudgetItem item = items.get(position);
        holder.projName.setText(item.getName());
        holder.money.setText(item.outOf());
        holder.pbar.setProgress(item.getPct());
        holder.pbarlabel.setText(Integer.toString(item.getPct())+"%");
        holder.tvToGo.setText(item.toGO());


        final String projectName = holder.projName.getText().toString();
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate("DELETE",projectName, position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate("UPDATE", projectName, position);
            }
        });

        holder.btnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate("FOCUS", projectName, position);
            }
        });


        holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return items.size();
    }


    public void operate (String op, final String prName, final int position){
        dataContractDbHelper helper = new dataContractDbHelper(context);
        mDb = helper.getWritableDatabase();

        switch(op){
            case "DELETE":
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Warning!");
                alert.setMessage("Are you sure?");

                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDb.delete(dataContract.dataEntry.TABLE_NAME, dataContract.dataEntry.COLUMN_PROJECT_NAME + "=" + "'" + prName + "'",null);
                        items.remove(position);
                        notifyItemRemoved(position);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog showAlert = alert.create();
                alert.show();
                break;
            case "UPDATE":
                final AlertDialog.Builder addMoney = new AlertDialog.Builder(context);
                addMoney.setTitle("Add funds!");
                final EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHeight(100);
                editText.setWidth(300);

                addMoney.setView(editText);
                addMoney.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues cv = new ContentValues();
                        Cursor c = mDb.rawQuery("SELECT* " +
                                " FROM " + dataContract.dataEntry.TABLE_NAME +
                                " WHERE " + dataContract.dataEntry.COLUMN_PROJECT_NAME +
                                "=" + "'" + prName + "'",null);
                        c.moveToFirst();
                        int a = c.getInt(c.getColumnIndex(dataContract.dataEntry.COLUMN_MONEY_GOT));

                        Log.d("DB", " " + a);
                        int x = Integer.parseInt(editText.getText().toString()) + a;
                        Log.d("DB", " " + x);

                        cv.put(dataContract.dataEntry.COLUMN_MONEY_GOT, x);
                        mDb.update(dataContract.dataEntry.TABLE_NAME, cv,  dataContract.dataEntry.COLUMN_PROJECT_NAME + "=" + "'" + prName + "'",null);

                        items.get(position).setMoneyRaised(x);
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });

                addMoney.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                addMoney.show();
                break;
            case "FOCUS":
                Intent intent = new Intent("select");
                intent.putExtra("index", Integer.toString(position));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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
        public TextView tvToGo;
        public RelativeLayout background;


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
            tvToGo = (TextView)itemView.findViewById(R.id.tvToGo);
            background = (RelativeLayout)itemView.findViewById(R.id.background);
        }
    }
}

