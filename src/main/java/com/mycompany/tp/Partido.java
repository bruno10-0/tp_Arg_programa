package com.mycompany.tp;

public class Partido {

    private int idPartido = 0;
    private int golEquipo1 = 0;
    private int golEquipo2 = 0;
    private Equipo equipo1=null;
    private Equipo equipo2=null;
    private int resultado = 0;
    public Partido(Equipo equipo1, Equipo equipo2, int golEquipo1, int golEquipo2, int idPartido) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golEquipo1 = golEquipo1;
        this.golEquipo2 = golEquipo2;
        this.idPartido=idPartido;
    }
    
    public void MostarPartido(){
    System.out.println("Partido "+idPartido + ": " + this.equipo1.getNomEquipo() + " vs " + this.equipo2.getNomEquipo());
    }

    public int getGolEquipo1() {
        return golEquipo1;
    }

    public int getGolEquipo2() {
        return golEquipo2;
    }

    public String getEquipo1Nom() {
        return equipo1.getNomEquipo();
    }

    public String getEquipo2Nom() {
        return equipo2.getNomEquipo();
    }

    public int getResultado() {
        return resultado;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public int resultadoPartido() {
        int g1=golEquipo1;
        int g2=golEquipo2;
        if (g1 > g2) {
            resultado = 1;
        } else if (g1==g2) {
            resultado = 0;
        } else {
            resultado = 2;
        }
        return resultado;
    }

}
