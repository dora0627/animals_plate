package com.example.animals_plate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class CustomInfoWindow extends InfoWindow {
    private TextView titleTextView;
    public CustomInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @Override
    public void onOpen(Object item) {
        if (mView == null) {
            return;
        }

        Marker marker = (Marker) item;
        titleTextView = mView.findViewById(R.id.titleTextView);
        titleTextView.setText(marker.getTitle());
        titleTextView.setTextColor(Color.BLACK);

        // 设置背景颜色为白色
        mView.setBackground(new ColorDrawable(Color.WHITE));

    }

    @Override
    public void onClose() {
        titleTextView = null;
    }
}
