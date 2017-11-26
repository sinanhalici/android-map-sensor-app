package com.example.jiaheng.mapsensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiaheng on 4/2/17.
 */

public class Global {
    public static final int NONE_PLACE = 110;

    public static List<Place> placeList = new ArrayList<>();

    public class FragmentType {
        public static final int DATE_FRAGMENT = 100;
        public static final int DESCRIPTION_FRAGMENT = 101;
        public static final int MAP_FRAGMENT = 102;
    }

    static {
        Place lane = new Place(111, 37.219462, -80.418023, "Lane Stadium");
        lane.setDescription("Lane Stadium is a stadium located in Blacksburg, in the U.S. state of Virginia. It is the home field of the Virginia Tech Hokies. It was rated the number one home field advantage in all of college football in 2005 by Rivals.com");
        lane.setImageId(R.drawable.lane);
        placeList.add(lane);

        Place burruss = new Place(112, 37.228355, -80.423065, "Burrus Hall");
        burruss.setDescription("Burruss Hall is the main administration building on campus. It contains a 3,003-seat auditorium, a venue where major events such as commencement, presidential speeches, concerts, and arts shows are held.");
        burruss.setImageId(R.drawable.burrus);
        placeList.add(burruss);

        Place moss = new Place(113, 37.231849, -80.418066, "Moss Art Center");
        moss.setDescription("The Moss Arts Center is creating a thriving creative community fueled by inspiration, where patrons have meaningful experiences, enjoying arts of the highest caliber in a wide variety of forms. ");
        moss.setImageId(R.drawable.moss);
        placeList.add(moss);

        Place mekong = new Place(114, 37.216132, -80.399647, "Cafe Mekong");
        mekong.setDescription("Vietnamese, Laotian & Thai cuisine offered in a simple strip-mall eatery with booth seating.");
        mekong.setImageId(R.drawable.mekong);
        placeList.add(mekong);

        Place pheasant = new Place(115, 37.249148, -80.418188, "Pheasant Run");
        pheasant.setDescription("This is my home, at Pheasant Run Drive");
        pheasant.setImageId(R.drawable.pheasant);
        placeList.add(pheasant);

        Place surge = new Place(116, 37.233052, -80.423123, "Surge Space Building");
        surge.setDescription("The building provides space to temporarily house academic or administrative units that are displaced because of renovations to their home buildings. The facility was designed using a pre-engineered steel frame with its exterior clad in metal panels and precast concrete-like panel accents. The building is designed to be disassembled and recycled in 10 or 15 years.");
        surge.setImageId(R.drawable.surge);
        placeList.add(surge);

    }

}
