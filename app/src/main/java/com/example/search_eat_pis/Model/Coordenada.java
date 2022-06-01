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

    public double distancia(Coordenada c){
        double lat1 = this.latitud;
        double lat2 = c.latitud;
        double lon1 = this.longitud;
        double lon2 = c.longitud;

        double d = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1));
        double dist = 111.18 * d;

        return Math.abs(dist);
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
