package com.example.animals_plate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Marker;

public class CustomMarker extends Marker {
    private int titleColor;
    private Paint backgroundPaint;
    private Rect backgroundRect;

    public CustomMarker(MapView mapView) {
        super(mapView);
        this.titleColor = Color.RED;  // 默认标题文本颜色为红色
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);  // 设置背景颜色为白色

        backgroundRect = new Rect();
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        super.draw(canvas, mapView, shadow);

        if (getTitle() != null) {
            // 绘制标题文本
            Paint textPaint = new Paint();
            textPaint.setColor(titleColor);
            textPaint.setTextSize(24);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getTitle(), mPositionPixels.x, mPositionPixels.y, textPaint);
        }
    }
}
