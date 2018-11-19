package com.example.gurki.cst2335_finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FNSearchResult extends AppCompatActivity {
    ProgressBar mProgressBar;
    TextView name;
    private TextView mcalories;
    TextView protein;
    TextView fat;
    ImageView image;

    protected static final String URL_STRING = "https://api.edamam.com/api/food-database/parser?app_id=e5bc806d&app_key=5f7521ffeefe491b936cea6271e13d3d&ingr=apple";
    protected static final String URL_IMAGE = "https://spoonacular.com/cdn/ingredients_100x100/";
    private TextView mCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fnsearchresult);
        mProgressBar = (ProgressBar) findViewById(R.id.fnProgressBar);
        name = (TextView) findViewById(R.id.fnName);
        mCalories = (TextView) findViewById(R.id.fnCalories);
        protein = (TextView) findViewById(R.id.fnProtein);
        fat = (TextView) findViewById(R.id.fnFat);
        image = (ImageView) findViewById(R.id.fnCurrentFood);
        mProgressBar.setVisibility(View.VISIBLE);
        String searchField;
        Button executeButton=(Button) findViewById(R.id.FNsearchButton);
        executeButton.setOnClickListener((v)->{
            new SearchQuery().execute("https://api.edamam.com/api/food-database/parser?app_id=e5bc806d&app_key=5f7521ffeefe491b936cea6271e13d3d&ingr=apple");
        });
        new SearchQuery().execute(null, null, null);
    }

    class SearchQuery extends AsyncTask<String, String, String> {
        private String name;
        private String calories;
        private String protein;
        private String fat;
        private Bitmap image;

/*        @Override
        protected String doInBackground(String... strings) {
            InputStream stream;
            BufferedReader reader = null;
            HttpURLConnection conn = null;
            try {
                //  EditText searchField = (EditText) findViewById(R.id.FNsearchEditText);
                // Editable searchFieldText = searchField.getText();
                URL url = new URL(URL_STRING);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); //in milliseconds
                conn.setConnectTimeout(15000); //in millisenconds
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }


                //download image through file or URL object
//                if(fileExist(iconFile + ".png")){
//                    Log.i(ACTIVITY_NAME, "Weather image exists, read file");
//                    FileInputStream fis = null;
//                    try {
//                        fis = openFileInput(iconFile + ".png");
//                    } catch (FileNotFoundException e){
//                        e.printStackTrace();
//                    }  bitmap = BitmapFactory.decodeStream(fis);
//
//                }else {
//                    Log.i(ACTIVITY_NAME, "Weather image does not exist, download URL");
//
//                    URL imageUrl = new URL(URL_IMAGE + iconFile + ".png");
//                    conn = (HttpURLConnection) imageUrl.openConnection();
//                    conn.connect();
//                    stream = conn.getInputStream();
//                    bitmap = BitmapFactory.decodeStream(stream);
//
//                    FileOutputStream fos = openFileOutput(iconFile + ".png", Context.MODE_PRIVATE);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
//                    fos.flush();
//                    fos.close();
//                    conn.disconnect();
//                }
                //              publishProgress(100);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }*/

        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection conn = null;
            try {
                URL url = new URL(urls[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String a) {
            super.onPostExecute(a);
            mCalories.setText(a);
        }
    }
}