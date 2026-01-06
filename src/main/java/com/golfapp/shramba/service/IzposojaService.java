package com.golfapp.shramba.service;

import com.golfapp.shramba.dto.CreateIzposojaRequest;
import com.golfapp.shramba.dto.RazpolozljivostResponse;
import com.golfapp.shramba.model.*;
import com.golfapp.shramba.repository.ArtikelRepository;
import com.golfapp.shramba.repository.IzposojaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class IzposojaService {

    private final IzposojaRepository izposojaRepository;
    private final ArtikelRepository artikelRepository;

    public IzposojaService(IzposojaRepository izposojaRepository, ArtikelRepository artikelRepository) {
        this.izposojaRepository = izposojaRepository;
        this.artikelRepository = artikelRepository;
    }

    public Izposoja create(CreateIzposojaRequest req) {
        validateRequest(req);

        // lahko imamo samo eno izposojo na rezervacijo
        izposojaRepository.findFirstByRezervacijaIdOrderByCreatedAtDesc(req.getRezervacijaId())
                .filter(i -> i.getStatus() == IzposojaStatus.ACTIVE)
                .ifPresent(i -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Za to rezervacijo že obstaja izposoja."
                    );
                });

        Izposoja izposoja = new Izposoja(req.getRezervacijaId(), req.getZacetek(), req.getKonec());
        izposoja.setStatus(IzposojaStatus.ACTIVE);

        for (CreateIzposojaRequest.Postavka p : req.getPostavke()) {
            if (p.getKolicina() == null || p.getKolicina() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mora biti > 0.");
            }

            Artikel artikel = artikelRepository.findById(p.getArtikelId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artikel ne obstaja: " + p.getArtikelId()));

            RazpolozljivostResponse razp = getRazpolozljivost(artikel.getId(), req.getZacetek(), req.getKonec());
            if (p.getKolicina() > razp.getNaVoljo()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Ni dovolj opreme za " + artikel.getImeArtikla() + " (na voljo: " + razp.getNaVoljo() + ")."
                );
            }

            izposoja.addPostavka(new IzposojaPostavka(artikel, p.getKolicina()));
        }

        return izposojaRepository.save(izposoja);
    }

    public RazpolozljivostResponse getRazpolozljivost(UUID artikelId, LocalDateTime od, LocalDateTime _do) {
        if (od == null || _do == null || !_do.isAfter(od)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Neveljaven interval,");
        }

        Artikel artikel = artikelRepository.findById(artikelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artikel ne obstaja."));

        long zasedeno = izposojaRepository.sumZasedenoZaArtikelVIntervalu(artikelId, od, _do, IzposojaStatus.ACTIVE);
        int skupaj = artikel.getKolicina() == null ? 0 : artikel.getKolicina();
        int naVoljo = (int) Math.max(0, skupaj - zasedeno);

        return new RazpolozljivostResponse(artikelId, skupaj, (int) zasedeno, naVoljo);
    }

    public List<Izposoja> getByRezervacijaId(String rezervacijaId) {
        return izposojaRepository.findByRezervacijaId(rezervacijaId);
    }

    public Izposoja vrni(UUID izposojaId) {
        Izposoja izposoja = izposojaRepository.findById(izposojaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Izposoja ne obstaja."));

        if (izposoja.getStatus() != IzposojaStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Izposoja ni aktivna.");
        }

        izposoja.setStatus(IzposojaStatus.RETURNED);
        return izposojaRepository.save(izposoja);
    }

    public Izposoja preklici(UUID izposojaId) {
        Izposoja izposoja = izposojaRepository.findById(izposojaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Izposoja ne obstaja."));

        if (izposoja.getStatus() != IzposojaStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Izposoja ni aktivna.");
        }

        izposoja.setStatus(IzposojaStatus.CANCELED);
        return izposojaRepository.save(izposoja);
    }

    private void validateRequest(CreateIzposojaRequest req) {
        if (req == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body.");
        if (req.getRezervacijaId() == null || req.getRezervacijaId().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manjka rezervacijaId.");
        if (req.getZacetek() == null || req.getKonec() == null || !req.getKonec().isAfter(req.getZacetek()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Neveljaven interval.");
        if (req.getPostavke() == null || req.getPostavke().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manjkajo postavke.");
    }
}
