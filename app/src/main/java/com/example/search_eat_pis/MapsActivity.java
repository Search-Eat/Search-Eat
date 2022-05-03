package com.example.search_eat_pis;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.search_eat_pis.Vista.MainMenu_Navegacion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.search_eat_pis.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        double loc_lat = 0;
        double loc_lon = 0;
        double pos_lat = MainMenu_Navegacion.cordenada.getLatitud();
        double pos_lon = MainMenu_Navegacion.cordenada.getLongitud();
        getIntent().getDoubleExtra("latitud",loc_lat);
        getIntent().getDoubleExtra("longitud",loc_lon);
        // Add a marker in Sydney and move the camera
        if(loc_lat != 0 && loc_lon != 0){
            LatLng posicion = new LatLng(pos_lat, pos_lon);
            LatLng local = new LatLng(loc_lat, loc_lon);
            mMap.addMarker(new MarkerOptions().position(posicion).title("Tú ubicación"));
            mMap.addMarker(new MarkerOptions().position(local).title(getIntent().getStringExtra("nombre")));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
        }
    }
}