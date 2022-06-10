package com.example.search_eat_pis.Model;

import java.util.Comparator;

public class ComparadorLocalPrecio implements Comparator<Local> {
    @Override
    public int compare(Local l1, Local l2) {
        int val1 =  (int) l1.getPrecio();
        int val2 =  (int) l2.getPrecio();
        if (val1 == -1){//Valor -1 es que no tiene precio asignado
            val1 = 100;
        }
        if (val2 == -1){
            val2 = 100;
        }
        return val1 - val2;
    }
}
