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

public class RegisterActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private EditText editTextNombre_Register;
    private EditText editTextPassword_Register;
    private EditText editTextEmail_Register;
    private EditText editTextNumTel_Register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setLiveDataObservers();
        editTextNombre_Register = findViewById(R.id.editTextNombre_Register);
        editTextPassword_Register = findViewById(R.id.editTextPassword_Register);
        editTextEmail_Register = findViewById(R.id.editTextEmail_Register);
        editTextNumTel_Register = findViewById(R.id.editTextNumTel_Register);
    }

    public void changeActivity(){
        Intent register_login = new Intent(this, LoginActivity.class);
        startActivity(register_login);
    }

    //Metodo SignUp boton
    public void SignUp(View view){
        viewModel.isValidCorreo(editTextEmail_Register.getText().toString());
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        final Observer<Boolean> observerBoolean = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (!b){
                    Toast.makeText(RegisterActivity.this, "El correo introducido ya esta registrado", Toast.LENGTH_SHORT).show();
                } else{
                    ArrayList<String> arrayList = new ArrayList<>();
                    Usuario usuario = new Usuario(editTextEmail_Register.getText().toString(), editTextNombre_Register.getText().toString(), Long.parseLong(editTextNumTel_Register.getText().toString()), editTextPassword_Register.getText().toString(), arrayList);
                    usuario.saveUsuario();
                    changeActivity();
                }
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(RegisterActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getToast().observe(this, observerToast);
        viewModel.getBoolean().observe(this, observerBoolean);
    }
}