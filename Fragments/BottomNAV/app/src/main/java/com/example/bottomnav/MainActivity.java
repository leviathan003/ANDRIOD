package com.example.bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;

import com.example.bottomnav.fragments.ContactFragment;
import com.example.bottomnav.fragments.HomeFragment;
import com.example.bottomnav.fragments.ProfileFragment;
import com.example.bottomnav.fragments.SearchFragment;
import com.example.bottomnav.fragments.UtilityFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv ;
    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottomNavView);
        bnv.setSelectedItemId(R.id.navHome);

        setFragment(new HomeFragment(),0);
        bnv.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.navHome){
                setFragment(new HomeFragment(),1);
            } else if (id == R.id.navSearch) {
                setFragment(new SearchFragment(),1);
            } else if (id == R.id.navUtilities) {
                setFragment(new UtilityFragment(),1);
            } else if (id == R.id.navContacts) {
                setFragment(new ContactFragment(),1);
            } else if (id == R.id.navProfile) {
                setFragment(new ProfileFragment(),1);
            }
            return true;
        });
    }

    public void setFragment(Fragment fragment,int flag){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if(flag==0) {
            ft.add(R.id.frameLay, fragment);
        }
        else{
            ft.replace(R.id.frameLay, fragment);
        }
        ft.commit();
    }
}