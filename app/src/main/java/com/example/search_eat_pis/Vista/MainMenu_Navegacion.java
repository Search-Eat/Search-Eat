package com.example.search_eat_pis.Vista;

import com.example.search_eat_pis.Controller.DatabaseAdapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.search_eat_pis.Controller.ViewModel;
import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.Model.Usuario;
import com.example.search_eat_pis.R;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.search_eat_pis.databinding.ActivityMainMenuNavegacionBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenu_Navegacion extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView buscador;
    private ActivityMainMenuNavegacionBinding binding;
    private Sector sectores;
    private LocationManager locManager;
    private Location loc;
    static public Coordenada cordenada;
    private ViewModel viewModel;
    ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_navegacion);
        ultima_ubicación();
        setLiveDataObservers();
        viewModel.iniSector();
        buscador = findViewById(R.id.Buscador);
        buscador.setOnQueryTextListener(this);
    }

    public void init(ArrayList<Local> elements){
         this.listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.lista_cards_restaurantes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }


    /*
    Aquí vemos que botón ha apretado el usuario (bar,cafeteria, restaurante), y escogemos el tipo de
    local más cercano al usuario llamando a los diferentes métodos que tendra DataBaseAdapter.
    */


    public void ultima_ubicación(){

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitud = loc.getLatitude();
            double longitud = loc.getLongitude();
            cordenada = new Coordenada(latitud,longitud);
        }
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.setCoordenada(cordenada);

        final Observer<Sector> observerSector = new Observer<Sector>() {
            @Override
            public void onChanged(Sector sector) {
                viewModel.iniLocal(getIntent().getStringExtra("boton"));
            }
        };

        final Observer< ArrayList<Local>> observerLocal = new Observer< ArrayList<Local>>() {
            @Override
            public void onChanged( ArrayList<Local> l) {
                init(l);
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(MainMenu_Navegacion.this, t, Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getToast().observe(this, observerToast);
        viewModel.getSector().observe(this, observerSector);
        viewModel.getLocales().observe(this, observerLocal);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        listAdapter.filtrado(s);
        return false;
    }
}