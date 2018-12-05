package com.example.gurki.cst2335_finalproject.OC_Transpo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gurki.cst2335_finalproject.R;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class SearchActivity extends AppCompatActivity {

    private static final String URL_STRING ="http://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" ;
    private EditText mStop,mRoute;
    private Button mSearchButton;
    private String holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchButton = (Button)findViewById(R.id.OC_searchButton);
        mStop =(EditText)findViewById(R.id.OC_stopEditText);
        mRoute =(EditText)findViewById(R.id.OC_routeEditText);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkStringEmoty(mRoute.getText().toString())||checkStringEmoty(mStop.getText().toString())){
                    Toast.makeText(SearchActivity.this,"Please Enter the stop number and route number!",Toast.LENGTH_LONG).show();
                }else  {

                }

            }
        });


}
    private boolean checkStringEmoty(String par){
        if(par.trim().isEmpty()){
            return true;
        }else return false;
    }






    class SearchQuery extends AsyncTask<String, String, String> {
        private String name;


        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(URL_STRING+mRoute.getText().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                int event = parser.getEventType();
                while (event != END_DOCUMENT) {
                    String name = parser.getName();
                    
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;}

        @Override
        protected void onPostExecute(String a) {
            super.onPostExecute(a);

        }
    }

    private void parsingRoutes(InputStream inputStream) {

    }

}