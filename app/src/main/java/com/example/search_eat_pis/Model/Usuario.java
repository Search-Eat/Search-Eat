package com.example.search_eat_pis.Model;

import android.util.Log;
import java.util.ArrayList;

public class Usuario {
    private String correo;
    private String nombre;
    private long telefono;
    private String contraseña;
    private ArrayList<String> reservas;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Usuario(String correo, String nombre, long telefono, String contraseña, ArrayList<String> reservas){
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.reservas = reservas;
    }

    public ArrayList<String> getReservas(){return reservas;}

    public String getCorreo(){return correo;}

    public void saveUsuario(){
        adapter.saveUsuario(this.correo, this.nombre, this.telefono, this.contraseña, this.reservas);
    }

    public void addReserva(String r){
        this.reservas.add(r);
    }

    public void deleteReserva(String r){
        this.reservas.remove(r);
    }
}
