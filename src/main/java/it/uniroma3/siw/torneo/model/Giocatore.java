package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Giocatore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String ruolo;
    private Double altezza;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public Double getAltezza() {
        return altezza;
    }

    public void setAltezza(Double altezza) {
        this.altezza = altezza;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Giocatore giocatore = (Giocatore) o;
        return Objects.equals(getId(), giocatore.getId()) && Objects.equals(getNome(), giocatore.getNome()) && Objects.equals(getCognome(), giocatore.getCognome()) && Objects.equals(getDataDiNascita(), giocatore.getDataDiNascita()) && Objects.equals(getRuolo(), giocatore.getRuolo()) && Objects.equals(getAltezza(), giocatore.getAltezza());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCognome(), getDataDiNascita(), getRuolo(), getAltezza());
    }
}
