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

   // funkcija za dodajanje novih artiklov (če že obstaja se le poveča količina)
   public Artikel createOrAddQuantity(Artikel artikel) {
      Optional<Artikel> existingOpt = artikelRepository.findByImeArtikla(artikel.getImeArtikla());

      // če obstaja prištejemo količino že obstoječemu artiklu
      if (existingOpt.isPresent()) {
         Artikel existing = existingOpt.get();
         // prištej količino
         existing.setKolicina(existing.getKolicina() + artikel.getKolicina());
         // ceno najema in opis posodobimo (naj bi bila enaka,
         // če pa nista, pa želimo najnovejše vrednosti)
         existing.setOpis(artikel.getOpis());
         existing.setCenaNajema(artikel.getCenaNajema());
         return artikelRepository.save(existing);
      }

      // če imaš polje izposojeno, poskrbi da ni null (npr. 0)
      if (artikel.getIzposojeno() == null)
         artikel.setIzposojeno(0);

      return artikelRepository.save(artikel);
   }

   public Artikel update(@NonNull UUID id, Artikel updated) {
      Optional<Artikel> optionalArtikel = artikelRepository.findById(id);

      if (optionalArtikel.isEmpty()) {
         return null;
      }

      Artikel existing = optionalArtikel.get();

      existing.setImeArtikla(updated.getImeArtikla());
      existing.setOpis(updated.getOpis());
      existing.setKolicina(updated.getKolicina());
      existing.setCenaNajema(updated.getCenaNajema());
      existing.setPregledano(updated.getPregledano());

      return artikelRepository.save(existing);
   }

   public boolean delete(@NonNull UUID id) {
      if (!artikelRepository.existsById(id)) {
         return false;
      }
      artikelRepository.deleteById(id);
      return true;
   }

}
