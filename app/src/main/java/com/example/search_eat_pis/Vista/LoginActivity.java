package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.search_eat_pis.Controller.ViewModel;
import com.example.search_eat_pis.Model.Usuario;
import com.example.search_eat_pis.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private EditText editTextEmail_LogIn;
    private EditText editTextPassword_LogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setLiveDataObservers();
        editTextEmail_LogIn = findViewById(R.id.editTextEmail_LogIn);
        editTextPassword_LogIn = findViewById(R.id.editTextPassword_LogIn);
    }

    public void changeActivity(){
        Intent login_mainmenu = new Intent(this, Menu_PrincipalActivity.class);
        startActivity(login_mainmenu);
    }

    //Metodo LogIn boton
    public void LogIn(View view){
        viewModel.iniUsuario(editTextEmail_LogIn.getText().toString(), editTextPassword_LogIn.getText().toString());

    }

    //Metodo Register boton
    public void Register(View view){
        Intent login_register = new Intent(this, RegisterActivity.class);
        startActivity(login_register);
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        final Observer<Usuario> observerUsuario = new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {
                if (u == null){
                    Toast.makeText(LoginActivity.this, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                } else{
                    changeActivity();
                }

            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(LoginActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getToast().observe(this, observerToast);
        viewModel.getUsuario().observe(this, observerUsuario);
    }
}