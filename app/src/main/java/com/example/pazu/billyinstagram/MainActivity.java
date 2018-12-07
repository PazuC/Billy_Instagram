package com.example.pazu.billyinstagram;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fmgr;
    private FragmentTransaction fragmentTransaction;
    private ViewGroup container;
    private Fragment login_page, register_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());


        container = (ViewGroup) findViewById(R.id.container);

        fmgr = getSupportFragmentManager();

        login_page = new LoginPageFragment();

        fragmentTransaction = fmgr.beginTransaction();

        fragmentTransaction.add(R.id.container, login_page);

        fragmentTransaction.commit();
    }
}
