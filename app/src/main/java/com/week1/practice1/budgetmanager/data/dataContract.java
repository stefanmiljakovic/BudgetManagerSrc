package com.week1.practice1.budgetmanager.data;

import android.provider.BaseColumns;

/**
 * Created by dev7 on 27.4.17..
 */
public class dataContract{

        public static final class dataEntry implements BaseColumns{
        public static final String TABLE_NAME = "projects";
        public static final String COLUMN_PROJECT_NAME = "project_name";
        public static final String COLUMN_MONEY_GOT = "money_got";
        public static final String COLUMN_MONEY_NEED = "money_need";
        public static final String COLUMN_DATE_END = "date_end";

    }
}
