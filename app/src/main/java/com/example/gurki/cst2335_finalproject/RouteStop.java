package com.example.gurki.cst2335_finalproject;

/**
 * Created by gurki on 2018-12-04.
 */

public class RouteStop {

    private int mRoute;
    private int mStop;
    private String mRouteHeading;
    public RouteStop(int route, int stop,String routeHeading) {
        mRoute = route;
        mStop = stop;
        mRouteHeading=routeHeading;
    }

    public int getRoute() {
        return mRoute;
    }

    public void setRoute(int route) {
        mRoute = route;
    }

    public String getRouteHeading() {
        return mRouteHeading;
    }

    public void setRouteHeading(String routeHeading) {
        mRouteHeading = routeHeading;
    }

    public int getStop() {

        return mStop;
    }

    public void setStop(int stop) {
        mStop = stop;
    }
}
