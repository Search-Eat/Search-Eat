package com.example.search_eat_pis.Model;

import java.util.Comparator;

public class ComparadorLocalValoracion implements Comparator<Local> {
    @Override
    public int compare(Local l1, Local l2) {
        return (int) ((l2.getValoracion()*10 - l1.getValoracion()*10));
    }
}
