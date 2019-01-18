package com.example.gurki.cst2335_finalproject;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class RouteDetailActivity extends AppCompatActivity {
    public static final String BUS_NUMBER="BUS";
    public static final String STOP_NUMBER="STOP";
    private int stopNumber;
    private int routeNumber;
    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        stopNumber = getIntent().getIntExtra(STOP_NUMBER,0);
        routeNumber=getIntent().getIntExtra(BUS_NUMBER,0);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(R.id.frame_layout);

        if (mFragment == null) {
            mFragment = RouteFragment.newInstance(stopNumber,routeNumber);
            fm.beginTransaction()
                    .add(R.id.frame_layout, mFragment)
                    .commit();
        }

    }

    public static Intent newIntent(Context context, int busNumber,int stopNumber){
        Intent intent = new Intent(context,RouteDetailActivity.class);
        intent.putExtra(BUS_NUMBER,busNumber);
        intent.putExtra(STOP_NUMBER,stopNumber);
        return intent;
    }
}
