package com.week1.practice1.budgetmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dev7 on 27.4.17..
 */
public class dataContractDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "items.db";

    private static final int DATABASE_VERSION = 1;

    public dataContractDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_TABLE = "CREATE TABLE " + dataContract.dataEntry.TABLE_NAME + " (" +
                dataContract.dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                dataContract.dataEntry.COLUMN_PROJECT_NAME + " TEXT NOT NULL, " +
                dataContract.dataEntry.COLUMN_MONEY_GOT + " INTEGER NOT NULL, " +
                dataContract.dataEntry.COLUMN_MONEY_NEED + " INTEGER NOT NULL, " +
                dataContract.dataEntry.COLUMN_DATE_END + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int k){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dataContract.dataEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
    }

}
