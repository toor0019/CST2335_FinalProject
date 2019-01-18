package com.example.gurki.cst2335_finalproject;

/**
 * Created by gurki on 2018-11-19.
 */

public class OCTranspoSchema {

    public static final class Stops{
        public static final String NAME = "stops";

        public static final class Cols {
            public static final String STOP_NUMBER="stopNumber";
            public static final String RouteID="routeID";
            public static final String RouteName="routeName";
        }
    }

    public static final class RouteTable{
        public static final String NAME = "routes";

        public static final class Cols {
            public static final String RoteID="routeID";
            public static final String DESTINATION="destination";
            public static final String GPS_SPEED = "speed";
            public static final String START_TIME = "time";
            public static final String  LATITUDE= "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String ADJUSTED_SCHEDULE_TIME = "adjustedscheduletime";

        }
    }
}
