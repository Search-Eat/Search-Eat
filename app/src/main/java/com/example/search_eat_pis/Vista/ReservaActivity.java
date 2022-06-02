package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.search_eat_pis.Controller.DatabaseAdapter;
import com.example.search_eat_pis.Controller.ViewModel;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Usuario;
import com.example.search_eat_pis.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ReservaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView textView_Fecha_Reserva;
    private TextView textView_Hora_Reserva;
    private TextView textView_NumPersonas_Reserva;
    private TextView nombre, telefono;
    private ImageView imagenLocal;
    private TextView nombreLocal;
    private NumberPicker numberPicker;
    private Button timebutton;
    private ViewModel viewModel;
    private int hour, minute, year, month, day, personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        textView_Fecha_Reserva = findViewById(R.id.textView_fecha_Reserva);
        textView_Hora_Reserva = findViewById(R.id.textView_hora_Reserva);
        timebutton = findViewById(R.id.button_hora_Reserva);
        textView_NumPersonas_Reserva = findViewById(R.id.textView_numPersonas_Reserva);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        nombreLocal = findViewById(R.id.textView_NombreRest_Reserva);
        nombre = findViewById(R.id.editTextNombreApellidos_Reserva);
        telefono = findViewById(R.id.editTextNumTel_Reserva);
        imagenLocal = findViewById(R.id.imageView_avatar);

        nombre.setText("Nombre: " + Usuario.usuario_actual.getNombre());
        telefono.setText("Teléfono: " + Long.toString(Usuario.usuario_actual.getTelefono()));
        nombreLocal.setText(getIntent().getStringExtra("nombre"));
        setLiveDataObservers();
        DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
        adapter.downloadPhotoFromStorage("locales/",getIntent().getStringExtra("id"),imagenLocal);

        numberPicker.setMaxValue(15);
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);



        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                personas = newValue;
                textView_NumPersonas_Reserva.setText("Sois " + newValue + " personas");
            }
        });

        findViewById(R.id.button_fecha_Reserva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendario();
            }
        });
    }
    private void showCalendario(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
         datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        String fecha = day + "/" + (month+1) + "/" + year;
        textView_Fecha_Reserva.setText(fecha);
    }

    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                textView_Hora_Reserva.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Selecciona la Hora");
        timePickerDialog.show();
    }
    public void Reservar(View view){
        if(validData()){
            viewModel.addReserva(getIntent().getStringExtra("id"),
                    Usuario.usuario_actual.getNombre(),
                    Usuario.usuario_actual.getTelefono(),
                    getIntent().getStringExtra("nombre"),
                    (long) personas,
                    (long) year,
                    (long) month,
                    (long) day,
                    (long) hour,
                    (long) minute);
        }
        else{
            Toast.makeText(ReservaActivity.this, "Los datos de la reserva no son válidos.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validData(){
        if (personas == 0){
            return false;
        }

        Calendar fecha = Calendar.getInstance();
        fecha.set(year,month,day,hour,minute);

        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        Calendar actual = Calendar.getInstance(tz);

        long tiempoReserva = tiempoReal(fecha);
        long tiempoActual = tiempoReal(actual);

        if(tiempoActual > tiempoReserva){
            return false;
        }
        return true;
    }

    public long tiempoReal(Calendar cal){
        long l = (cal.get(Calendar.YEAR) * 365 * 24 * 60 +
                cal.get(Calendar.DAY_OF_YEAR) * 24 * 60 +
                cal.get(Calendar.HOUR_OF_DAY) * 60 +
                cal.get(Calendar.MINUTE));
        return l;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(ReservaActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getToast().observe(this, observerToast);
    }
}