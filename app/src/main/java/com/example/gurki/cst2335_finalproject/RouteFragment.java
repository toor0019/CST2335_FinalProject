package com.example.gurki.cst2335_finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES;
import static org.xmlpull.v1.XmlPullParser.PROCESSING_INSTRUCTION;
import static org.xmlpull.v1.XmlPullParser.START_TAG;



public class RouteFragment extends Fragment {

    private static final String URL_STRING="https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=3017&routeNo=95";
    private static final String STOP_NUMBER = "stop";
    private static final String ROUTE_NUMBER = "route";
    private  ListView mListView;
    private ProgressBar mProgressBar;
    private ChatAdapter mChatAdapter;
    private int mStopNumber;
    private int mRouteNumber;
    private List<OCTranspoRoute> mRoutes;

    public RouteFragment() {
        // Required empty public constructor
    }


    public static RouteFragment newInstance(int stopNumber, int routeNumber) {
        RouteFragment fragment = new RouteFragment();
        Bundle args = new Bundle();
        args.putInt(STOP_NUMBER,stopNumber);
        args.putInt(ROUTE_NUMBER,routeNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoutes = new ArrayList<>();
        if (getArguments() != null) {
            mStopNumber = getArguments().getInt(STOP_NUMBER);
            mRouteNumber = getArguments().getInt(ROUTE_NUMBER);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_route, container, false);

        mListView = (ListView)view.findViewById(R.id.Bus_listView);
        mProgressBar=(ProgressBar)view.findViewById(R.id.busProgressBar) ;
        mProgressBar.setVisibility(View.VISIBLE);
        new RouteLoader().execute();
       return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    private class ChatAdapter extends ArrayAdapter<OCTranspoRoute> {

        private ChatAdapter(Context mContext) {
            super(mContext, 0);
        }

        public int getCount() {
            return mRoutes.size();
        }

        public OCTranspoRoute getItem(int position) {
            return mRoutes.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();

            View result = result = inflater.inflate(R.layout.routeview, null);
            ;


            TextView stopNumber = (TextView) result.findViewById(R.id.routeStopTextView);
            TextView busNumber = (TextView) result.findViewById(R.id.busRouteTextView);
            TextView adjustedSechdule = (TextView) result.findViewById(R.id.adjustedSechduleTimeTextView);
            TextView tripDestination = (TextView) result.findViewById(R.id.tripDestinationTextView);
            TextView tripStartTime = (TextView) result.findViewById(R.id.tripStartTimeTextView);
            TextView latitude = (TextView) result.findViewById(R.id.latitudeTextView);
            TextView longitude = (TextView) result.findViewById(R.id.longitudeTextView);
            TextView gpsspedd = (TextView) result.findViewById(R.id.gpsspeddTextView);

            busNumber.setText("Bus Number " + mRoutes.get(position).getRouteNumber());
            stopNumber.setText("Stop Number" +mRoutes.get(position).getStopNumber());
            adjustedSechdule.setText("Bus is "+mRoutes.get(position).getAdjustedSScheduleTime()+" late");
            tripDestination.setText("Trip Destination is "+mRoutes.get(position).getTripDestination());
            tripStartTime.setText("Trip Start Time is "+mRoutes.get(position).getTripStartTime());
            latitude.setText("Latitude "+mRoutes.get(position).getLatitude());
            longitude.setText("Longitude "+mRoutes.get(position).getLongitude());
            gpsspedd.setText("GPSSpeed "+mRoutes.get(position).getGPSSpeed());

            return result;

        }
    }
    private class RouteLoader extends AsyncTask<String,Integer,String>{
        private String mTripDestination, mTripStartTime, mAdjustedSScheduleTime, mLatitude, mLongitude,mGpsSpeed;
        private  final String URL_STRING ="https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+mStopNumber+"&routeNo="+mRouteNumber;
        @Override
        protected String doInBackground(String... strings) {

            URL url = null;
            try {

                url = new URL(URL_STRING );
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
                while (event != END_DOCUMENT) {
                    String name = parser.getName();

                    switch (event) {
                        case START_TAG:


                            if (name.equalsIgnoreCase("trip")) {
                                inside = true;
                                publishProgress(25);


                            } else if (name.equalsIgnoreCase("tripdestination")) {
                                if (inside) {
                                    mTripDestination =parser.nextText();
                                    publishProgress(30);

                                }
                            } else if (name.equalsIgnoreCase("tripstarttime")) {
                                if (inside) {
                                    mTripStartTime = parser.nextText();
                                    publishProgress(40);
                                }
                            }
                            else if (name.equalsIgnoreCase("adjustedscheduletime")) {
                                if (inside) {
                                    mAdjustedSScheduleTime = parser.nextText();
                                    publishProgress(50);
                                }
                            }
                            else if (name.equalsIgnoreCase("latitude")) {
                                if (inside) {
                                    mLatitude = parser.nextText();
                                    publishProgress(60);
                                }
                            }
                            else if (name.equalsIgnoreCase("longitude")) {
                                if (inside) {
                                    mLongitude = parser.nextText();
                                    publishProgress(70);
                                }
                            }
                            else if (name.equalsIgnoreCase("gpsspeed")) {
                                if (inside) {
                                    double speed;
                                    mGpsSpeed = parser.nextText();
                                    if(mGpsSpeed.isEmpty()){
                                        mGpsSpeed=""+0;
                                    }
                                    speed=Double.parseDouble(mGpsSpeed);
                                    OCTranspoRoute route = new OCTranspoRoute(mStopNumber,mRouteNumber,speed,mTripDestination,mTripStartTime,mAdjustedSScheduleTime,mLatitude,mLongitude);
                                    mRoutes.add(route);}
                                    publishProgress(80);


                                    inside=false;



                            }


                        case END_TAG:
                            publishProgress(100);

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
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChatAdapter = new ChatAdapter(getActivity());
                    mListView.setAdapter(mChatAdapter);

                }
            });

        }
    }
}
