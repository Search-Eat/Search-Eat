package com.example.search_eat_pis.Controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.Model.Usuario;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{
    private Coordenada coordenada;
    private MutableLiveData<String> mToast;
    private MutableLiveData<ArrayList<Local>> mLocales;
    private MutableLiveData<Sector> mSector;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<ArrayList<Reserva>> mReservas;
    DatabaseAdapter da;

    public ViewModel(Application application, Coordenada coordenada) {
            super(application);
            this.coordenada = coordenada;
            da = new DatabaseAdapter(this);
    }


    public void iniSector(){
        da.getSectores();
    }

    public void iniUsuario(String correo, String contraseña){
        da.getUsuario(correo, contraseña);
    }

    public void iniLocal(String tipoLocal){
        ArrayList<String> sLocales;

        if(tipoLocal.equals("restaurantes")){
            sLocales = mSector.getValue().getRestaurantes();
            da.getLocales(sLocales, coordenada);
        }
        else if(tipoLocal.equals("bares")){
            sLocales = mSector.getValue().getBares();
            da.getLocales(sLocales, coordenada);
        }
        else if(tipoLocal.equals("cafes")){
            sLocales = mSector.getValue().getCafes();
            da.getLocales(sLocales, coordenada);
        }
    }

    public void iniReservas(){
        ArrayList<String> mReservas = mUsuario.getValue().getReservas();
        if(!mReservas.isEmpty()){
            da.getReservas(mReservas);
        }
    }

    @Override
    public void setSector(ArrayList<Sector> s) {
        double dist = Float.POSITIVE_INFINITY;
        Iterator it = s.iterator();
        Sector sector = null;

        while(it.hasNext()){
            Sector sec = (Sector) it.next();
            if (dist > coordenada.distancia(sec.getCoordenada())){
                sector = sec;
                dist = coordenada.distancia(sec.getCoordenada());
            }
        }
        if (sector.equals(null)){
            mToast.setValue("No se ha encontrado el sector");
        }
        else{
            mSector.setValue(sector);
        }
    }

    @Override
    public void setLocales(ArrayList<Local> l) {
        mLocales.setValue(l);
    }

    @Override
    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setReservas(ArrayList<Reserva> r) {
        mReservas.setValue(r);
    }

    @Override
    public void setUsuario(Usuario u) {
        mUsuario.setValue(u);
    }

    public Sector getSector(){return mSector.getValue();}

    public ArrayList<Local> getLocales(){return mLocales.getValue();}

    public Usuario getUsuario(){return mUsuario.getValue();}

    public ArrayList<Reserva> getReservas(){return mReservas.getValue();}

    public String getToast(){return mToast.getValue();}
}
