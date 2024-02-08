package com.example.tablayoutjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    TabLayout tab;
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tabLay);
        viewPager = findViewById(R.id.viewPager);

        FragmentManager fm = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm,getLifecycle());

        viewPager.setAdapter(adapter);
        tab.addTab(tab.newTab().setText("Chats"));
        tab.addTab(tab.newTab().setText("Status"));
        tab.addTab(tab.newTab().setText("Calls"));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tab.selectTab(tab.getTabAt(position));
            }
        });

    }
}