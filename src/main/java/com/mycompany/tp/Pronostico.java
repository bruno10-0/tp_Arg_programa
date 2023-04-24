package com.mycompany.tp;

public final class Pronostico {
    private String usuario;
    private Partido partido;
    private Equipo equipo;
    private int voto;
    private int puntos;
    public Pronostico(Partido partido, Equipo equipo,String usuario,int voto,int puntos) {
        this.equipo = equipo;
        this.partido = partido;
        this.usuario=usuario;
        this.voto=voto;
        this.puntos=puntos;
        //pronosticos.add(proAux);
        //System.out.println("");
        //mostrarEquipo();
        //mostrarResultadoPartido();
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public void mostrarEquipo() {
        System.out.print("\n"+usuario+" voto por: " + equipo.getNomEquipo());
        System.out.println(", conocido por ser " + equipo.getDescri());
    }
    public int puntosGanados(){
        int Puntos=0;
        int auxVoto=voto;
        int AuxResPartido=partido.resultadoPartido();
        if(AuxResPartido==auxVoto){
            Puntos=Puntos+2;
        }
        return this.puntos=Puntos;
    }

    public void mostrarResultadoPartido() {
        //System.out.println("Los resultado del enfrentamiento son:");
        System.out.println(partido.getEquipo1Nom() + " " + partido.getGolEquipo1() + " vs " + partido.getGolEquipo2() + partido.getEquipo2Nom() +"\n");
        if(partido.getGolEquipo1()>partido.getGolEquipo2()){
            System.out.println(" Ganó: "+partido.getEquipo1Nom());
        }else if(partido.getGolEquipo1()==partido.getGolEquipo2()){
            System.out.println("Empate");
        }else{
            System.out.println( "Ganó: "+partido.getEquipo2Nom());
        }
        puntos=puntosGanados();
        System.out.println("Sus puntos son: "+puntos);
    }
    
}
