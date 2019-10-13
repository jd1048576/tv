package jdr.tvtracker.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jdr.tvtracker.R;

public class CircularProgress extends ConstraintLayout {
    private ProgressBar progressBar;
    private TextView textView;

    public CircularProgress(Context context) {
        this(context, null);
    }

    public CircularProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        if (inflater != null) {
            inflater.inflate(R.layout.circular_progress, this);
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.progressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        this.textView = (TextView) findViewById(R.id.circularProgressTextView);
    }

    public void setValue(double valueToSet) {
        int value = BigDecimal.valueOf(10.0d * valueToSet).setScale(0, RoundingMode.HALF_UP).intValue();
        this.progressBar.setProgress(value);
        if (value < 50) {
            this.progressBar.setProgressTintList(ColorStateList.valueOf(Color.argb(255, 219, 35, 96)));
            this.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.argb(64, 219, 35, 96)));
        } else if (value < 70) {
            this.progressBar.setProgressTintList(ColorStateList.valueOf(Color.argb(255, 210, 213, 49)));
            this.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.argb(64, 210, 213, 49)));
        } else {
            this.progressBar.setProgressTintList(ColorStateList.valueOf(Color.argb(255, 33, 208, 122)));
            this.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.argb(64, 33, 208, 122)));
        }
        this.textView.setText(String.valueOf(value));
    }
}
