package it.uniroma3.siw.torneo.controller;

import it.uniroma3.siw.torneo.model.Utente;
import it.uniroma3.siw.torneo.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService)
    {
        this.utenteService=utenteService;
    }

    @GetMapping("/registrazione")
    public String mostraFormRegistrazione(Model model)
    {
        model.addAttribute("utente", new Utente());

        return "utente/registrazione";
    }

    @PostMapping("/registrazione")
    public String registraUtente(@ModelAttribute("utente") Utente utente, Model model)
    {
        try {
            utenteService.registraUtente(utente);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erroreUtente", e.getMessage());
            return "utente/registrazione";
        }
    }

    @GetMapping("/login")
    public String mostraFormLogin()
    {
        return "utente/login";
    }
}