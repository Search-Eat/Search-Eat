package com.example.search_eat_pis.Model;

import android.util.Log;
import java.util.ArrayList;
import com.example.search_eat_pis.Controller.DatabaseAdapter;

public class Usuario {
    private String correo;
    private String nombre;
    private long telefono;
    private String contraseña;
    private ArrayList<String> reservas;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    static public Usuario usuario_actual;


    public Usuario(String correo, String nombre, long telefono, String contraseña, ArrayList<String> reservas){
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.reservas = reservas;
        usuario_actual = this;
    }

    public ArrayList<String> getReservas(){return reservas;}

    public String getCorreo(){return correo;}

    public void saveUsuario(){
        adapter.saveUsuario(this.correo, this.nombre, this.telefono, this.contraseña, this.reservas);
    }

    public void addReserva(String r){
        this.reservas.add(r);
    }

    public void updateUsuario(){
        adapter.updateReservas(reservas, correo);
    }

    public void updateUsuarioReserva() { adapter.updateReservas(reservas, correo);}

    public void deleteReserva(String r){
        this.reservas.remove(r);
    }
}
