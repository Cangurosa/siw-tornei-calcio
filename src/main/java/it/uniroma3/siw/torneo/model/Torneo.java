package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String anno;
    private String descrizione;

    @ManyToMany
    @JoinTable(
            name = "torneo_squadra",
            joinColumns = @JoinColumn(name="torneo_id"),
            inverseJoinColumns = @JoinColumn(name = "squadra_id")
    )
    private List<Squadra> squadre = new ArrayList<>();

    @OneToMany(mappedBy = "torneo", fetch = FetchType.EAGER)
    private List<Partita> partite = new ArrayList<>();

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

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Squadra> getSquadre() {
        return squadre;
    }

    public void setSquadre(List<Squadra> squadre) {
        this.squadre = squadre;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    public void setPartite(List<Partita> partite) {
        this.partite = partite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Torneo torneo = (Torneo) o;
        return Objects.equals(getId(), torneo.getId()) && Objects.equals(getNome(), torneo.getNome()) && Objects.equals(getAnno(), torneo.getAnno()) && Objects.equals(getDescrizione(), torneo.getDescrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getAnno(), getDescrizione());
    }
}
