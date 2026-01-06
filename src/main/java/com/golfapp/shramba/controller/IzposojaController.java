package com.golfapp.shramba.controller;

import com.golfapp.shramba.dto.CreateIzposojaRequest;
import com.golfapp.shramba.model.Izposoja;
import com.golfapp.shramba.service.IzposojaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class IzposojaController {

    private final IzposojaService izposojaService;

    public IzposojaController(IzposojaService izposojaService) {
        this.izposojaService = izposojaService;
    }

    // POST /api/izposoje
    @PostMapping("/izposoje")
    public ResponseEntity<Izposoja> create(@RequestBody CreateIzposojaRequest req) {
        return ResponseEntity.ok(izposojaService.create(req));
    }

    // GET /api/rezervacije/{rezervacijaId}/oprema
    @GetMapping("/rezervacije/{rezervacijaId}/oprema")
    public ResponseEntity<List<Izposoja>> getByRezervacija(@PathVariable String rezervacijaId) {
        return ResponseEntity.ok(izposojaService.getByRezervacijaId(rezervacijaId));
    }

    // PUT /api/izposoje/{id}/vrni
    @PutMapping("/izposoje/{id}/vrni")
    public ResponseEntity<Izposoja> vrni(@PathVariable UUID id) {
        return ResponseEntity.ok(izposojaService.vrni(id));
    }

    // PUT /api/izposoje/{id}/preklici
    @PutMapping("/izposoje/{id}/preklici")
    public ResponseEntity<Izposoja> preklici(@PathVariable UUID id) {
        return ResponseEntity.ok(izposojaService.preklici(id));
    }
}
