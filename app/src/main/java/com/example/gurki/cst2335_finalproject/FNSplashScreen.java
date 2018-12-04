package com.example.gurki.cst2335_finalproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FNSplashScreen extends AppCompatActivity {
private static int SPLASH=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fnsplash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent fnMain=new Intent(FNSplashScreen.this,FoodNutritionActivity.class);
                startActivity(fnMain);
                finish();
            }
        },SPLASH);
    }
    }

