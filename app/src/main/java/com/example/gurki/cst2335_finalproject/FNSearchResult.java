package com.example.gurki.cst2335_finalproject;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    private TextView mCalories;
    TextView protein;
    TextView fat;
    ImageView image;
    private String query;
    private TextView mResult;
    protected static final String URL_STRING = "https://api.edamam.com/api/food-database/parser?app_id=e5bc806d&app_key=5f7521ffeefe491b936cea6271e13d3d&ingr=";
    protected static final String URL_IMAGE = "https://spoonacular.com/cdn/ingredients_100x100/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fnsearchresult);
        Bundle b=getIntent().getExtras();
        query =b.getString("value");
        mProgressBar = (ProgressBar) findViewById(R.id.fnProgressBar);
        name = (TextView) findViewById(R.id.fnName);
        mCalories = (TextView) findViewById(R.id.fnCalories);
        protein = (TextView) findViewById(R.id.fnProtein);
        fat = (TextView) findViewById(R.id.fnFat);
        image = (ImageView) findViewById(R.id.fnCurrentFood);
        mProgressBar.setVisibility(View.VISIBLE);
        mResult=(TextView) findViewById(R.id.fnCalories);
        String searchField;
//            Intent intent = new Intent(FNSearchResult.this, FNResult.class);
  //          Log.w("m","ptint");
            new SearchQuery().execute();
    //        startActivity(intent);

        }
    //}

     class SearchQuery extends AsyncTask<String, String, String> {
        private String name;
        private String calories;
        private String protein;
        private String fat;
        private Bitmap image;


        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection conn = null;
            try {
                URL url = new URL(URL_STRING+query);
                conn = (HttpURLConnection) url.openConnection();
                mProgressBar.setProgress(50);
                conn.connect();
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    mProgressBar.setProgress(100);
                }

          /*String object = buffer.toString();
                JSONObject parentObject =new JSONObject(object);
                JSONArray parentArray=parentObject.getJSONArray("parsed");
                StringBuffer data=new StringBuffer();
                for(int i=0; i<parentArray.length();i++){
                    JSONObject finalObject=parentArray.getJSONObject(i);
                    String name=finalObject.getString("categoryLabel");
                    String category=finalObject.getString("category");
                    data.append(name+" - "+category+"\n");
                }*/

                //return data.toString();

            return buffer.toString();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //catch (JSONException e) {
              //  e.printStackTrace();
            //}
            finally {
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
            mResult.setText(a);
        }
    }
}
