package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private RuoloUtente ruolo;

    @OneToMany(mappedBy = "autore")
    private List<Commento> commentiDellUtente;

    public RuoloUtente getRuolo() {
        return ruolo;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRuolo(RuoloUtente ruolo) {
        this.ruolo = ruolo;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public List<Commento> getCommentiDellUtente() {
        return commentiDellUtente;
    }

    public void setCommentiDellUtente(List<Commento> commentiDellUtente) {
        this.commentiDellUtente = commentiDellUtente;
    }

}