package com.example.testproject.models;

import android.content.Context;

import androidx.annotation.DrawableRes;

import com.example.testproject.R;
import com.example.testproject.utilities.Utilities;

public class Transaction {
    private String title;
    private @DrawableRes
    int icon;
    private long date;
    private int price;
    private Type type;

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public String getDate() {
        return Utilities.formatDate(date);
    }

    public String getPrice() {
        // Todo format price
        return Utilities.getPriceString(String.valueOf(price));
    }

    public Type getType() {
        return type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setType(Context context, Type type) {
        this.type = type;
        switch (type) {
            case Deposit: {
                title = context.getString(R.string.Deposit);
                icon = R.drawable.ic_atm;
                break;
            }
            case Internet: {
                title = context.getString(R.string.Internet);
                icon = R.drawable.ic_fee;
                break;
            }
            case Withdraw: {
                title = context.getString(R.string.Withdraw);
                icon = R.drawable.ic_deposit_gift;
                break;
            }
            case Transfer: {
                title = context.getString(R.string.Transfer);
                icon = R.drawable.ic_remittance_transfer;
                break;
            }
        }
        this.date = System.currentTimeMillis();
    }

    public enum Type {
        Deposit, Internet, Withdraw, Transfer
    }
}
