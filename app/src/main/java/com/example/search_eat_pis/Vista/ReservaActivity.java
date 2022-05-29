package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.search_eat_pis.Controller.DatabaseAdapter;
import com.example.search_eat_pis.R;

import java.util.Calendar;
import java.util.Locale;

public class ReservaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView textView_Fecha_Reserva;
    private TextView textView_Hora_Reserva;
    private TextView textView_NumPersonas_Reserva;
    private ImageView imagenLocal;
    private TextView nombreLocal;
    private NumberPicker numberPicker;
    private Button timebutton;
    private int hour, minute;

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
        imagenLocal = findViewById(R.id.imageView_avatar);

        nombreLocal.setText(getIntent().getStringExtra("nombre"));
        DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
        adapter.downloadPhotoFromStorage("locales/",getIntent().getStringExtra("id"),imagenLocal);

        numberPicker.setMaxValue(15);
        numberPicker.setMinValue(0);
        numberPicker.setValue(2);



        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
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
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String fecha = day + "/" + month + "/" + year;
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
        Intent reservar_main = new Intent(this, MainMenu_Navegacion.class);
        startActivity(reservar_main);
    }
}