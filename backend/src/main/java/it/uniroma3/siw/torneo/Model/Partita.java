package it.uniroma3.siw.torneo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime dataOra;
    private String luogo;
    private Integer goalsHome;
    private Integer goalsAway;
    private String stato;

    @ManyToOne
    private Torneo torneo;

    @ManyToOne
    private Squadra squadraHome;

    @ManyToOne
    private Squadra squadraAway;

    @ManyToOne
    private Arbitro arbitro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Integer getGoalsHome() {
        return goalsHome;
    }

    public void setGoalsHome(Integer goalsHome) {
        this.goalsHome = goalsHome;
    }

    public Integer getGoalsAway() {
        return goalsAway;
    }

    public void setGoalsAway(Integer goalsAway) {
        this.goalsAway = goalsAway;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Squadra getSquadraHome() {
        return squadraHome;
    }

    public void setSquadraHome(Squadra squadraHome) {
        this.squadraHome = squadraHome;
    }

    public Squadra getSquadraAway() {
        return squadraAway;
    }

    public void setSquadraAway(Squadra squadraAway) {
        this.squadraAway = squadraAway;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dataOra == null) ? 0 : dataOra.hashCode());
        result = prime * result + ((luogo == null) ? 0 : luogo.hashCode());
        result = prime * result + ((goalsHome == null) ? 0 : goalsHome.hashCode());
        result = prime * result + ((goalsAway == null) ? 0 : goalsAway.hashCode());
        result = prime * result + ((stato == null) ? 0 : stato.hashCode());
        result = prime * result + ((torneo == null) ? 0 : torneo.hashCode());
        result = prime * result + ((squadraHome == null) ? 0 : squadraHome.hashCode());
        result = prime * result + ((squadraAway == null) ? 0 : squadraAway.hashCode());
        result = prime * result + ((arbitro == null) ? 0 : arbitro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Partita other = (Partita) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dataOra == null) {
            if (other.dataOra != null)
                return false;
        } else if (!dataOra.equals(other.dataOra))
            return false;
        if (luogo == null) {
            if (other.luogo != null)
                return false;
        } else if (!luogo.equals(other.luogo))
            return false;
        if (goalsHome == null) {
            if (other.goalsHome != null)
                return false;
        } else if (!goalsHome.equals(other.goalsHome))
            return false;
        if (goalsAway == null) {
            if (other.goalsAway != null)
                return false;
        } else if (!goalsAway.equals(other.goalsAway))
            return false;
        if (stato == null) {
            if (other.stato != null)
                return false;
        } else if (!stato.equals(other.stato))
            return false;
        if (torneo == null) {
            if (other.torneo != null)
                return false;
        } else if (!torneo.equals(other.torneo))
            return false;
        if (squadraHome == null) {
            if (other.squadraHome != null)
                return false;
        } else if (!squadraHome.equals(other.squadraHome))
            return false;
        if (squadraAway == null) {
            if (other.squadraAway != null)
                return false;
        } else if (!squadraAway.equals(other.squadraAway))
            return false;
        if (arbitro == null) {
            if (other.arbitro != null)
                return false;
        } else if (!arbitro.equals(other.arbitro))
            return false;
        return true;
    }

    
}
