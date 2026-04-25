package it.uniroma3.siw.torneo.Model;

public class Classifica {
    private Squadra squadra;
    private int punti;

    public Classifica(Squadra squadra, int punti){
        this.squadra = squadra;
        this.punti = punti;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    

}
