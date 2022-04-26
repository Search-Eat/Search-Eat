package com.example.search_eat_pis.Model;

import java.util.Date;
import java.util.UUID;
import java.util.Calendar;
import java.util.TimeZone;
import com.example.search_eat_pis.Controller.DatabaseAdapter;

public class Reserva {
    private String reservaID;
    private String restaurante;
    private Calendar fecha;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Reserva(String restaurante, long año, long mes, long dia, long hora, long minuto){
        UUID uuid = UUID.randomUUID();
        this.reservaId = uuid.toString();
        this.restaurante = restaurante;
        fecha.set(año,dia,mes,hora,minutos);
    }

    public String setId(String id){this.reservaID = id;}

    public void saveReserva(){
        adapter.saveReserva(reservaID, restaurante, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH), fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE));
    }

    public String getId(){return reservaID;}

    public Calendar getFecha(){return fecha;}

    public String getRestaurante(){return restaurante;}

    public boolean valorar(){
        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        Calendar actual = Calendar.getInstance(tz);
        int tiempo = fecha.compareTo(actual);

        if (tiempo >= 1000*60*30){
            return true;
        }
        return false;
    }
