package com.golfapp.shramba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "izposoja_postavke", indexes = {@Index(name = "idx_postavke_artikel", columnList = "artikel_id")})
public class IzposojaPostavka {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "izposoja_id", nullable = false)
    @JsonBackReference
    private Izposoja izposoja;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "artikel_id", nullable = false)
    private Artikel artikel;

    @Column(nullable = false)
    private Integer kolicina;

    public IzposojaPostavka() {}

    public IzposojaPostavka(Artikel artikel, Integer kolicina) {
        this.artikel = artikel;
        this.kolicina = kolicina;
    }

    public UUID getId() { 
        return id; 
    }
    public void setId(UUID id) { 
        this.id = id; 
    }

    public Izposoja getIzposoja() { 
        return izposoja; 
    }

    public void setIzposoja(Izposoja izposoja) { 
        this.izposoja = izposoja; 
    }

    public Artikel getArtikel() { 
        return artikel; 
    }

    public void setArtikel(Artikel artikel) { 
        this.artikel = artikel; 
    }

    public Integer getKolicina() { 
        return kolicina; 
    }

    public void setKolicina(Integer kolicina) { 
        this.kolicina = kolicina; 
    }
}

