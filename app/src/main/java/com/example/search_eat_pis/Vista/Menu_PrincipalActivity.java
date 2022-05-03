package com.example.search_eat_pis.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.search_eat_pis.R;
import com.ramotion.circlemenu.CircleMenuView;

public class Menu_PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        final CircleMenuView circleMenu = findViewById(R.id.circleMenu);
        circleMenu.setEventListener(new CircleMenuView.EventListener(){

            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                //do something...
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationStart(view, buttonIndex);
                switch(buttonIndex){
                    case 0:
                        Toast.makeText(Menu_PrincipalActivity.this, "Botón de perfil ", Toast.LENGTH_SHORT).show();
                        //perfil(view);
                        break;
                    case 1:
                        Toast.makeText(Menu_PrincipalActivity.this, "Botón de restaurantes ", Toast.LENGTH_SHORT).show();
                        restaurantes(view);
                        break;
                    case 2:
                        Toast.makeText(Menu_PrincipalActivity.this, "Botón de cafeterias ", Toast.LENGTH_SHORT).show();
                        cafeteria(view);
                        break;
                    case 3:
                        Toast.makeText(Menu_PrincipalActivity.this, "Botón de bares ", Toast.LENGTH_SHORT).show();
                        bares(view);
                        break;
                    case 4:
                        Toast.makeText(Menu_PrincipalActivity.this, "acerca del programa ", Toast.LENGTH_SHORT).show();
                        //informacion(view);
                        break;


                }
            }
        });




    }
   /* public void perfil(View view){
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }*/

    public void restaurantes(View view){
        Intent rest = new Intent(this, MainMenu_Navegacion.class);
        rest.putExtra("boton","restaurantes");
        startActivity(rest);
    }
    public void cafeteria (View view){
        Intent cafe = new Intent(this, MainMenu_Navegacion.class);
        cafe.putExtra("boton","cafes");
        startActivity(cafe);
    }
    public void bares(View view){
        Intent bar = new Intent(this, MainMenu_Navegacion.class);
        bar.putExtra("boton","bares");
        startActivity(bar);
    }
    /*public void informacion(View view){
        Intent info = new Intent(this, Informacion.class);
        startActivity(info);
    }*/
}