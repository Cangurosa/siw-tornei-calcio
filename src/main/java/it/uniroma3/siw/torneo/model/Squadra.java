package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="squadra")
public class Squadra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Column(name = "anno_fondazione")
    private Integer annoFondazione;
    private String citta;

    @ManyToMany(mappedBy = "squadre")
    private List<Torneo> tornei;

    @OneToMany(mappedBy = "squadra")
    private List<Giocatore> giocatori;

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

    public Integer getAnnoFondazione() {
        return annoFondazione;
    }

    public void setAnnoFondazione(Integer annoFondazione) {
        this.annoFondazione = annoFondazione;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public List<Torneo> getTornei() {
        return tornei;
    }

    public void setTornei(List<Torneo> tornei) {
        this.tornei = tornei;
    }

    public List<Giocatore> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squadra squadra = (Squadra) o;
        return Objects.equals(getId(), squadra.getId()) && Objects.equals(getNome(), squadra.getNome()) && Objects.equals(getAnnoFondazione(), squadra.getAnnoFondazione()) && Objects.equals(getCitta(), squadra.getCitta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getAnnoFondazione(), getCitta());
    }
}