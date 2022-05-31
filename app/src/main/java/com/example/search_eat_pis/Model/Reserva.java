package com.example.search_eat_pis.Model;

import java.util.Date;
import java.util.UUID;
import java.util.Calendar;
import java.util.TimeZone;
import com.example.search_eat_pis.Controller.DatabaseAdapter;

public class Reserva {
    private String reservaID;
    private String localID;

    public String getReservaID() {
        return reservaID;
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
        fecha.set((int) año, (int) dia, (int) mes, (int) hora, (int) minuto);
    }

    public void setId(String id) { this.reservaID = id; }

    public void saveReserva() {
        adapter.saveReserva(reservaID, localID, nombre, telefono, local, personas, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH), fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE));
    }

    public void deleteReserva() {
        adapter.deleteReserva(reservaID);
    }

    public long getPersonas() {return personas; }

    public String getId() {
        return reservaID;
    }

    public Calendar getFecha() {
        return fecha;
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