package com.example.jiaheng.mapsensor;

/**
 * Created by Jiaheng on 4/2/17.
 */

public class Calc {

    public static double calculateAngle(double lat1, double lon1, double lat2, double lon2) {

        double dLon = (lon2 - lon1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }

}
