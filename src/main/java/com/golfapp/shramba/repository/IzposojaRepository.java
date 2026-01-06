package com.golfapp.shramba.repository;

import com.golfapp.shramba.model.Izposoja;
import com.golfapp.shramba.model.IzposojaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IzposojaRepository extends JpaRepository<Izposoja, UUID> {

    Optional<Izposoja> findFirstByRezervacijaIdOrderByCreatedAtDesc(String rezervacijaId);

    List<Izposoja> findByRezervacijaId(String rezervacijaId);

    @Query("""
        SELECT COALESCE(SUM(p.kolicina), 0)
        FROM Izposoja i
        JOIN i.postavke p
        WHERE p.artikel.id = :artikelId
          AND i.status = :status
          AND i.zacetek < :do
          AND i.konec > :od
    """)
    long sumZasedenoZaArtikelVIntervalu(
            @Param("artikelId") UUID artikelId,
            @Param("od") LocalDateTime od,
            @Param("do") LocalDateTime _do,
            @Param("status") IzposojaStatus status
    );
}
