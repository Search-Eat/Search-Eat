package com.example.search_eat_pis.Model;

import java.lang.Math;

public class Coordenada {
    private double latitud;
    private double longitud;

    public Coordenada(String latitud, String longitud){
        this.latitud = Double.parseDouble(latitud) ;
        this.longitud = Double.parseDouble(longitud);

    }

    public Coordenada(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double distancia(Coordenada cord){

        double lat_ini = latitud * (Math.PI / 180);
        double lon_ini = longitud * (Math.PI / 180);
        double lat_fin = cord.latitud * (Math.PI / 180);
        double lon_fin = cord.longitud * (Math.PI / 180);
        double var_lat = Math.abs(lat_ini - lat_fin);
        double var_lon = Math.abs(lon_ini - lon_fin);

        double dist = (Math.pow(Math.sin(var_lat/2),2) + Math.cos(lat_ini)) * Math.cos(lat_fin) * Math.pow(Math.sin(var_lon/2),2);
        dist = 2 * Math.atan(Math.sqrt(dist) / Math.sqrt(1-dist));
        dist = 6378 * dist;

        return dist;
    }
}
