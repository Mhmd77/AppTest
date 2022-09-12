package com.example.testproject.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.testproject.R;
import com.example.testproject.utilities.AndroidUtilities;
import com.example.testproject.utilities.LayoutHelper;

import kotlin.NoWhenBranchMatchedException;

public class HeaderLayoutView extends LinearLayout {

    public HeaderLayoutView(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
    }

    public void addItem(Context context, @DrawableRes int icon, String title, @ColorRes int tintColor) {
        addItem(context, icon, title, tintColor, 0);
    }

    public void addItem(Context context, @DrawableRes int icon, String title, @ColorRes int tintColor, @ColorRes int drawableBackgroundColor) {
        HeaderItem item = new HeaderItem(context, icon, title);
        item.setIconTintColor(tintColor);
        item.setDrawableBackgroundColor(drawableBackgroundColor);
        addView(item, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 8, 0, 8, 0));
        invalidate();
    }

    @SuppressLint("ViewConstructor")
    public static class HeaderItem extends FrameLayout {
        private static final int ICON_SIZE = AndroidUtilities.dp(60);
        private String title;
        private @DrawableRes
        int icon;
        private TextPaint textPaint;
        private StaticLayout textLayout;
        private int titleY;
        private @ColorRes
        int tintColor;
        private final Paint drawableBackgroundPaint;
        private int textWidth;

        public HeaderItem(Context context, @DrawableRes int ic, String t) {
            super(context);
            textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setTypeface(AndroidUtilities.getTypeface(context, "fonts/iranyekanwebregular.ttf"));
            textPaint.setColor(ContextCompat.getColor(context, R.color.header_text_color));
            textPaint.setTextSize(AndroidUtilities.dp(14));

            drawableBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            drawableBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.alpha_white));

            icon = ic;
            title = t;
            setWillNotDraw(false);
        }

        public void setIconTintColor(@ColorRes int tintColor) {
            if (tintColor != 0) {
                this.tintColor = tintColor;
            }
        }

        public void setDrawableBackgroundColor(@ColorRes int color) {
            if (color != 0) {
                drawableBackgroundPaint.setColor(ContextCompat.getColor(getContext(), color));
            }
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            textLayout = null;
            textWidth = 0;
            float maxWidth = ICON_SIZE * 1.5f;
            int width;
            if (!TextUtils.isEmpty(title)) {
                textWidth = (int) Math.ceil(textPaint.measureText(title));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    StaticLayout.Builder builder = StaticLayout.Builder.obtain(title, 0, title.length(), textPaint, (int) Math.min(textWidth, maxWidth))
                            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                            .setLineSpacing(0f, 1.0f)
                            .setIncludePad(false);
                    textLayout = builder.build();
                } else {
                    textLayout = new StaticLayout(title, textPaint, (int) Math.min(textWidth, maxWidth), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }
                width = Math.max(icon != 0 ? ICON_SIZE : 0, textWidth);
            } else {
                width = icon != 0 ? ICON_SIZE : 0;
            }
            int height = 0;
            if (icon != 0) {
                height += ICON_SIZE + AndroidUtilities.dp(8);
                titleY = height;
            }
            if (textLayout != null && textLayout.getLineCount() != 0) {
                height += textLayout.getLineBottom(textLayout.getLineCount() - 1);
            }
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.e("DRAW", "NOW");

            if (icon != 0) {
                canvas.save();
                canvas.translate(getWidth() / 2f, ICON_SIZE / 2f);
                canvas.drawOval((-ICON_SIZE) / 2f, -(ICON_SIZE) / 2f, (ICON_SIZE) / 2f, (ICON_SIZE) / 2f, drawableBackgroundPaint);
                Drawable drawable = ContextCompat.getDrawable(getContext(), icon);
                if (drawable != null) {
                    int iconSize = ICON_SIZE - AndroidUtilities.dp(32);
                    drawable.setBounds((-iconSize) / 2, -(iconSize) / 2, (iconSize) / 2, (iconSize) / 2);
                    if (tintColor != 0) {
                        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getContext(), tintColor));
                    }
                    drawable.draw(canvas);
                }
                canvas.restore();
            }
            if (textLayout != null) {
                canvas.save();
                canvas.translate((getWidth() - textWidth) / 2f, titleY);
                textLayout.draw(canvas);
                canvas.restore();
            }
        }
    }
}
