package com.example.search_eat_pis.Model;

import java.util.Comparator;

public class ComparadorLocalPrecio implements Comparator<Local> {
    @Override
    public int compare(Local l1, Local l2) {
        return (int) (l1.getPrecio() - l2.getPrecio());
    }
}
