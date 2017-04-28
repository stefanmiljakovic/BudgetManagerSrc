package com.week1.practice1.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Activity"); // Main Acitivty name

        FloatingActionButton fbtnMain = (FloatingActionButton)findViewById(R.id.floatBtnMain);

        fbtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdd();
            }
        });
    }

    private void goToAdd(){
        Intent add = new Intent(this, addToList.class);
        startActivity(add);
    }

}
