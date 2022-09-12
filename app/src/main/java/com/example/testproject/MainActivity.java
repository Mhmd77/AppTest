package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testproject.components.HeaderLayoutView;
import com.example.testproject.components.TransactionCell;
import com.example.testproject.models.Transaction;
import com.example.testproject.utilities.AndroidUtilities;
import com.example.testproject.utilities.LayoutHelper;
import com.example.testproject.utilities.Utilities;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Transaction> items = new ArrayList<>();
    private ListAdapter adapter;
    private BottomSheetBehavior behavior;
    private RecyclerView rvTransactions;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));

        AndroidUtilities.fillStatusBarHeight(this);
        AndroidUtilities.checkDisplaySize(this);
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        ((CoordinatorLayout.LayoutParams) headerLayout.getLayoutParams()).topMargin = AndroidUtilities.statusBarHeight;
        headerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headerLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int screenHeight = AndroidUtilities.getScreenHeight(MainActivity.this);
                int heightSize = headerLayout.getHeight() + AndroidUtilities.dp(16) + ((CoordinatorLayout.LayoutParams) headerLayout.getLayoutParams()).topMargin;
                behavior.setPeekHeight(screenHeight - heightSize);
            }
        });

        addHeaderItems(headerLayout);

        rvTransactions = findViewById(R.id.transactions_list);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(adapter = new ListAdapter(this));

        AndroidUtilities.runOnUIThread(this::loadData, 5000);

        ((TextView) findViewById(R.id.balanceTextView)).setText(Utilities.getPriceString("78500000"));

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    private void loadData() {
        rvTransactions.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

        ArrayList<Transaction> data = Utilities.createMockData(this);
        Collections.shuffle(data);
        items.addAll(data);
        adapter.notifyDataSetChanged();
    }

    private void addHeaderItems(LinearLayout headerLayout) {
        HeaderLayoutView headerLayoutView = new HeaderLayoutView(this);

        headerLayoutView.addItem(this, R.drawable.chart, getString(R.string.ManageFinancial), R.color.header_icon_tint_color);
        headerLayoutView.addItem(this, R.drawable.spaces, getString(R.string.Box), R.color.header_icon_tint_color);
        headerLayoutView.addItem(this, R.drawable.add, getString(R.string.ChargeAccount), 0, R.color.white);

        headerLayout.addView(headerLayoutView, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 16, 0, 0));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context mContext;

        public ListAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TransactionCell cell = new TransactionCell(mContext);
            cell.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new Holder(cell);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TransactionCell cell = (TransactionCell) holder.itemView;
            cell.setData(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private class Holder extends RecyclerView.ViewHolder {
            public Holder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}