package com.example.search_eat_pis.Controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.Model.Usuario;

import java.util.ArrayList;

public class ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{
    private Coordenada coordenada;
    private ArrayList<Local> local;
    private Sector sector;
    private Usuario usuario;
    private ArrayList<Reserva> reservas;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public ViewModel(Application application) {
        super(application);
    }

    public void setCoordenada(Coordenada c){this.coordenada = c;}

    public boolean logIn(String correo, String contraseña){
        adapter.getUsuario(correo, contraseña);
    }

    @Override
    public void setSector(ArrayList<Sector> s) {

    }

    @Override
    public void setRestaurantes(ArrayList<Local> l) {

    }

    @Override
    public void setToast(String s) {

    }

    @Override
    public void setReservas(ArrayList<Reserva> r) {

    }

    @Override
    public void setUsuario(Usuario u) {

    }
}
