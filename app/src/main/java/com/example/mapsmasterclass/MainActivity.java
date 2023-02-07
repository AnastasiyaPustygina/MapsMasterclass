package com.example.mapsmasterclass;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mapsmasterclass.db.DBImitation;
import com.example.mapsmasterclass.domain.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap myMap;
    private final Place[] places = DBImitation.places;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Toast.makeText(this, latLng.latitude + " " + latLng.longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        myMap.setOnMapClickListener(this);
        myMap.setOnMapLongClickListener(this);
        for (Place place: places) {
            myMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
        }
        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.fragment_bottom_sheet);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                Place place = DBImitation.findByName(marker.getTitle());

                TextView tv_name = dialog.getWindow().findViewById(R.id.tv_name);
                TextView tv_address = dialog.getWindow().findViewById(R.id.tv_address);
                TextView tv_description = dialog.getWindow().findViewById(R.id.tv_description);
                ImageView imageView = dialog.getWindow().findViewById(R.id.iv_image);

                tv_name.setText(place.getName());
                tv_address.setText(place.getAddress());
                tv_description.setText(place.getInformation());

                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://maps-e3c2e.appspot.com");
                StorageReference reference = firebaseStorage.getReference(place.getPathToImage());
                Glide.with(MainActivity.this).load(reference).into(imageView);
                return false;
            }
        });
    }
}