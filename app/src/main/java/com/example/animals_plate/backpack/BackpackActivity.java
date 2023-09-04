package com.example.animals_plate.backpack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.example.animals_plate.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BackpackActivity extends AppCompatActivity {
    private ViewPager2 viewPagers;
    private TabLayout tableLayouts;
    private ImageButton bpclose;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);
        //銀藏預設action bar
        getSupportActionBar().hide();

        viewPagers = findViewById(R.id.view_pager_bp);
        tableLayouts = findViewById(R.id.tab_layout_bp);

        bpclose  = findViewById(R.id.backpack_close);
        bpclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setupViewPager();

    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPagers.setAdapter(adapter);
        viewPagers.setCurrentItem(0);
       new TabLayoutMediator(tableLayouts, viewPagers, new TabLayoutMediator.TabConfigurationStrategy() {
           @Override
           public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
               if (position == 0) tab.setText("頭相框");
               if (position == 1) tab.setText("背景");
               if (position == 2) tab.setText("成就");
           }
       }).attach();
    }
}