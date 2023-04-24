package com.mycompany.tp;

import java.util.ArrayList;

public class Ronda {
private int nroRonda=0;
    ArrayList<Partido> listaFulbitos = new ArrayList<Partido>();

    public Ronda(Partido partido1,Partido partido2,Partido partido3,int nroRonda) {
    this.listaFulbitos.add(partido1);
    this.listaFulbitos.add(partido2);
    this.listaFulbitos.add(partido3);
    this.nroRonda=nroRonda;
    }
    
}
