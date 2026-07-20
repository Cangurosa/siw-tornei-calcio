package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.model.Arbitro;
import it.uniroma3.siw.torneo.repository.ArbitroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ArbitroService {
    private final ArbitroRepository arbitroRepository;

    public ArbitroService(ArbitroRepository arbitroRepository) {
        this.arbitroRepository = arbitroRepository;
    }

    /**
     * Ritorna la lista mappata degli arbitri caricando le relazioni lazy sotto transazione.
     */
    public List<Map<String, Object>> getAllArbitriMapped() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Arbitro a : arbitroRepository.findAll()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("nome", a.getNome());
            map.put("cognome", a.getCognome());
            map.put("codiceArbitrale", a.getCodiceArbitrale());
            map.put("numPartite", a.getPartite() != null ? a.getPartite().size() : 0);
            list.add(map);
        }
        return list;
    }

    /**
     * Ritorna i dettagli mappati di un arbitro caricando le relazioni lazy sotto transazione.
     */
    public Map<String, Object> getArbitroByIdMapped(Long id) {
        Optional<Arbitro> opt = arbitroRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Arbitro a = opt.get();
        Map<String, Object> map = new HashMap<>();
        map.put("id", a.getId());
        map.put("nome", a.getNome());
        map.put("cognome", a.getCognome());
        map.put("codiceArbitrale", a.getCodiceArbitrale());
        map.put("numPartite", a.getPartite() != null ? a.getPartite().size() : 0);
        return map;
    }

    public List<Arbitro> findAll() {
        List<Arbitro> list = new ArrayList<>();
        arbitroRepository.findAll().forEach(list::add);
        return list;
    }

    public Optional<Arbitro> findById(Long id) {
        return arbitroRepository.findById(id);
    }

    @Transactional
    public Arbitro save(Arbitro arbitro) {
        return arbitroRepository.save(arbitro);
    }

    @Transactional
    public void deleteById(Long id) {
        arbitroRepository.deleteById(id);
    }
}
