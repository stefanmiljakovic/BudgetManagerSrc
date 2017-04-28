package com.week1.practice1.budgetmanager;

import java.util.Date;

/**
 * Created by dev7 on 25.4.17..
 */
public class BudgetItem{
        int money;
        String name;
        Date date = new Date();
        Date dateEnd;
        int moneyRaised;

        BudgetItem(int a, String b, Date c, int d){
            money = a;
            name = b;
            dateEnd = c;
            moneyRaised = d;
        }

        public long timeLeft(){
            return Math.abs(date.getTime() - dateEnd.getTime()) / (24 * 60 * 60 * 1000);
        }

        public int moneyLeft(){
            return money - moneyRaised;
        }

        public void AddMoney(int add){
            moneyRaised += add;
            if (moneyRaised > money)
                moneyRaised = money;
        }

        public String getName(){
            return name;
        }

        public int getPct(){
            double a = money;
            double b = moneyRaised;
            Double c = Math.floor(b/a);
            return Integer.valueOf(c.intValue());
        }
}
