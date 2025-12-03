package com.golfapp.shramba.repository;

import com.golfapp.shramba.model.Artikel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArtikelRepository extends JpaRepository<Artikel, UUID> {

   Optional<Artikel> findByImeArtikla(String imeArtikla);
}
