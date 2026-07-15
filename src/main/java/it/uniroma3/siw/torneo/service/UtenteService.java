package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.model.RuoloUtente;
import it.uniroma3.siw.torneo.model.Utente;
import it.uniroma3.siw.torneo.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder)
    {
        this.utenteRepository=utenteRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public Utente registraUtente(Utente utente)
    {
        if (utenteRepository.existsByUsername(utente.getUsername())) {
            throw new IllegalArgumentException("Esiste già un utente con questo username");
        }
        String passwordCriptata=passwordEncoder.encode(utente.getPassword());
        utente.setPassword(passwordCriptata);
        return utenteRepository.save(utente);
    }


    @Transactional(readOnly = true)
    public Utente trovaUtentePerNome(String nome) {
        return utenteRepository.findByUsername(nome).orElseThrow();
    }
}