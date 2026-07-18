package it.uniroma3.siw.torneo.controller;

import it.uniroma3.siw.torneo.model.Arbitro;
import it.uniroma3.siw.torneo.repository.ArbitroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RestController
@RequestMapping("/api")
@Transactional
public class ArbitroRestController {

    private final it.uniroma3.siw.torneo.service.ArbitroService arbitroService;

    public ArbitroRestController(it.uniroma3.siw.torneo.service.ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }

    /**
     * Ritorna l'utente corrente con il suo ruolo per permettere a React di adattare l'interfaccia.
     */
    @GetMapping("/utente/corrente")
    public ResponseEntity<Map<String, Object>> getUtenteCorrente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = auth.getName();
        String ruoloStr = "USER";
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String aut = authority.getAuthority();
            if (aut.equals("ROLE_ADMIN")) {
                ruoloStr = "ADMIN";
                break;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("ruolo", ruoloStr);
        return ResponseEntity.ok(response);
    }

    /**
     * Ritorna la lista di tutti gli arbitri, mappata via Service.
     */
    @GetMapping("/arbitri")
    public ResponseEntity<List<Map<String, Object>>> getAllArbitri() {
        return ResponseEntity.ok(arbitroService.getAllArbitriMapped());
    }

    /**
     * Ritorna i dettagli di un singolo arbitro.
     */
    @GetMapping("/arbitri/{id}")
    public ResponseEntity<Map<String, Object>> getArbitroById(@PathVariable Long id) {
        Map<String, Object> map = arbitroService.getArbitroByIdMapped(id);
        if (map == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map);
    }

    /**
     * Crea un nuovo arbitro (protetto via SecurityConfig).
     */
    @PostMapping("/arbitri")
    public ResponseEntity<Arbitro> createArbitro(@RequestBody Arbitro arbitro) {
        if (arbitro.getNome() == null || arbitro.getCognome() == null || arbitro.getCodiceArbitrale() == null) {
            return ResponseEntity.badRequest().build();
        }
        Arbitro saved = arbitroService.save(arbitro);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Modifica un arbitro esistente (protetto via SecurityConfig).
     */
    @PutMapping("/arbitri/{id}")
    public ResponseEntity<Arbitro> updateArbitro(@PathVariable Long id, @RequestBody Arbitro arbitroDati) {
        Optional<Arbitro> opt = arbitroService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Arbitro a = opt.get();
        a.setNome(arbitroDati.getNome());
        a.setCognome(arbitroDati.getCognome());
        a.setCodiceArbitrale(arbitroDati.getCodiceArbitrale());
        Arbitro saved = arbitroService.save(a);
        return ResponseEntity.ok(saved);
    }

    /**
     * Elimina un arbitro (protetto via SecurityConfig).
     */
    @DeleteMapping("/arbitri/{id}")
    public ResponseEntity<Void> deleteArbitro(@PathVariable Long id) {
        Optional<Arbitro> opt = arbitroService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        arbitroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
