package com.example.gurki.cst2335_finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import org.w3c.dom.Text;
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
    private ProgressBar mProgressBar;
    private  TextView name;
    private TextView mCalories;
    private TextView mProtein;
    private TextView mfat;
    private TextView mCarbohydrates;
    private TextView mFibre;
    private TextView mCurrentFood;
    private String query;
    private TextView mResult;
    private Button mGoBackButton;
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
        mProtein = (TextView) findViewById(R.id.fnProtein);
        mfat = (TextView) findViewById(R.id.fnFat);
        mCarbohydrates=(TextView) findViewById(R.id.fnCarbohydrates);
        mFibre=(TextView)findViewById(R.id.fnFibre);
        mCurrentFood=(TextView) findViewById(R.id.fnCurrentFood);
        mProgressBar.setVisibility(View.VISIBLE);
        mResult=(TextView) findViewById(R.id.fnCalories);


        mGoBackButton=(Button) findViewById(R.id.goBack);
        String searchField;
//            Intent intent = new Intent(FNSearchResult.this, FNResult.class);
  //          Log.w("m","ptint");
            new SearchQuery().execute();
    //        startActivity(intent);



        mGoBackButton = findViewById(R.id.goBack);
        mGoBackButton.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FNSearchResult.this);

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

        }
    //}

     class SearchQuery extends AsyncTask<String, String, String> {
        private String name;
        private double calories;
        private double protein;
        private double fat;
         private double carbohydrates;
         private double fibre;
        private Bitmap bitmap;
        public String ImageURL;
        HTTPUtils http = new HTTPUtils();


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

          String object = buffer.toString();
                JSONObject parentObject =new JSONObject(object);
                JSONArray parentArray=parentObject.getJSONArray("parsed");
                JSONObject firstFood = parentArray.getJSONObject(0);//first element
                JSONObject food = firstFood.getJSONObject("food");
                JSONObject nutrients = food.getJSONObject("nutrients");
                 fat = nutrients.getDouble("FAT");
                 calories = nutrients.getDouble("ENERC_KCAL");
                 protein=nutrients.getDouble("PROCNT");
                 carbohydrates=nutrients.getDouble("CHOCDF");
                 fibre=nutrients.getDouble("FIBTG");
            return buffer.toString();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
               e.printStackTrace();
            }
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
            mCurrentFood.setText(query);
            mCalories.setText("\nCalories : " + calories);
            mfat.setText("Fat :"+ fat);
            mProtein.setText("Protein :"+protein);
            mCarbohydrates.setText("Carbohydrates :"+ carbohydrates);
            mFibre.setText("Fibre :"+ fibre);
            mProgressBar.setVisibility(View.INVISIBLE);

        }
    }
    public class HTTPUtils {
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }


    }
}
