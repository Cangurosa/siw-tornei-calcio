package it.uniroma3.siw.torneo.Controller;

import it.uniroma3.siw.torneo.Repository.SquadraRepository;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.torneo.Model.Giocatore;
import it.uniroma3.siw.torneo.Model.Squadra;
import it.uniroma3.siw.torneo.Service.SquadraService;




@Controller
public class SquadraController {
    private final SquadraRepository squadraRepository;
    private SquadraService squadraService;

    public SquadraController(SquadraService squadraService, SquadraRepository squadraRepository){
        this.squadraService = squadraService;
        this.squadraRepository = squadraRepository;
    }

    //lista squadre
    @GetMapping("/squadre")
    public String list(Model model) {
        List<Squadra> squadre = this.squadraService.getAllSquadre();
        model.addAttribute("squadre", squadre);
        return "squadre.html";
    }

    //id squadra
    @GetMapping("/squadra/{id:\\d+}")
    public String getSquadra(@PathVariable("id") Long id, Model model) {
        model.addAttribute("squadra", squadraService.getSquadraById(id));
        return "squadra.html";
    }

    @GetMapping("/squadra/nuova")
    public String nuovaSquadra (Model model){
        model.addAttribute("squadra", new Squadra());
        return "formSquadra.html";
    }

    @PostMapping("/squadra")
    public String salvaSquadra(@ModelAttribute Squadra squadra) {
        squadraService.saveSquadra(squadra);
        return "redirect:/squadre";
    }

    @GetMapping("/giocatore/nuovo")
    public String nuovoGiocatore(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        model.addAttribute("squadre", this.squadraService.getAllSquadre());
        return "formGiocatore.html";
    }

    @PostMapping("/giocatore")
    public String salvaGiocatore(@ModelAttribute Giocatore giocatore, @RequestParam("squadraId") Long squadraId) {
        Squadra squadra = squadraService.getSquadraById(squadraId);
        giocatore.setSquadra(squadra);
        squadraService.saveGiocatore(giocatore);
        return "redirect:/squadra/" + squadraId;
    }
    
    
    

}
