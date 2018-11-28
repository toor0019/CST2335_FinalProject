package com.example.gurki.cst2335_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class StartActivity extends AppCompatActivity {


    private Button m_OC_Transpo;
    private Button m_Movie_Information;
    private Button m_CBC_News_Reader;
    private Button m_Food_Nutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        m_OC_Transpo= (Button)findViewById(R.id.oc_transpo_bus_route);
        m_Movie_Information=(Button)findViewById(R.id.movie_information);
        m_CBC_News_Reader=(Button)findViewById(R.id.cbc_news_reader);
        m_Food_Nutrition=(Button)findViewById(R.id.food_nutrition);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);//
        m_OC_Transpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,OCTranspoActivity.class);
                startActivity(intent);
            }
        });

        m_Movie_Information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,MovieInformationActivity.class);
                startActivity(intent);
            }
        });

        m_CBC_News_Reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,CBCNewsReaderActivity.class);
                startActivity(intent);
            }
        });

        m_Food_Nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,FNSplashScreen.class);
                startActivity(intent);
            }
        });
    }
}
