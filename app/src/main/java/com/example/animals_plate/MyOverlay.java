package com.example.animals_plate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class MyOverlay extends Overlay {
    private Paint paint;
    private Context context;
    public MyOverlay(List<OverlayItem> items) {
        super();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas pCanvas, MapView pMapView, boolean pShadow) {
        super.draw(pCanvas, pMapView, pShadow);
        if (!pShadow) {
            // 在canvas上绘制覆盖物
            GeoPoint point = new GeoPoint(25.0339639, 121.5622831);
            Point screenPts = new Point();
            pMapView.getProjection().toPixels(point, screenPts);
            int radius = 20;
            pCanvas.drawCircle(screenPts.x, screenPts.y, radius, paint);
           // Drawable drawable = ContextCompat.getDrawable(this, R.drawable.baseline_chat_24);
            //Drawable markerDrawable = ContextCompat.getDrawable(context, R.drawable.baseline_location_on_24); // 自定義的標記圖案
           // GeoPoint point = new GeoPoint(25.0339639, 121.5622831); // 定義標記的位置，這裡是台北 101
            //Marker marker = new Marker(pMapView);
            //marker.setPosition(point);
           // marker.setTitle("Marker Title");
           // marker.setIcon(markerDrawable); // 設定標記圖案
           // pMapView.getOverlays().add(marker);
        }
    }

    public void addItem(OverlayItem overlayItem) {

    }
}
