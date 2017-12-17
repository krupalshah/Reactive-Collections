package com.krupalshah.obsrvablecollections.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        addFragment(R.id.frame_top, ChangeSourceFragment.newInstance());
        addFragment(R.id.frame_bottom, ChangeObserverFragment.newInstance());
    }

    private void addFragment(int containedId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containedId, fragment, fragment.getClass().getSimpleName())
                .commit();
    }




}
