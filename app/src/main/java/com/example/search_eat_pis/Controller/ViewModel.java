package com.example.search_eat_pis.Controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.Model.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{
    private Coordenada coordenada;
    private MutableLiveData<String> mToast;
    private MutableLiveData<ArrayList<Local>> mLocales;
    private MutableLiveData<Sector> mSector;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<ArrayList<Reserva>> mReservas;
    private MutableLiveData<Boolean> mBoolean;
    DatabaseAdapter da;

    public ViewModel(Application application) {
            super(application);
            mBoolean = new MutableLiveData<>();
            mToast = new MutableLiveData<>();
            mLocales = new MutableLiveData<>();
            mSector = new MutableLiveData<>();
            mUsuario = new MutableLiveData<>();
            mReservas = new MutableLiveData<>();
            da = new DatabaseAdapter(this);
    }

    public void setCoordenada(Coordenada coordenada){ this.coordenada = coordenada;}

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
    public void isValidCorreo(String correo){ da.isValidCorreo(correo);}

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

    @Override
    public void setBoolean(boolean b) {mBoolean.setValue(b);}


    public LiveData<Sector> getSector(){return mSector;}

    public LiveData<ArrayList<Local>> getLocales(){return mLocales;}

    public LiveData<Usuario> getUsuario(){return mUsuario;}

    public LiveData<ArrayList<Reserva>> getReservas(){return mReservas;}

    public LiveData<String> getToast(){return mToast;}

    public LiveData<Boolean> getBoolean(){return mBoolean;}

    public Local getLocal(int idx){return mLocales.getValue().get(idx);}

    public Reserva getReserva(int idx){return mReservas.getValue().get(idx);}

    public void addReserva(String nombre, long telefono, String local, long personas, Calendar cal){
        Reserva reserva = new Reserva(  nombre,
                                        telefono,
                                        local,
                                        personas,
                                (long)  cal.get(Calendar.YEAR),
                                (long)  cal.get(Calendar.MONTH),
                                (long)  cal.get(Calendar.DAY_OF_MONTH),
                                (long)  cal.get(Calendar.HOUR_OF_DAY),
                                (long)  cal.get(Calendar.MINUTE));
        if (reserva != null){
            mReservas.getValue().add(reserva);
            mReservas.setValue(mReservas.getValue());
            reserva.saveReserva();
            mUsuario.getValue().addReserva(reserva.getId());
            mUsuario.getValue().updateUsuarioReserva();
        }
    }
}
