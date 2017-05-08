package com.week1.practice1.budgetmanager;

import java.util.Calendar;
import java.util.Date;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.week1.practice1.budgetmanager.data.dataContract;
import com.week1.practice1.budgetmanager.data.dataContractDbHelper;


public class addToList extends AppCompatActivity {

    public String TAG = "ez: ";
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        final Button btn = (Button)findViewById(R.id.button);

        //DB Init

        dataContractDbHelper DBHelper = new dataContractDbHelper(this);
        mDb = DBHelper.getWritableDatabase();

        //Click Listener for launching Date Dialog
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final DatePickerDialog dpd = new DatePickerDialog(addToList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Date today = new Date(getYear(), getMonth()-1, getDay());
                        Date picked = new Date(year, month, dayOfMonth);
                        //Prevent projected date being greater than current
                        if(picked.after(today))
                        {
                           btn.setText(dayOfMonth + "/" + Integer.toString(month + 1) + "/" + year);
                        }
                        else{
                            Snackbar.make(v, "Error! Given date can'be be smaller than the current one.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                        }
                    }
                },getYear(),getMonth()-1,getDay());
                dpd.show();
            }
        });

        FloatingActionButton btnAccept = (FloatingActionButton)findViewById(R.id.fabAccept);
        FloatingActionButton btnDecline = (FloatingActionButton)findViewById(R.id.fabDecline);

        // On Decline -> Finish Activity
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // On Accept Call Add to database and check for return
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float check = addToDB();

                if(check!=-1){
                    finish();
                }
                else{
                    Snackbar.make(v, "ERROR!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
            }
        });

    }

    protected void onStart(){
        super.onStart();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Project");


    }


    //Calendar getDay/Month/Year functions
    protected int getDay(){
        Calendar currDate = Calendar.getInstance();
        return currDate.get(Calendar.DAY_OF_MONTH);
    }
    protected int getMonth(){
        Calendar currDate = Calendar.getInstance();
        return currDate.get(Calendar.MONTH)+1;
    }
    protected int getYear(){
        Calendar currDate = Calendar.getInstance();
        return currDate.get(Calendar.YEAR);
    }

   protected long addToDB(){
        EditText prName = (EditText)findViewById(R.id.editText);
        EditText mnGot = (EditText)findViewById(R.id.editText2);
        EditText mnNeed = (EditText)findViewById(R.id.editText3);
        Button getDate = (Button)findViewById(R.id.button);

        int moneyGot;
        int moneyNeed;

        String date = getDate.getText().toString();
        String name = prName.getText().toString();
        try{
            moneyGot = Integer.parseInt(mnGot.getText().toString());
            moneyNeed = Integer.parseInt(mnNeed.getText().toString());
        }
        catch (Exception e){
            return -1;
        }
        if(getDate.getText().toString().contains("/") == false || prName.length() == 0){
            return -1;
        }

        ContentValues cv = new ContentValues();

        cv.put(dataContract.dataEntry.COLUMN_PROJECT_NAME, name);
        cv.put(dataContract.dataEntry.COLUMN_DATE_END, date);
        cv.put(dataContract.dataEntry.COLUMN_MONEY_GOT, moneyGot);
        cv.put(dataContract.dataEntry.COLUMN_MONEY_NEED, moneyNeed);


        long a = mDb.insert(dataContract.dataEntry.TABLE_NAME, null, cv);
        return a;

    }

}


