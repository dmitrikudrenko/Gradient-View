package io.github.dmitrikudrenko.gradientview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class GradientView extends View {
    private int centerColor;
    private int borderColor;

    public GradientView(Context context) {
        this(context, null);
    }

    public GradientView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.GradientView);
        centerColor = attributes.getColor(R.styleable.GradientView_gr_centerColor, Color.BLACK);
        borderColor = attributes.getColor(R.styleable.GradientView_gr_borderColor, Color.BLACK);
        attributes.recycle();
        setGradientBackground();
    }

    private void setGradientBackground() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int radius = calculateRadius(width, height);
        GradientDrawable background = new GradientDrawable();
        background.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        background.setColors(new int[]{borderColor, centerColor});
        background.setGradientRadius(radius);
        background.setGradientCenter(0.5F, 1);
        setBackgroundDrawable(background);
    }

    private int calculateRadius(int width, int height) {
        return (int) Math.sqrt(height * height + width * width / 4);
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        setGradientBackground();
    }
}
