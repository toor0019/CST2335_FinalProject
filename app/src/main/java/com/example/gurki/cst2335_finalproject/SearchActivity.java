package com.example.gurki.cst2335_finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class SearchActivity extends AppCompatActivity {

    private ImageButton mImageButton;
    private ChatAdapter mChatAdapter;
    private List<Integer> savedStopNumbers;
    private EditText mStop;
    private Button mSearchButton;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private boolean finish=false;
    private static final String URL_STRING = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        savedStopNumbers = OCTranspoLab.get(getApplicationContext()).savedStopList();
        mSearchButton = (Button) findViewById(R.id.OC_searchButton);
        mStop = (EditText) findViewById(R.id.OC_stopEditText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mListView = (ListView) findViewById(R.id.savedRoutes_ListView);
        mChatAdapter = new ChatAdapter( this);
        mListView.setAdapter(mChatAdapter);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStringEmoty(mStop.getText().toString())) {
                    Toast.makeText(SearchActivity.this, "Please Enter the stop number !", Toast.LENGTH_LONG).show();
                } else {
                    finish=false;
                    if (OCTranspoLab.get(getApplicationContext()).stopAlreadyExists(Integer.parseInt(mStop.getText().toString().trim()))) {

                    } else {
                        mProgressBar.setVisibility(View.VISIBLE);
                        new SearchQuery().execute();


                    }
                }
            }
        });
        mListView.setOnItemClickListener(((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = LoadActivity.newIntent(SearchActivity.this,""+savedStopNumbers.get(position));
            startActivity(intent);
            }})));

    }

    private boolean checkStringEmoty(String par) {
        if (par.trim().isEmpty()) {
            return true;
        } else return false;
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        private ChatAdapter(Context mContext) {
            super(mContext, 0);
        }

        public int getCount() {
            return savedStopNumbers.size();
        }

        public String getItem(int position) {
            return "" + savedStopNumbers.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = SearchActivity.this.getLayoutInflater();
            View result = result = inflater.inflate(R.layout.savedstop, null);

            TextView stopNumber = (TextView) result.findViewById(R.id.Stop_TextView);
            stopNumber.setText("" + savedStopNumbers.get(position));
            ImageButton imageButton = (ImageButton)result.findViewById(R.id.delete_imagebutton);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OCTranspoLab.get(SearchActivity.this).deleteByStopNumber(savedStopNumbers.get(position));
                    savedStopNumbers=OCTranspoLab.get(SearchActivity.this).savedStopList();
                    notifyDataSetChanged();
                }
            });

            return result;

        }

    }
        private class SearchQuery extends AsyncTask<String, Integer, String> {
            private int routeNumber;
            private String routeName;
            int stopNumber = Integer.parseInt(mStop.getText().toString());

            @Override
            protected String doInBackground(String... strings) {

                URL url = null;
                try {

                    url = new URL(URL_STRING + stopNumber);
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
                // Starts the query
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
                    boolean inside = false;
                    if(event==END_DOCUMENT){
                        finish=true;
                    }
                    while (event != END_DOCUMENT) {

                        String name = parser.getName();

                        switch (event) {
                            case START_TAG:

                                if (name.equalsIgnoreCase("Route")) {
                                    inside = true;
                                    publishProgress(25);


                                } else if (name.equalsIgnoreCase("RouteNo")) {
                                    if (inside) {
                                        routeNumber = Integer.parseInt(parser.nextText());
                                        publishProgress(50);


                                    }
                                } else if (name.equalsIgnoreCase("RouteHeading")) {
                                    if (inside) {
                                        routeName = parser.nextText();
                                        OCTranspoLab.get(getApplicationContext()).addRoute(new RouteStop(routeNumber, stopNumber, routeName));
                                        publishProgress(75);
                                        inside = false;

                                    }
                                }


                            case END_TAG:

                                break;

                            default:
                                break;
                        }
                        event = parser.next();
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


                return null;
            }

            protected void onProgressUpdate(Integer... mInt) {

                mProgressBar.setProgress(mInt[0]);
            }

            @Override
            protected void onPostExecute(String a) {
                super.onPostExecute(a);
                mProgressBar.setVisibility(View.INVISIBLE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        savedStopNumbers=OCTranspoLab.get(SearchActivity.this).savedStopList();
                        mChatAdapter.notifyDataSetChanged();
                    }
                });

            }

        }


    }






