package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.search_eat_pis.Controller.ViewModel;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Usuario;
import com.example.search_eat_pis.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PerfilActivity extends AppCompatActivity implements ListAdapter_Reserva.playerInterface {
    private ViewModel viewModel;
    private TextView nombre;
    private TextView correo;
    private TextView telefono;
    private ListAdapter_Reserva listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombre = findViewById(R.id.nombrePerfil);
        correo = findViewById(R.id.correoPerfil);
        telefono = findViewById(R.id.telefonoPerfil);


        iniPerfil();
        setLiveDataObservers();
        if(!Usuario.usuario_actual.getReservas().isEmpty()) {
            viewModel.iniReservas(Usuario.usuario_actual.getReservas());
        }

    }
    public void init(ArrayList<Reserva> elements){
        listAdapter = new ListAdapter_Reserva(elements,this, (ListAdapter_Reserva.playerInterface) this);
        RecyclerView recyclerView = findViewById(R.id.rvPerfil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
    public void iniPerfil(){
        nombre.setText("Nombre: " + Usuario.usuario_actual.getNombre());
        correo.setText("Correo: " + Usuario.usuario_actual.getCorreo());
        correo.setTextSize(TypedValue.COMPLEX_UNIT_SP,nombre.getTextSize());
        telefono.setText("Teléfono: " + Long.toString(Usuario.usuario_actual.getTelefono()));
        telefono.setTextSize(TypedValue.COMPLEX_UNIT_SP,nombre.getTextSize());
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        final Observer<ArrayList<Reserva>> observerReservas = new Observer<ArrayList<Reserva>>() {
            @Override
            public void onChanged(ArrayList<Reserva> reservas) {
                ArrayList<String> locales = new ArrayList<>();
                Iterator it = reservas.iterator();
                while(it.hasNext()){
                    Reserva reserva = (Reserva) it.next();
                    locales.add(reserva.getLocalID());
                }
                viewModel.iniLocales(locales);
                init(reservas);
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

    @Override
    public void valorar(int reserva) {
        showPopup(reserva);
    }

    @Override
    public void eliminar(int reserva) {
        viewModel.eliminarReserva(reserva);
    }

    public void showPopup(int idx) {

        View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);
        Button valorar = popupView.findViewById(R.id.boton_popup);
        valorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valoración = ratingBar.getNumStars();
                viewModel.valorar(idx, valoración);
                popupWindow.dismiss();
            }
        });
    }
}