package com.example.mapsmasterclass.db;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mapsmasterclass.domain.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;

public class DBImitation {

    public static Place[] places = {new Place(1, "Canada", "/Canada.jpg",
            "Canada Address", "Canada info", new LatLng(43.7001, 79.4163)),
            new Place(2, "Japan", "/Japan.jpg",
                    "Japan Address", "Japan info", new LatLng(35.6895, 139.692)),
    };

    public static String linkFirebase = "gs://maps-e3c2e.appspot.com";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Place findByName(String name){

        for (Place place: places) {
            if(place.getName().equals(name)) return place;
        }
        // stream api - быстрый способ пройтись по коллекции, сделав выборку/обработку/группировку/... с данными
        // ссылка на информацию - https://metanit.com/java/tutorial/10.1.php
//        return Arrays.stream(places).filter(place -> place.getName().equals(name)).findFirst().get();
        return null;
    }

}
