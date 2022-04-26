package com.example.search_eat_pis.Model;

import java.util.Date;
import java.util.UUID;
import java.util.Calendar;
import java.util.TimeZone;
import com.example.search_eat_pis.Controller.DatabaseAdapter;

public class Reserva {
    private String reservaID;
    private String nombre;
    private String restaurante;
    private long telefono
    private Calendar fecha;
    private long personas;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Reserva(String nombre, long telefono, String restaurante, long personas, long año, long mes, long dia, long hora, long minuto) {
        this.nombre = nombre;
        this.telefono = telefono;
        UUID uuid = UUID.randomUUID();
        this.reservaId = uuid.toString();
        this.restaurante = restaurante;
        fecha.set(año, dia, mes, hora, minutos);
    }

    public String setId(String id) {
        this.reservaID = id;
    }

    public void saveReserva() {
        adapter.saveReserva(reservaID, nombre, telefono, restaurante, personas, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH), fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE));
    }

    public void deleteReserva() {
        adapter.deleteReserva(reservaID);
    }

    public String getId() {
        return reservaID;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public String getRestaurante() {
        return restaurante;
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