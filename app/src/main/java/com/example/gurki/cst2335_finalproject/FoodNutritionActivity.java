package com.example.gurki.cst2335_finalproject;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FoodNutritionActivity extends AppCompatActivity {
    private static String ACTIVITY_NAME = "FoodNutritionStart";
    private Button mButton;
    ProgressBar myProgressBar;
    //final ProgressBar myProgressBar =(ProgressBar)findViewById(R.id.ourProgressBar);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nutrition);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        myProgressBar = (ProgressBar) findViewById(R.id.ourProgressBar);
        mButton = findViewById(R.id.FNsearch);
        mButton.setOnClickListener((v) -> {
            Log.i(ACTIVITY_NAME, "User clicked on search Food");
            Toast toast = Toast.makeText(getApplicationContext(), "Search button is pressed!", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
            toast.show();
            myProgressBar.setVisibility(View.VISIBLE);

            new Thread(new Runnable(){

                @Override
                public void run() {
                    int i=0;
                    while (i!=100){
                        myProgressBar.setProgress(i);
                        i++;
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();


            Snackbar.make(mButton, "Search button is pressed", Snackbar.LENGTH_LONG).show();
        });

    }


}
