package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.repository.UtenteRepository;
import it.uniroma3.siw.torneo.model.Utente;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailService(UtenteRepository utenteRepository) {
        this.utenteRepository=utenteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Utente> optionalUtente=utenteRepository.findByUsername(username);

        if(optionalUtente.isPresent()){
            Utente utente= optionalUtente.get();

            UserDetails dettagliUtente=User.builder()
                    .username(utente.getUsername())
                    .password(utente.getPassword())
                    .roles(utente.getRuolo().name())
                    .build();

            return dettagliUtente;
        }

        else {
            throw new UsernameNotFoundException("l'utente cercato non è stato trovato nel db");
        }
    }
}