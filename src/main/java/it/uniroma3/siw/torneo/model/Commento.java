package it.uniroma3.siw.torneo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commenti partite")
public class Commento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String corpo;
    private LocalDateTime dataCreazione;

    @ManyToOne
    private Partita partita;

    @ManyToOne
    private Utente autore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Partita getPartita() {
        return partita;
    }

    public void setPartita(Partita partita) {
        this.partita = partita;
    }

    public Utente getAutore() {
        return autore;
    }

    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    @PrePersist
    protected void onCreate(){
        this.dataCreazione=LocalDateTime.now();
    }
}
