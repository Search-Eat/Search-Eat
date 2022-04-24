package com.example.test;

import java.util.ArrayList;

class Sector {
    private final Coordenada coordenada;
    private final ArrayList<String> restaurant;
    private final ArrayList<String> bar;
    private final ArrayList<String> cafe;

    public Sector(String latitud, String longitud, ArrayList<String> restaurant, ArrayList<String> bar, ArrayList<String> cafe ){
        this.coordenada = new Coordenada(latitud,longitud);
        this.restaurant = restaurant;
        this.bar = bar;
        this.cafe = cafe;
    }

    public ArrayList<String> getRestaurantes(){
        return restaurant;
    }

    public ArrayList<String> getBares(){
        return bar;
    }

    public ArrayList<String> getCafes(){
        return cafe;
    }

    public Coordenada getCoordenada(){
        return coordenada;
    }
}
