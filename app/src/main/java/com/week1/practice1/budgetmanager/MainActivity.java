package com.week1.practice1.budgetmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.week1.practice1.budgetmanager.adapter.MainAdapter;

import com.week1.practice1.budgetmanager.data.dataContract;
import com.week1.practice1.budgetmanager.data.dataContractDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.*;

public class MainActivity extends AppCompatActivity {

    public SQLiteDatabase mDb;

    private void reloadDB(){
       RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new MainAdapter(createMockList(), R.layout.item_layout));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

    }

    private List<BudgetItem> createMockList(){
        dataContractDbHelper DBHelper = new dataContractDbHelper(this);
        SQLiteDatabase mDb = DBHelper.getReadableDatabase();

        List<BudgetItem> list = new ArrayList<>();
        int i = 0;
        Cursor cur = mDb.rawQuery("SELECT* FROM " + dataContract.dataEntry.TABLE_NAME , null);
        if(cur.getCount()>0){
        cur.moveToFirst();
        do {
            String name = cur.getString(cur.getColumnIndex(dataContract.dataEntry.COLUMN_PROJECT_NAME));
            int moneyNeed = cur.getInt(cur.getColumnIndex(dataContract.dataEntry.COLUMN_MONEY_NEED));
            int moneyGot = cur.getInt(cur.getColumnIndex(dataContract.dataEntry.COLUMN_MONEY_GOT));
            String dateSTR = cur.getString(cur.getColumnIndex(dataContract.dataEntry.COLUMN_DATE_END));

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = sdf.parse(dateSTR);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            i++;
            list.add(new BudgetItem(moneyNeed, name, date, moneyGot));
            Log.d("AE","AE" + i);
        }while(cur.moveToNext());

        cur.close();
        mDb.close();}

        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Activity"); // Main Acitivty name

        // Floating Button
        FloatingActionButton fbMain = (FloatingActionButton)findViewById(R.id.floatBtnMain);
        fbMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdd();
            }
        });
        reloadDB();

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        reloadDB();
    }

    private void goToAdd(){
        Intent add = new Intent(this, addToList.class);
        startActivity(add);
    }

}
