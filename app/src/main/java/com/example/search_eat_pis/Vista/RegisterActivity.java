package com.example.search_eat_pis.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.search_eat_pis.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //Metodo SignUp boton
    public void SignUp(View view){
        Intent register_login = new Intent(this, LoginActivity.class);
        startActivity(register_login);
    }
}