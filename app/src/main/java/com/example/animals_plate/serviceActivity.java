package com.example.animals_plate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class serviceActivity extends AppCompatActivity  {
    ImageView btn_back;
    private static ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        getSupportActionBar().hide();
        expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);

        // Setting group indicator null for custom indicator
        expandableListView.setGroupIndicator(null);

        setItems();
        setListener();
        btnbackclicker();
    }
    public void btnbackclicker(){
        btn_back = findViewById(R.id.service_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(serviceActivity.this,othersfunctionActivity.class);
                startActivity(it);
            }
        });
        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24_pressed);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    // 釋放或取消觸摸時恢復原來的顏色
                   btn_back.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
                }
                return false;
            }
        });
    }
    // Setting headers and childs to expandable listview
    void setItems() {

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();
        List<String> child5 = new ArrayList<String>();
        List<String> child6 = new ArrayList<String>();
        List<String> child7 = new ArrayList<String>();
        List<String> child8 = new ArrayList<String>();
        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        // Adding headers to list
        for (int i = 1; i < 10; i++) {
            header.add(i+" 為何我在地圖編輯通報點內進行更改,按下按鈕後過了一段時間才跳出成功訊息?");

        }
        // Adding child data
        for (int i = 1; i < 2; i++) {
            child1.add("網路訊號的強弱對於通報也會有延遲,建議在訊號較佳的地方進行");

        }
        // Adding child data
        for (int i = 1; i < 2; i++) {
            child2.add("Group 2  - " + " : Child" + i);

        }
        // Adding child data
        for (int i = 1; i < 2; i++) {
            child3.add("Group 3  - " + " : Child" + i);

        }
        // Adding child data
        for (int i = 1; i < 2; i++) {
            child4.add("Group 4  - " + " : Child" + i);

        }
        for (int i = 1; i < 2; i++) {
            child5.add("Group 5  - " + " : Child" + i);

        }
        for (int i = 1; i < 2; i++) {
            child6.add("Group 6  - " + " : Child" + i);

        }
        for (int i = 1; i < 2; i++) {
            child7.add("Group 7  - " + " : Child" + i);

        }
        for (int i = 1; i < 2; i++) {
            child8.add("Group 8  - " + " : Child" + i);

        }

        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);
        hashMap.put(header.get(4), child5);
        hashMap.put(header.get(5), child6);
        hashMap.put(header.get(6), child7);
        hashMap.put(header.get(7), child8);

        adapter = new ExpandableListAdapter(serviceActivity.this, header, hashMap);

        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);


    }

    // Setting different listeners to expandablelistview
    void setListener() {

        // This listener will show toast on group click
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {

                Toast.makeText(serviceActivity.this,
                        "You clicked : " + adapter.getGroup(group_pos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListView
                .setOnGroupExpandListener(new OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(
                        serviceActivity.this,
                        "You clicked : " + adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    }
