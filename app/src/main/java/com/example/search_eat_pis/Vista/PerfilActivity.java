package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.search_eat_pis.Controller.ViewModel;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Usuario;
import com.example.search_eat_pis.R;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {
    private ViewModel viewModel;
    private TextView nombre;
    private TextView correo;
    private TextView telefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nombre = findViewById(R.id.nombrePerfil);
        correo = findViewById(R.id.correoPerfil);
        telefono = findViewById(R.id.telefonoPerfil);
        iniPerfil();
        setLiveDataObservers();
        //viewModel.iniReservas();
    }

    public void iniPerfil(){
        nombre.setText("Nombre: " + Usuario.usuario_actual.getNombre());
        correo.setText("Correo: " + Usuario.usuario_actual.getCorreo());
        telefono.setText("Teléfono: " + Long.toString(Usuario.usuario_actual.getTelefono()));
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        final Observer<ArrayList<Reserva>> observerReservas = new Observer<ArrayList<Reserva>>() {
            @Override
            public void onChanged(ArrayList<Reserva> reservas) {

            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(PerfilActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getToast().observe(this, observerToast);
        viewModel.getReservas().observe(this, observerReservas);
    }
}