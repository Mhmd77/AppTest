package com.example.testproject.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.testproject.R;
import com.example.testproject.models.Transaction;

public class TransactionCell extends FrameLayout {
    private @ColorInt final int highlightTintColor;
    public TransactionCell(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.cell_transaction, this);
        highlightTintColor = ContextCompat.getColor(context, R.color.highlight_tint_color);
    }

    public void setData(Transaction transaction) {
        if (transaction == null) {
            return;
        }
        ((ImageView)findViewById(R.id.imageViewIcon)).setImageResource(transaction.getIcon());
        ((TextView)findViewById(R.id.textViewTitle)).setText(transaction.getTitle());
        ((TextView)findViewById(R.id.textViewPrice)).setText(transaction.getPrice());
        ((TextView) findViewById(R.id.textViewDate)).setText(transaction.getDate());
        if (transaction.getType() == Transaction.Type.Withdraw) {
            ((TextView) findViewById(R.id.textViewPrice)).setBackgroundColor(highlightTintColor);
        } else {
            ((TextView) findViewById(R.id.textViewPrice)).setBackgroundColor(0);
        }
    }
}
