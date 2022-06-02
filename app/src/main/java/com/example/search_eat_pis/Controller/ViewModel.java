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
    private DatabaseAdapter da;

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

    public void iniLocales(ArrayList<String> locales){ da.getLocales(locales, new Coordenada(0,0));}

    public void iniReservas(ArrayList<String> reservas){
        da.getReservas(reservas);
    }

    public void comprobarReservas(){
        Iterator it = mReservas.getValue().iterator();
        while(it.hasNext()){
            Reserva res = (Reserva) it.next();
            if(res.valorar()){
            }
        }
    }

    @Override
    public void setSector(ArrayList<Sector> s) {
        ArrayList<String> restaurantes = new ArrayList<>();
        ArrayList<String> bares = new ArrayList<>();
        ArrayList<String> cafes = new ArrayList<>();
        Coordenada c = null;
        Iterator it = s.iterator();

        while(it.hasNext()){
            Sector sec = (Sector) it.next();
            if (1 >= coordenada.distancia(sec.getCoordenada())){
                restaurantes.addAll(sec.getRestaurantes());
                bares.addAll(sec.getBares());
                cafes.addAll(sec.getCafes());
                c = sec.getCoordenada();
            }
        }
        if (c.equals(null)){
            mToast.setValue("No se ha encontrado el sector");
        }
        else{
            Sector superSector = new Sector(Double.toString(c.getLatitud()),Double.toString(c.getLongitud()),restaurantes,bares,cafes);
            mSector.setValue(superSector);
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

    public Local getLocal(String id){
        Local local = null;
        Iterator it = mLocales.getValue().iterator();
        while (it.hasNext()){
            Local l = (Local) it.next();
            if (l.getiD().equals(id)){
                local = l;
            }
        }
        return local;
    }

    public void valorar(int idx, int valoración){
        Reserva reserva = getReserva(idx);
        if(reserva.valorar()){
            Local local = getLocal(reserva.getLocalID());
            if (local == null){
                setToast("No se ha podido valorar el local.");
            }
            else{
                local.addValoracion(valoración);
                local.updateValoraciones();
            }
        }
        else{
            setToast("La valoración no está disponible.");
        }
    }

    public void eliminarReserva(int idx){
        Reserva reserva = getReserva(idx);
        Usuario.usuario_actual.deleteReserva(reserva.getId());
        da.deleteReserva(reserva.getId());
        ArrayList<Reserva> reservas = mReservas.getValue();
        reservas.remove(reserva);
        mReservas.setValue(reservas);
    }

    public void addReserva(String localID, String nombre, long telefono, String local, long personas, long año, long mes, long dia, long hora, long minuto){
        Reserva reserva = new Reserva(localID,nombre,telefono,local,personas,año,mes,dia,hora,minuto);
        if (reserva != null){
            reserva.saveReserva();
            Usuario.usuario_actual.addReserva(reserva.getId());
            Usuario.usuario_actual.saveUsuario();
        }
    }
}
