package com.example.gurki.cst2335_finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Purpose of this activity
 */
public class FoodNutritionActivity extends AppCompatActivity {
    private static String ACTIVITY_NAME = "FoodNutritionStart";
    private Button mSearchButton, mGoBackButton;
    ProgressBar myProgressBar;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nutrition);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        myProgressBar = (ProgressBar) findViewById(R.id.ourProgressBar);
        mSearchButton = findViewById(R.id.FNsearchButton);

        String fruits[] = {"Apple", "Banana", "Mango", "Grapes", "Strawberry"};
        mListView = (ListView) findViewById(R.id.FNListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fruits);
        mListView.setAdapter(adapter);
        mSearchButton.setOnClickListener((v) -> {
            Log.i(ACTIVITY_NAME, "User clicked on search Food");
            Toast toast = Toast.makeText(getApplicationContext(), "Search button is pressed!", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
            toast.show();
            myProgressBar.setVisibility(View.VISIBLE);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    int i = 0;
                    while (i != 100) {
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


            Snackbar.make(mSearchButton, "Search button is pressed", Snackbar.LENGTH_LONG).show();
        });
        mGoBackButton = findViewById(R.id.FNgoBackButton);
        mGoBackButton.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodNutritionActivity.this);

            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "Here is my response");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();


        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(FoodNutritionActivity.this, Page.class);
                startActivity(intent);
            }


        });
    }
}

