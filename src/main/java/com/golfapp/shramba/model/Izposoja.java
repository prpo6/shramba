package com.golfapp.shramba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "izposoje", indexes = {
        @Index(name = "idx_izposoje_rezervacija", columnList = "rezervacija_id"),
        @Index(name = "idx_izposoje_cas", columnList = "zacetek,konec")
})
public class Izposoja {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "rezervacija_id", nullable = false)
    private String rezervacijaId;

    @Column(nullable = false)
    private LocalDateTime zacetek;

    @Column(nullable = false)
    private LocalDateTime konec;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IzposojaStatus status = IzposojaStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "izposoja", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<IzposojaPostavka> postavke = new ArrayList<>();

    public Izposoja() {}

    public Izposoja(String rezervacijaId, LocalDateTime zacetek, LocalDateTime konec) {
        this.rezervacijaId = rezervacijaId;
        this.zacetek = zacetek;
        this.konec = konec;
    }

    public void addPostavka(IzposojaPostavka postavka) {
        postavke.add(postavka);
        postavka.setIzposoja(this);
    }

    public void removePostavka(IzposojaPostavka postavka) {
        postavke.remove(postavka);
        postavka.setIzposoja(null);
    }

    public UUID getId() { 
        return id; 
    }

    public void setId(UUID id) { 
        this.id = id; 
    }

    public String getRezervacijaId() { 
        return rezervacijaId; 
    }

    public void setRezervacijaId(String rezervacijaId) { 
        this.rezervacijaId = rezervacijaId; 
    }

    public LocalDateTime getZacetek() { 
        return zacetek; 
    }

    public void setZacetek(LocalDateTime zacetek) { 
        this.zacetek = zacetek; 
    }

    public LocalDateTime getKonec() { 
        return konec; 
    }

    public void setKonec(LocalDateTime konec) { 
        this.konec = konec; 
    }

    public IzposojaStatus getStatus() { 
        return status; 
    }

    public void setStatus(IzposojaStatus status) { 
        this.status = status; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public List<IzposojaPostavka> getPostavke() { 
        return postavke; 
    }
    
    public void setPostavke(List<IzposojaPostavka> postavke) { 
        this.postavke = postavke; 
    }
}