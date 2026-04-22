package it.uniroma3.siw.torneo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.torneo.Model.Squadra;
import it.uniroma3.siw.torneo.Service.SquadraService;

@Controller
public class SquadraController {
    private SquadraService squadraService;

    public SquadraController(SquadraService squadraService){
        this.squadraService = squadraService;
    }

    //lista squadre
    @GetMapping("/squadre")
    public String list(Model model) {
        List<Squadra> squadre = this.squadraService.getAllSquadre();
        model.addAttribute("squadre", squadre);
        return "squadre.html";
    }

    @GetMapping("/squadra/{id}")
    public String getSquadra(@PathVariable("id") Long id, Model model) {
        model.addAttribute("squadra", squadraService.getSquadraById(id));
        return "squadra.html";
    }

}
