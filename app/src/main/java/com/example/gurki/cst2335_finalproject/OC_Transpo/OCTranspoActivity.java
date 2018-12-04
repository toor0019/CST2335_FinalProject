package com.example.gurki.cst2335_finalproject.OC_Transpo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.gurki.cst2335_finalproject.R;

public class OCTranspoActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        mProgressBar = (ProgressBar) findViewById(R.id.start_progressBar);
        startActivity(new Intent(OCTranspoActivity.this,SearchActivity.class));
       // Will try to load the list from the database latter and check if it is empty so that so we can
        // sure to load all the routes previously saved
    }



}
