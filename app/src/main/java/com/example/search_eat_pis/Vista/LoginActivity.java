package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.search_eat_pis.R;

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
}