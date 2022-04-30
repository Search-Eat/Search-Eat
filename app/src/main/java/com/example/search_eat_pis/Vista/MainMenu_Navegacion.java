package com.example.search_eat_pis.Vista;

import com.example.search_eat_pis.Controller.DatabaseAdapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;


import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.R;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.search_eat_pis.databinding.ActivityMainMenuNavegacionBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenu_Navegacion extends AppCompatActivity implements DatabaseAdapter.vmInterface {
    private ActivityMainMenuNavegacionBinding binding;
    private List<Local> elements;
    private Sector sectores;
    private LocationManager locManager;
    private Location loc;
    private Coordenada cordenada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_navegacion);
        ultima_ubicación();
        initialize_elements();
        init();

    }

    public void init(){
    ListAdapter listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.lista_cards_restaurantes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }


    /*
    Aquí vemos que botón ha apretado el usuario (bar,cafeteria, restaurante), y escogemos el tipo de
    local más cercano al usuario llamando a los diferentes métodos que tendra DataBaseAdapter.
    */
    public void initialize_elements(){

        elements = new ArrayList<>(); //limpiamos toda la lista

        if(getIntent().getExtras()==null){
            elements = null;
        }
        else{
            if(getIntent().getStringExtra("boton").equals("restaurante")){

                // base de datos con lista de restaurantes cercanos;
                // elements = new ArrayList<>(funcion);

            }
            else if(getIntent().getStringExtra("boton").equals("cafe")){

                // base de datos con lista de cafeterias cercanas;
                // elements = new ArrayList<>(funcion);
            }
            else if(getIntent().getStringExtra("boton").equals("bares")){

                // base de datos con lista de bares cercanos;
                // elements = new ArrayList<>(funcion);


            }
            else{
                elements = null;
            }
        }

    }

    public void ultima_ubicación(){

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitud = loc.getLatitude();
            double longitud = loc.getLongitude();
            this.cordenada = new Coordenada(latitud,longitud);
        }
    }


    @Override
    public void setSector(ArrayList<Sector> s) {
        double dist = Float.POSITIVE_INFINITY;
        Iterator it = s.iterator();
        while(it.hasNext()){
            Sector sec = (Sector) it.next();
            if (dist > cordenada.distancia(sec.getCoordenada())){
                sectores = sec;
                dist = cordenada.distancia(sec.getCoordenada());
            }
        }
    }

    @Override
    public void setRestaurantes(ArrayList<Local> l) {
        System.out.println(asd);
    }

    @Override
    public void setToast(String s) {


    }
}