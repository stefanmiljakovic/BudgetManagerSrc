package com.week1.practice1.budgetmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.week1.practice1.budgetmanager.data.dataContractDbHelper;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public SQLiteDatabase mDb;

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

        //Recycler View
        LinearLayoutManager ll = new LinearLayoutManager(this);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(ll);

        dataContractDbHelper DBHelper = new dataContractDbHelper(this);
        SQLiteDatabase mDb = DBHelper.getWritableDatabase();
    }

    public final SQLiteDatabase getWritableDatabase() {
        return mDb;
    }

    private void goToAdd(){
        Intent add = new Intent(this, addToList.class);
        startActivity(add);
    }

}
