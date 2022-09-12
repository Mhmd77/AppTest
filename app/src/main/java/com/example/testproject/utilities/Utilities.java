package com.example.testproject.utilities;

import android.content.Context;

import com.example.testproject.models.Transaction;

import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class Utilities {
    private static final CharSequence space = ",";

    public static ArrayList<Transaction> createMockData(Context context) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Transaction t = new Transaction();
            t.setType(context, Transaction.Type.Deposit);
            transactions.add(t);
            t.setDate(System.currentTimeMillis());
            t.setPrice(150000);
        }
        for (int i = 0; i < 5; i++) {
            Transaction t = new Transaction();
            t.setType(context, Transaction.Type.Internet);
            transactions.add(t);
            t.setDate(System.currentTimeMillis());
            t.setPrice(220000);
        }
        for (int i = 0; i < 5; i++) {
            Transaction t = new Transaction();
            t.setType(context, Transaction.Type.Withdraw);
            transactions.add(t);
            t.setDate(System.currentTimeMillis());
            t.setPrice(440000);
        }
        for (int i = 0; i < 5; i++) {
            Transaction t = new Transaction();
            t.setType(context, Transaction.Type.Transfer);
            transactions.add(t);
            t.setDate(System.currentTimeMillis());
            t.setPrice(380000);
        }

        return transactions;
    }

    public static String formatDate(long date) {
        PersianDate persianDate = new PersianDate(date);
        PersianDateFormat persianDateFormat = new PersianDateFormat("l j F H:i Y", PersianDateFormat.PersianDateNumberCharacter.FARSI);
        return persianDateFormat.format(persianDate);
    }

    public static String getPriceString(String s) {
        StringBuilder result = new StringBuilder();
        int counter = 0;
        String clean = s.replace(space, "");
        for (int i = clean.length() - 1; i >= 0; i--) {
            result.append(clean.charAt(i));
            counter++;
            if (counter == 3) {
                result.append(space);
                counter = 0;
            }
        }
        if (result.length() > 3) {
            char end = result.charAt(result.length() - 1);
            if (end == space.charAt(0)) {
                result.deleteCharAt(result.length() - 1);
            }
        }

        return toPersian(result.reverse().toString()) + " ریال";
    }

    public static String toPersian(String c) {
        String[] enN = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] faN = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

        if (c == null) {
            return "";
        }

        for (int i = 0; i < 10; i++) {
            c = c.replace(enN[i], faN[i]);
        }
        return c;
    }
}
