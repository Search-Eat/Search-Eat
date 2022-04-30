package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.search_eat_pis.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Metodo LogIn boton
    public void LogIn(View view){
        Intent login_mainmenu = new Intent(this, MainMenu_Navegacion.class);            //Hay que cambiar lo de MainMenu_Navegacion por MainMenu
        startActivity(login_mainmenu);
    }

    //Metodo Register boton
    public void Register(View view){
        Intent login_register = new Intent(this, RegisterActivity.class);
        startActivity(login_register);
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.setCoordenada(coordenada);

        final Observer<Sector> observerSector = new Observer<Sector>() {
            @Override
            public void onChanged(Sector s) {
                viewModel.iniLocal("restaurantes");
            }
        };

        final Observer<ArrayList<Local>> observer = new Observer<ArrayList<Local>>() {
            @Override
            public void onChanged(ArrayList<Local> l) {
                CustomAdapter newAdapter = new CustomAdapter(parentContext, l, (CustomAdapter.playerInterface) mActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };
        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getSector().observe(this,observerSector);
        viewModel.getLocales().observe(this, observer);
        viewModel.getToast().observe(this, observerToast);

        viewModel.iniSector();
    }
}