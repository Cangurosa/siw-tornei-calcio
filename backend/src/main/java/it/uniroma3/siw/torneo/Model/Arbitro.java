package it.uniroma3.siw.torneo.Model;

import jakarta.persistence.*;
import java.util.List;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
        result = prime * result + ((codiceArbitrale == null) ? 0 : codiceArbitrale.hashCode());
        result = prime * result + ((partite == null) ? 0 : partite.hashCode());
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
        Arbitro other = (Arbitro) obj;
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
        if (codiceArbitrale == null) {
            if (other.codiceArbitrale != null)
                return false;
        } else if (!codiceArbitrale.equals(other.codiceArbitrale))
            return false;
        if (partite == null) {
            if (other.partite != null)
                return false;
        } else if (!partite.equals(other.partite))
            return false;
        return true;
    }

    
}