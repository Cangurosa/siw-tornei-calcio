package it.uniroma3.siw.torneo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.torneo.Model.Torneo;
import it.uniroma3.siw.torneo.Service.PartitaService;
import it.uniroma3.siw.torneo.Service.TorneoService;

@Controller
public class TorneoController {
    private TorneoService torneoService;
    private PartitaService partitaService;

    public TorneoController(TorneoService torneoService, PartitaService partitaService){
        this.torneoService = torneoService;
        this.partitaService = partitaService;
    }

    //lista tornei
    @GetMapping("/tornei")
    public String list(Model model){
        List<Torneo> tornei = this.torneoService.getAllTornei();
        model.addAttribute("tornei", tornei);
        return "tornei.html";
    }

    // dettaglio torneo
    @GetMapping("/torneo/{id}")
    public String getTorneo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "torneo.html";
    }

    //calendario partite torneo_id
    @GetMapping("/torneo/{id}/partite")
    public String getPartite(@PathVariable Long id, Model model) {
        model.addAttribute("partite", partitaService.getPartiteByTorneo(id));
        return "partite.html";
    }

    @GetMapping("/torneo/{id}/classifica")
    public String getClassifica(@PathVariable Long id, Model model) {
        model.addAttribute("classifica", torneoService.getClassifica(id));
        return "classifica.html";
    }

    public TorneoService getTorneoService() {
        return torneoService;
    }

    public void setTorneoService(TorneoService torneoService) {
        this.torneoService = torneoService;
    }
}
