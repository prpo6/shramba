package com.golfapp.shramba.service;

import com.golfapp.shramba.model.Artikel;
import com.golfapp.shramba.repository.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtikelService {

   private final ArtikelRepository artikelRepository;

   public ArtikelService(ArtikelRepository artikelRepository) {
      this.artikelRepository = artikelRepository;
   }

   public List<Artikel> getAll() {
      return artikelRepository.findAll();
   }

   public Artikel getById(@NonNull UUID id) {
      return artikelRepository.findById(id).orElse(null);
   }

   // funkcija za dodajanje novih artiklov (ce ze obstaja se le poveca kolicina)
   public Artikel createOrAddQuantity(Artikel artikel) {
      // TO DO
      return null;
   }

   public Artikel update(UUID id, Artikel updated) {
      // TO DO
      return null;
   }

   public boolean delete(UUID id) {
      // TO DO
      return false;
   }
}
