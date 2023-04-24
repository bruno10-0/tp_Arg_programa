package com.mycompany.tp;

public class Equipo {

    private int id_equipo = 0;
    private String nomEquipo="";
    private String descri="";
    Equipo(String nomEquipo, String descri, int id_equipo) {
        this.nomEquipo = nomEquipo;
        this.descri = descri;
        this.id_equipo = id_equipo;
    }

    public String getNomEquipo() {
        return nomEquipo;
    }

    public String getDescri() {
        return descri;
    }

    public int getId_equipo() {
        return id_equipo;
    }

}
