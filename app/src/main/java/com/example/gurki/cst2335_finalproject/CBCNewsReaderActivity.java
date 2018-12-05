package com.example.gurki.cst2335_finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;

/*
@Author: Mahdi Khiari
 */
public class CBCNewsReaderActivity extends AppCompatActivity {

    ListView newsList;
    Button searchButton;
    EditText searchEdit;
    ImageButton cbcImage;
    Context context = this;
    View.OnClickListener mOnClickListener;
    ArrayList<String> listView = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbcnews_reader);

        newsList = findViewById(R.id.cbcNewsList);
        searchButton = findViewById(R.id.cbcSearchButton);
        searchEdit = findViewById(R.id.cbcSearchEdit);
        cbcImage = findViewById(R.id.cbcNewsImage);

        for (int i = 0; i< 5; i++) {
            listView.add("Article " + i);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listView );
        newsList.setAdapter(arrayAdapter);

        cbcImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You are on CBC News", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEdit.getText().toString().isEmpty()){
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Notification");
                    alertDialogBuilder.setMessage("Cannot enter empty String");
                    alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(),"Dialog Box closed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                    alertDialogBuilder.show();
                }
            }
        });

    }
}
