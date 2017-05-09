package com.week1.practice1.budgetmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.week1.practice1.budgetmanager.adapter.MainAdapter;

import com.week1.practice1.budgetmanager.data.NotificationReceiver;
import com.week1.practice1.budgetmanager.data.dataContract;
import com.week1.practice1.budgetmanager.data.dataContractDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.*;

public class MainActivity extends AppCompatActivity {

    public SQLiteDatabase mDb;
    public RecyclerView recyclerView;

    public void reloadDB(){
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new MainAdapter(createMockList(), R.layout.item_layout, this));
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

        reloadDB();

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

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter("select"));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("index");
            int index = Integer.parseInt(message);

            recyclerView = (RecyclerView) findViewById(R.id.rv);

            for(int i = 0; i < recyclerView.getLayoutManager().getChildCount();i++)
                recyclerView.getLayoutManager().findViewByPosition(i).setBackgroundColor(Color.parseColor("#EEEEEE"));
            recyclerView.getLayoutManager().findViewByPosition(index).setBackgroundColor(Color.parseColor("#00ff00"));

            List<BudgetItem> bi = createMockList();
            BuildNotification(bi.get(index));

        }
    };

    private void BuildNotification(BudgetItem budgetItem){
        final Intent emptyIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent cancel = new Intent(this, NotificationReceiver.class);
        cancel.putExtra("msg","CANCEL");
        PendingIntent cancelPending = PendingIntent.getBroadcast(this, 0, cancel, 0);


        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.dollar_icon)
                        .setContentTitle("Project name: " + budgetItem.getName())
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("You have raised " + budgetItem.getPct() + "% so far. You have " + budgetItem.toGO()))
                        .setContentIntent(pendingIntent)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_decline,"Dismiss", cancelPending);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        reloadDB();
    }


    private void goToAdd(){
        Intent add = new Intent(this, addToList.class);
        startActivity(add);
    }

}
