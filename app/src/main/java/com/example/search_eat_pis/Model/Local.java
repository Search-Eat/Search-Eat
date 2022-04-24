package com.example.test;

import android.util.Log;

import java.util.ArrayList;
import java.lang.String;
import com.example.test.Coordenada;

class Local {

    private final String iD;
    private final String direccion;
    private final ArrayList<String> etiquetas;
    private final Coordenada coordenada;
    private final String nombre;
    private double valoracion;
    private long num_valoraciones;
    private final String foto;
    private final long precio;
    private double distancia;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Local(String id, String direccion, ArrayList<String> etiquetas, double latitud, double longitud, String nombre, Object valoración, long num_valoraciones, String foto, long precio){
        this.iD = id;
        this.direccion = direccion;
        this.etiquetas = etiquetas;
        this.coordenada = new Coordenada(latitud,longitud);
        this.nombre = nombre;
        if(valoración instanceof Long){
            long val = (Long) valoración;
            this.valoracion = (double) val;
        }
        else {
            this.valoracion = (double) valoración;
        }
        this.num_valoraciones = num_valoraciones;
        this.foto = foto;
        this.precio = precio;

    }

    public void setDistancia(Coordenada c){
        double dist = coordenada.distancia(c);
        this.distancia = dist;
    }

    public String getDistancia(){return String.valueOf(distancia);}

    public String getFoto(){return foto;}

    public String getNombre(){return nombre;}

    public double getValoracion(){return valoracion;}

    public long getNumValoraciones(){return num_valoraciones;}

    public String getDireccion(){return direccion;}

    public ArrayList<String> getEtiquetas(){return etiquetas;}

    public long getPrecio(){return precio;}

    public void addValoracion(double num){
        double val = valoracion * num_valoraciones;
        this.num_valoraciones++;
        this.valoracion = (val + num)/ num_valoraciones;
    }

}
