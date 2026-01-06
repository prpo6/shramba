package com.golfapp.shramba.controller;

import com.golfapp.shramba.dto.RazpolozljivostResponse;
import com.golfapp.shramba.model.Artikel;
import com.golfapp.shramba.service.ArtikelService;
import com.golfapp.shramba.service.IzposojaService;

import io.micrometer.common.lang.NonNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController @RequestMapping("/api/artikli")
public class ArtikelController {

   private final ArtikelService artikelService;
   private final IzposojaService izposojaService;

   public ArtikelController(ArtikelService artikelService, IzposojaService izposojaService) {
      this.artikelService = artikelService;
      this.izposojaService = izposojaService;
   }

   // READ - GET /api/artikli
   @GetMapping
   public List<Artikel> getAll() {
      return artikelService.getAll();
   }

   // READ - GET /api/artikli/{id}
   @GetMapping("/{id}")
   public ResponseEntity<Artikel> getById(@PathVariable UUID id) {
      Artikel artikel = artikelService.getById(id);
      if (artikel == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(artikel);
   }

   // READ - GET /api/artikli/{id}/razpolozljivost?od=...&do=...
   @GetMapping("/{id}/razpolozljivost")
   public ResponseEntity<RazpolozljivostResponse> getRazpolozljivost(
           @PathVariable UUID id,
           @RequestParam("od") LocalDateTime od,
           @RequestParam("do") LocalDateTime _do
   ) {
      return ResponseEntity.ok(izposojaService.getRazpolozljivost(id, od, _do));
   }

   // CREATE - POST /api/artikli
   @PostMapping
   public ResponseEntity<Artikel> createOrAdd(@RequestBody Artikel artikel) {
      Artikel saved = artikelService.createOrAddQuantity(artikel);
      return ResponseEntity.ok(saved);
   }

   // UPDATE - PUT /api/artikli/{id}
   @PutMapping("/{id}")
   public ResponseEntity<Artikel> update(@PathVariable UUID id, @RequestBody Artikel updated) {
      Artikel result = artikelService.update(id, updated);
      if (result == null) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(result);
   }

   // DELETE - DELETE /api/artikli/{id}
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable UUID id) {
      boolean deleted = artikelService.delete(id);
      if (!deleted) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.noContent().build();
   }
}
