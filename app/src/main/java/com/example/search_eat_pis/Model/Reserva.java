package com.example.search_eat_pis.Model;

import java.util.Date;
import java.util.UUID;
import java.util.Calendar;
import java.util.TimeZone;
import com.example.search_eat_pis.Controller.DatabaseAdapter;

public class Reserva {
    private String reservaID;
    private String localID;
    private String nombre;
    private String local;
    private long telefono;
    private Calendar fecha;
    private long personas;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Reserva(String localID, String nombre, long telefono, String local, long personas, long año, long mes, long dia, long hora, long minuto) {
        this.localID = localID;
        this.nombre = nombre;
        this.telefono = telefono;
        UUID uuid = UUID.randomUUID();
        this.reservaID = uuid.toString();
        this.local = local;
        this.personas = personas;
        fecha = Calendar.getInstance();
        fecha.set((int) año, (int) mes, (int) dia, (int) hora, (int) minuto);
    }

    public void setId(String id) { this.reservaID = id; }

    public void saveReserva() {
        adapter.saveReserva(reservaID, localID, nombre, telefono, local, personas, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH), fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE));
    }

    public String getLocalID() {
        return localID;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public long getPersonas() {return personas; }

    public String getId() {
        return reservaID;
    }

    public String getFecha() {
        String sFecha = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH))
                + "/" + Integer.toString(fecha.get(Calendar.MONTH))
                + "/" + Integer.toString(fecha.get(Calendar.YEAR))
                + " " + Integer.toString(fecha.get(Calendar.HOUR_OF_DAY))
                + ":" + Integer.toString(fecha.get(Calendar.MINUTE));
        return sFecha;
    }

    public String getLocal() {
        return local;
    }

    public boolean valorar() {
        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        Calendar actual = Calendar.getInstance(tz);
        int tiempo = fecha.compareTo(actual);

        if (tiempo >= 1000 * 60 * 30) {
            return true;
        }
        return false;
    }
}