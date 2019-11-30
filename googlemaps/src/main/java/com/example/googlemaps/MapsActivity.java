package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int REQUEST_LOCATION_PERMISSION = 1;

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

        // Add a marker in Sydney and move the camera
        LatLng tokyo = new LatLng(35.6684415, 139.6007807);
        mMap.addMarker(new MarkerOptions().position(tokyo).title("Tokyo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 6F));

        setmMapTitle();
        //removeMarker();

        setPointOfInterestClick(mMap);
        enableMyLocation();
        showMapDetails();
    }

    private void setMatClick() {
        mMap.setOnMapLongClickListener(latLng -> {
            mMap.addMarker(new MarkerOptions().position(latLng));
        });
    }

    private void removeMarker() {
        mMap.setOnMarkerClickListener(marker -> {
            marker.remove();
            return true;
        });
    }

    private void setmMapTitle() {
        mMap.setOnMapLongClickListener(latLng -> {
            String title = String.format(Locale.getDefault(), "Lat: %1$.5f, Long: %2$.5f", latLng.latitude, latLng.longitude);

            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.localizacao))
                    .snippet(title));

        });
    }

    private void setPointOfInterestClick(GoogleMap map) {
        mMap.setOnPoiClickListener(pointOfInterest -> {
            Marker poi = map.addMarker(new MarkerOptions()
                    .position(pointOfInterest.latLng)
                    .title(pointOfInterest.name));

            poi.showInfoWindow();

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void enableMyLocation() {
        String[] permission = Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION).toArray(new String[0]);

        if (isPermissionGranted()) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    permission, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void showMapDetails() {
        mMap.setOnInfoWindowClickListener(marker -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);
        });
    }
}
