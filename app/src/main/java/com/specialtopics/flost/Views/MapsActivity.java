package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

public class MapsActivity extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    ToggleButton listToggle;
    private static final LatLng Fowler = new LatLng(34.126926, -118.211240);
    private static final LatLng Johnson = new LatLng(34.127530, -118.211572);
    private static final LatLng Library = new LatLng(34.126065, -118.211476);
    private static final LatLng MP = new LatLng(34.127793, -118.212401);

    private Marker mFowler;
    private Marker mJohnson;
    private Marker mLibrary;
    private Marker mMP;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Utils utils = new Utils();
    private FragmentTransaction transaction;

    public MapsActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
         //       .findFragmentById(com.specialtopics.flost.R.id.map);
       // mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_maps, null, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(this);
        return view;
    }
    public void setUpToggle(){
        listToggle = getView().findViewById(R.id.toggleButton);
        listToggle.setChecked(true);

        listToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                transaction = getFragmentManager().beginTransaction();
                Utils.loadFragment(new HomeFragment(), R.id.frame_container, transaction);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng oxy = new LatLng(34.126955, -118.211874);

        mFowler = mMap.addMarker(new MarkerOptions().position(Fowler).title("Fowler"));
        mJohnson = mMap.addMarker(new MarkerOptions().position(Johnson).title("Johnson"));
        mLibrary = mMap.addMarker(new MarkerOptions().position(Library).title("Library"));
        mMP = mMap.addMarker(new MarkerOptions().position(MP).title("Market Place"));
        float zoomLevel = 17.5f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oxy, zoomLevel));
        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            public void onInfoWindowClick(Marker marker) {
//                Intent intent1 = new Intent(MapsActivity.this, AnotherActivity.class);
//                String title = marker.getTitle();
//                intent1.putExtra("markertitle", title);
//                startActivity(intent1);
            }
        });
        setUpToggle();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.clear();
        mapFragment.onDestroyView();
    }
}