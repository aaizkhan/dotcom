package com.dcservicez.a247services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dcservicez.a247services.Prefs.Prefs;
import com.dcservicez.a247services.debugs.Debug;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class search_service extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Debug debug;
    Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        debug=new Debug(this);
        prefs=new Prefs(this);



    }

    @SuppressLint("MissingPermission")
    private Location getMyLocation() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }
        return myLocation;
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

//        mMap.getMyLocation();
                mMap.setMyLocationEnabled(true);
                Location l=getMyLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(),l.getLongitude())));


//        mMap.setMaxZoomPreference(50.0f);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
//                debug.print("location is changeds "+location.getLongitude()+","+location.getLatitude());

//                Location location=getMyLocation();
//        debug.print("permession is  grandeted");
//                LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());
//                debug.print(prefs.sverc_type());

//                mMap.addMarker(new MarkerOptions().position(sydney).title(prefs.sverc_type()));

                update_location(location);

            }
        });

    }

    public void check_service_near_me(Location location,final String service_type){
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child("service_provider_location");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot data:dataSnapshot.getChildren()) {
                        ref.child(data.getKey().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot data2:dataSnapshot.getChildren()) {
                                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.service);

                                    mMap.addMarker(new MarkerOptions().title(data.getKey().toString()+data2.getKey().toString()).position(new LatLng(Float.parseFloat(data2.child("Latitude").getValue().toString()),Float.parseFloat(data2.child("Longitude").getValue().toString()))));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Query chatRoomsQuery = ref.orderByChild("Latitude").e(32.9853067);
//chatRoomsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        if(dataSnapshot!=null){
//            debug.print("found"+dataSnapshot.getKey());
//        }
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});
    }
    public void update_location(Location location){
        if(prefs.sverc_type().isEmpty()){
            check_service_near_me(location,"Cleaner");
            return;

        }
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child("service_provider_location").child(prefs.sverc_type()).child(prefs.email()).child("Latitude").setValue(location.getLatitude());
        ref.child("service_provider_location").child(prefs.sverc_type()).child(prefs.email()).child("Longitude").setValue(location.getLongitude());


    }
}