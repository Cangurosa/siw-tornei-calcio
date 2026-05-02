package it.uniroma3.siw.torneo.Controller;

import it.uniroma3.siw.torneo.Repository.TorneoRepository;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.torneo.Model.Torneo;
import it.uniroma3.siw.torneo.Service.PartitaService;
import it.uniroma3.siw.torneo.Service.TorneoService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TorneoController {
    private TorneoRepository torneoRepository;
    private TorneoService torneoService;
    private PartitaService partitaService;

    public TorneoController(TorneoService torneoService, PartitaService partitaService, TorneoRepository torneoRepository){
        this.torneoService = torneoService;
        this.partitaService = partitaService;
        this.torneoRepository = torneoRepository;
    }

    //lista tornei
    @GetMapping("/tornei")
    public String list(Model model){
        List<Torneo> tornei = this.torneoService.getAllTornei();
        model.addAttribute("tornei", tornei);
        return "tornei/tornei.html";
    }

    // dettaglio torneo
    @GetMapping("/torneo/{id}")
    public String getTorneo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "tornei/torneo.html";
    }

    //calendario partite torneo_id
    @GetMapping("/torneo/{id}/partite")
    public String getPartite(@PathVariable Long id, Model model) {
        model.addAttribute("partite", partitaService.getPartiteByTorneo(id));
        return "partite/partite.html";
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

    @GetMapping("tornei/torneo/nuovo")
    public String formNuovoTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        return "tornei/formTorneo.html";
    }

    @PostMapping("tornei/torneo")
    public String salvaTorneo(@ModelAttribute Torneo torneo) {
        System.out.println("TORNEO: " + torneo.getNome());
        System.out.println("ANNO: " + torneo.getAnno());

        torneoRepository.save(torneo);

        return "redirect:/tornei";
    }

    @GetMapping("tornei/torneo/{id}/modifica")
    public String modificaTorneo(@PathVariable Long id, Model model){
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "tornei/modificaTorneo.html";
    }

    @PostMapping("tornei/torneoSalvaModificato")
    public String salvaTorneoModificato(@ModelAttribute Torneo torneo){
        this.torneoRepository.save(torneo);
        return "redirect:/tornei";
    }
    
}
