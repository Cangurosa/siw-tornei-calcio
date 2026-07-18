package it.uniroma3.siw.torneo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArbitroController {

    @GetMapping("/arbitri")
    public String mostraArbitri() {
        return "arbitri";
    }
}
