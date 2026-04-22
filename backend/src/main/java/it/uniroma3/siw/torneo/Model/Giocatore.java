package it.uniroma3.siw.torneo.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="giocatore")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Double getAltezza() {
        return altezza;
    }

    public void setAltezza(Double altezza) {
        this.altezza = altezza;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
        result = prime * result + ((dataDiNascita == null) ? 0 : dataDiNascita.hashCode());
        result = prime * result + ((ruolo == null) ? 0 : ruolo.hashCode());
        result = prime * result + ((altezza == null) ? 0 : altezza.hashCode());
        result = prime * result + ((squadra == null) ? 0 : squadra.hashCode());
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
        Giocatore other = (Giocatore) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (cognome == null) {
            if (other.cognome != null)
                return false;
        } else if (!cognome.equals(other.cognome))
            return false;
        if (dataDiNascita == null) {
            if (other.dataDiNascita != null)
                return false;
        } else if (!dataDiNascita.equals(other.dataDiNascita))
            return false;
        if (ruolo == null) {
            if (other.ruolo != null)
                return false;
        } else if (!ruolo.equals(other.ruolo))
            return false;
        if (altezza == null) {
            if (other.altezza != null)
                return false;
        } else if (!altezza.equals(other.altezza))
            return false;
        if (squadra == null) {
            if (other.squadra != null)
                return false;
        } else if (!squadra.equals(other.squadra))
            return false;
        return true;
    }

    
}
