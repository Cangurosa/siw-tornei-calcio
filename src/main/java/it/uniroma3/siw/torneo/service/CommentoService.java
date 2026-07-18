package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.model.Commento;
import it.uniroma3.siw.torneo.model.Partita;
import it.uniroma3.siw.torneo.repository.CommentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentoService {
    private final CommentoRepository commentoRepository;

    public CommentoService(CommentoRepository commentoRepository){
        this.commentoRepository = commentoRepository;
    }

    public List<Commento> creaListaCommenti(Partita partita){
        long inizio = System.currentTimeMillis();
        List<Commento> commentiPartita = commentoRepository.findByPartitaOttimizzata(partita);
        long fine = System.currentTimeMillis();

        System.out.println("TEMPO DI ESECUZIONE QUERY COMMENTI: " + (fine-inizio) + "ms");
        return commentiPartita;
    }

    @Transactional
    public Commento registraCommento(Commento commento){
        return commentoRepository.save(commento);
    }

    public Commento trovaCommentoPerId(Long idCommento){
        return commentoRepository.findById(idCommento).orElseThrow();
    }

    @Transactional
    public void eliminaCommento(Commento commento){
        commentoRepository.delete(commento);
    }
}
