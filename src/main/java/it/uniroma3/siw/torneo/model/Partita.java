package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @OneToMany
    private List<Commento> commentiDellaPartita;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    public Squadra getSquadraAway() {
        return squadraAway;
    }

    public void setSquadraAway(Squadra squadraAway) {
        this.squadraAway = squadraAway;
    }

    public Squadra getSquadraHome() {
        return squadraHome;
    }

    public void setSquadraHome(Squadra squadraHome) {
        this.squadraHome = squadraHome;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
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

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public List<Commento> getCommentiDellaPartita() {
        return commentiDellaPartita;
    }

    public void setCommentiDellaPartita(List<Commento> commentiDellaPartita) {
        this.commentiDellaPartita = commentiDellaPartita;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partita partita = (Partita) o;
        return Objects.equals(getId(), partita.getId()) && Objects.equals(getDataOra(), partita.getDataOra()) && Objects.equals(getLuogo(), partita.getLuogo()) && Objects.equals(getGoalsHome(), partita.getGoalsHome()) && Objects.equals(getGoalsAway(), partita.getGoalsAway()) && Objects.equals(getStato(), partita.getStato()) && Objects.equals(getTorneo(), partita.getTorneo()) && Objects.equals(getSquadraHome(), partita.getSquadraHome()) && Objects.equals(getSquadraAway(), partita.getSquadraAway()) && Objects.equals(getArbitro(), partita.getArbitro());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDataOra(), getLuogo(), getGoalsHome(), getGoalsAway(), getStato(), getTorneo(), getSquadraHome(), getSquadraAway(), getArbitro());
    }
}
