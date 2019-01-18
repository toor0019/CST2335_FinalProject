package com.example.gurki.cst2335_finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;


public class OCTranspoActivity extends AppCompatActivity {

    private Button mStartButton;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        mStartButton = (Button) findViewById(R.id.start_Button);
        mToolbar =(Toolbar) findViewById(R.id.OC_toolbar);
        setSupportActionBar(mToolbar);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OCTranspoActivity.this,SearchActivity.class));
            }
        });

    }
    @Override
    public boolean  onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.ocItem:

                break;
            // action with ID action_settings was selected
            case R.id.ocFN:
              Intent fnintent = new Intent(OCTranspoActivity.this,FoodNutritionActivity.class);
              startActivity(fnintent);
                break;
            case R.id.movieItem:
                Intent mintent = new Intent(OCTranspoActivity.this,MovieInformationActivity.class);
                startActivity(mintent);
                break;
            case R.id.cbcItem:
                Intent newsintent = new Intent(OCTranspoActivity.this,CBCNewsReaderActivity.class);
                startActivity(newsintent);
                break;
            case R.id.helpItem:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Author : Gurkirat Singh\n"+"Version: 1")
                .setTitle(R.string.help)
                .setPositiveButton(R.string.OK,null)
                .create().show();
                Snackbar.make(mStartButton,R.string.Show,Snackbar.LENGTH_LONG).show();
                break;
            case R.id.dialogItem:
                AlertDialog.Builder databaseDialog = new AlertDialog.Builder(this);
               Map<String,Integer> mInfo = OCTranspoLab.get(OCTranspoActivity.this).getDatabaseInfo();
                databaseDialog.setMessage("Table Name :"+OCTranspoSchema.Stops.NAME+"\n"+"Column Names: "+OCTranspoSchema.Stops.Cols.STOP_NUMBER +" "+OCTranspoSchema.Stops.Cols.RouteID +" "+OCTranspoSchema.Stops.Cols.RouteName+"\n"+"Number of Rows: " +mInfo.get(OCTranspoLab.NUMBER_OF_ROWS)+" \n"+"Number of Stops Saved: "+mInfo.get(OCTranspoLab.NUMBER_OF_STOPS)+"\n"+"Number of Buses Saved: "+mInfo.get(OCTranspoLab.NUMBER_OF_BUSES)).setTitle(R.string.help).setPositiveButton(R.string.OK,null).create().show();

                break;
            default:
                break;
        }

        return true;
    }
}
