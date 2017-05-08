package com.week1.practice1.budgetmanager;

import android.content.Context;
import android.util.Log;

import java.util.Date;

/**
 * Created by dev7 on 25.4.17..
 */
public class BudgetItem{
        int moneyNeed;
        String name;
        Date date = new Date();
        Date dateEnd;
        int moneyRaised;

        BudgetItem(int MoneyNeed, String Name, Date DateEnd, int MoneyRaised){
            moneyNeed = MoneyNeed;
            name = Name;
            dateEnd = DateEnd;
            moneyRaised = MoneyRaised;
            if (moneyRaised > moneyNeed){
                moneyRaised = moneyNeed;
            }
        }

        public long timeLeft(){
            return Math.abs(date.getTime() - dateEnd.getTime()) / (24 * 60 * 60 * 1000);
        }

        public int moneyLeft(){
            return moneyNeed - moneyRaised;
        }

        public void AddMoney(int add, Context context){
            moneyRaised += add;
            if (moneyRaised > moneyNeed)
                moneyRaised = moneyNeed;
        }

        public String getName(){
            return name;
        }

        public int getPct(){
            double a = moneyNeed;
            double b = moneyRaised;
            Double c = 100 * b/a;
            return Integer.valueOf(c.intValue());
        }

        public String toGO(){
            date = new Date();
            double a = (double)((dateEnd.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
            int s = (int) a;
            return s + " days to go.";
        }
        public String outOf(){
            if(moneyRaised == moneyNeed)
                return "Complete!";
            else
                return Integer.toString(moneyRaised) + "$ / " + Integer.toString(moneyNeed) + "$";
        }
}
