package com.example.shan.onlineorderapp;

import android.os.Bundle;

//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double lat = 1.092698;
        double longtude =34.178923;
        // Add a marker in Mbale, Uganda, and move the camera.
        LatLng iuiu = new LatLng(lat,longtude);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.addMarker(new MarkerOptions().position(iuiu).title("Marker in IUIU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iuiu));
    }






}
