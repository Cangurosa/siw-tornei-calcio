package it.uniroma3.siw.torneo.controller;

import it.uniroma3.siw.torneo.model.Commento;
import it.uniroma3.siw.torneo.model.Partita;
import it.uniroma3.siw.torneo.model.Utente;
import it.uniroma3.siw.torneo.service.*;
import it.uniroma3.siw.torneo.repository.ArbitroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import it.uniroma3.siw.torneo.model.Torneo;
import it.uniroma3.siw.torneo.model.Squadra;

@Controller
public class PartitaController {
    private final SquadraService squadraService;
    private final PartitaService partitaService;
    private final TorneoService torneoService;
    private final UtenteService utenteService;
    private final CommentoService commentoService;
    private final ArbitroRepository arbitroRepository;

    public PartitaController(CommentoService commentoService, UtenteService utenteService, PartitaService partitaService, SquadraService squadraService, TorneoService torneoService, ArbitroRepository arbitroRepository){
        this.partitaService = partitaService;
        this.squadraService = squadraService;
        this.torneoService = torneoService;
        this.utenteService = utenteService;
        this.commentoService = commentoService;
        this.arbitroRepository = arbitroRepository;
    }

    /**
     * Creazione di una nuova partita
     */
    @GetMapping("/partita/nuova")
    public String nuovaPartita(Model model){
        model.addAttribute("partita", new Partita()); //oggetto vuoto
        model.addAttribute("squadre", squadraService.getAllSquadre());
        model.addAttribute("tornei", torneoService.getAllTornei());
        model.addAttribute("arbitri", arbitroRepository.findAll());

        // Costruiamo la mappa torneo-squadre per il filtraggio dinamico lato client
        Map<Long, List<Map<String, Object>>> torneoSquadreMap = new HashMap<>();
        for (Torneo t : torneoService.getAllTornei()) {
            List<Map<String, Object>> squadreList = new ArrayList<>();
            for (Squadra s : t.getSquadre()) {
                Map<String, Object> sMap = new HashMap<>();
                sMap.put("id", s.getId());
                sMap.put("nome", s.getNome());
                squadreList.add(sMap);
            }
            torneoSquadreMap.put(t.getId(), squadreList);
        }
        model.addAttribute("torneoSquadreMap", torneoSquadreMap);

        return "partite/form";
    }

    /**
     * Mostra la lista di tutte le partite
     */
    @GetMapping("/partite")
    public String listPartite(Model model) {
        List<Partita> partite = this.partitaService.getAllPartite();
        model.addAttribute("partite", partite);
        return "partite/list";
    }

    //riceve e salva i dati dal form
    @PostMapping("/partita")
    public String salvaPartita(@ModelAttribute Partita partita,
                               @RequestParam("squadraHomeId") Long squadraHomeId,
                               @RequestParam("squadraAwayId") Long squadraAwayId,
                               @RequestParam("torneoId") Long torneoId,
                               @RequestParam("arbitroId") Long arbitroId) {
        partita.setSquadraHome(squadraService.getSquadraById(squadraHomeId));
        partita.setSquadraAway(squadraService.getSquadraById(squadraAwayId));
        partita.setTorneo(torneoService.getTorneoById(torneoId));
        partita.setArbitro(arbitroRepository.findById(arbitroId).orElse(null));
        partitaService.savePartita(partita);
        return "redirect:/partite";
    }

    @GetMapping("/partite/commenti/{partitaId}")
    public String apriCommentiPartita(@PathVariable("partitaId") Long idPartita, Model model){
        Partita partita = partitaService.findPartitaById(idPartita);
        List<Commento> commenti = commentoService.creaListaCommenti(partita);

        model.addAttribute("partita", partita);
        model.addAttribute("commenti", commenti);
        model.addAttribute("commento", new Commento());

        return "partite/listCommento";
    }

    @GetMapping("/partita/{partitaId}/scriviCommento")
    public String scriviCommento(@PathVariable("partitaId") Long idPartita, Model model){
        Partita partita = partitaService.findPartitaById(idPartita);

        model.addAttribute("commento", new Commento());
        model.addAttribute("partita", partita);

        return "partite/formCommento";
    }

    @PostMapping("/partita/{partitaId}/scriviCommento")
    public String scriviCommentoPartita(@PathVariable("partitaId") Long idPartita, @ModelAttribute Commento commento, Principal principal){
        Partita partita = partitaService.findPartitaById(idPartita);
        Utente autore = utenteService.trovaUtentePerNome(principal.getName());

        commento.setPartita(partita);
        commento.setAutore(autore);
        commento.setDataCreazione(LocalDateTime.now());

        commentoService.registraCommento(commento);
        return "redirect:/partita/" +idPartita+ "/scriviCommento";
    }

    @GetMapping("/partita/{partitaId}/modifica/{commentoId}")
    public String modificaCommento(@PathVariable("partitaId") Long idPartita, @PathVariable("commentoId") Long idCommento, Model model, Principal principal){
        Commento commento = commentoService.trovaCommentoPerId(idCommento);
        Partita partita = partitaService.findPartitaById(idPartita);
        String usernameLoggato = principal.getName();

        if(!commento.getAutore().getUsername().equals(usernameLoggato)){
            return "redirect:/partita/" +idPartita+ "/scriviCommento";
        }

        model.addAttribute("commento", commento);
        model.addAttribute("partita", partita);

        return "partite/formCommento";
    }

    @PostMapping("partita/{partitaId}/modifica/{commentoId}")
    public String modificaCommento(@PathVariable("partitaId") Long idPartita, @PathVariable("commentoId") Long idCommento, @ModelAttribute("commento") Commento commentoForm, Principal principal){
        Commento commentoOriginale = commentoService.trovaCommentoPerId(idCommento);
        String usernameLoggato = principal.getName();

        if(!commentoOriginale.getAutore().getUsername().equals(usernameLoggato)){
            return "redirect:/partita/" +idPartita+ "/scriviCommento";
        }
        commentoOriginale.setCorpo((commentoForm.getCorpo()));
        commentoService.registraCommento(commentoOriginale);

        return "redirect:/partita/" +idPartita+ "/scriviCommento";
    }

    @PostMapping("/partita/{partitaId}/commenti/elimina/{commentoId}")
    public String eliminaCommento(@PathVariable("partitaId") Long idPartita, @PathVariable("commentoId") Long idCommento, Principal principal){
        Commento commentoOriginale = commentoService.trovaCommentoPerId(idCommento);
        String usernameLoggato = principal.getName();

        if(!commentoOriginale.getAutore().getUsername().equals(usernameLoggato)){
            return "redirect:/partita/" +idPartita+ "/scriviCommento";
        }

        commentoService.eliminaCommento(commentoOriginale);
        return "redirect:/partita/" +idPartita+ "/scriviCommento";
    }


}
