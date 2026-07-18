package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private String codiceArbitrale;

    @OneToMany(mappedBy = "arbitro")
    private List<Partita> partite;

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

    public String getCodiceArbitrale() {
        return codiceArbitrale;
    }

    public void setCodiceArbitrale(String codiceArbitrale) {
        this.codiceArbitrale = codiceArbitrale;
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
        Arbitro arbitro = (Arbitro) o;
        return Objects.equals(getId(), arbitro.getId()) && Objects.equals(getNome(), arbitro.getNome()) && Objects.equals(getCognome(), arbitro.getCognome()) && Objects.equals(getCodiceArbitrale(), arbitro.getCodiceArbitrale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCognome(), getCodiceArbitrale());
    }
}