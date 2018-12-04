package com.example.gurki.cst2335_finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Purpose of this activity
 */
public class FoodNutritionActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1: Toast.makeText(this, "Item1 selected",Toast.LENGTH_SHORT).show();
            FNIntroductionFragment item1=new FNIntroductionFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmenttry,item1).commit();


            return true;
            case R.id.item2: Toast.makeText(this, "Item2 selected",Toast.LENGTH_SHORT).show();
                FNDescriptionFragment item2=new FNDescriptionFragment();
                FragmentManager fragmentManager2=getSupportFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.fragmenttry,item2).commit();
                return true;
            case R.id.item3: Toast.makeText(this, "Item3 selected",Toast.LENGTH_SHORT).show();
                FNInformationFragment item3=new FNInformationFragment();
                FragmentManager fragmentManager3=getSupportFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.fragmenttry,item3).commit();
                return true;
            case R.id.item4:  Toast.makeText(this, "Item4 selected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FoodNutritionActivity.this, FoodNutritionActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.drawermenu,menu);

        return true;
    }

    private static String ACTIVITY_NAME = "FoodNutritionStart";
    private Button mSearchButton, mGoBackButton,mRefresh;
    ProgressBar myProgressBar,fnProgress;
    ListView mListView;
    private EditText mEditText;
    FNDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nutrition);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myProgressBar = (ProgressBar) findViewById(R.id.ourProgressBar);
        mSearchButton = findViewById(R.id.FNsearchButton);
        mEditText=(EditText) findViewById(R.id.FNsearchEditText);
        myDB=new FNDatabaseHelper(this);
        mListView = (ListView) findViewById(R.id.FNListView);
        mRefresh=(Button) findViewById(R.id.FNRefresh);
        fnProgress=findViewById(R.id.fnProgressBar);

        ArrayList<String> history=new ArrayList<>();
        Cursor data=myDB.getListContents();
        if(data.getCount()==0){
            Toast toast = Toast.makeText(getApplicationContext(), "History is empty!", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
            toast.show();
        }
        else{
            while(data.moveToNext()){
                history.add(data.getString(1));
                ListAdapter listAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,history);
                mListView.setAdapter(listAdapter);

            }
        }
//        mListView = (ListView) findViewById(R.id.FNListView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fruits);
//        mListView.setAdapter(adapter);
        mSearchButton.setOnClickListener((v) -> {

            String newEntry=mEditText.getText().toString();
            if(mEditText.length()!=0){
                addHistory(newEntry);
            }
            else{
                Snackbar.make(mSearchButton, "This field cannot be empty", Snackbar.LENGTH_LONG).show();
            }
            Log.i(ACTIVITY_NAME, "User clicked on search Food");
            Toast toast = Toast.makeText(getApplicationContext(), "Search button is pressed!", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
            toast.show();
            String temp=mEditText.getText().toString();
           if( temp.trim().isEmpty()){
               //Dialog to show  user to enter text
           }else{
               Intent intent = new Intent(FoodNutritionActivity.this, FNSearchResult.class);
               intent.putExtra("value",temp);
               startActivity(intent);
           }
            myProgressBar.setVisibility(View.VISIBLE);

//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    int i = 0;
//                    while (i != 100) {
//                        myProgressBar.setProgress(i);
//                        i++;
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }).start();


            Snackbar.make(mSearchButton, "Search button is pressed", Snackbar.LENGTH_LONG).show();
        });
        mGoBackButton = findViewById(R.id.FNgoBackButton);
        mGoBackButton.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodNutritionActivity.this);

            builder.setMessage(R.string.FNdialog_message)
                    .setTitle(R.string.FNdialog_title)
                    .setPositiveButton(R.string.FNok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "Here is my response");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.FNcancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();


        });
        mRefresh=findViewById(R.id.FNRefresh);
        mRefresh.setOnClickListener((v)->{
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

                String temp= (String) adapter.getItemAtPosition(position);
                if( temp.trim().isEmpty()){
                    //Dialog to show  user to enter text
                }else{
                    Intent intent = new Intent(FoodNutritionActivity.this, FNSearchResult.class);
                    intent.putExtra("value",temp);
                    startActivity(intent);
                }
                fnProgress.setVisibility(View.VISIBLE);



                Intent intent = new Intent(FoodNutritionActivity.this, FNSearchResult.class);
                startActivity(intent);
            }


        });
    }
        @Override
      protected void onResume() {

        super.onResume();
        //this.onCreate(null);
     }

    public void addHistory(String newEntry){
        boolean insertData=myDB.addData(newEntry);
        if(insertData=true){
            Toast toast = Toast.makeText(getApplicationContext(), "HISTORY MADE!!!", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
            toast.show();

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "OOPS !", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
            toast.show();
        }
    }
}

