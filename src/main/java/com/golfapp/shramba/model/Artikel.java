package com.golfapp.shramba.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name = "artikli")
public class Artikel {

   @Id @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(name = "ime_artikla", nullable = false, unique = true)
   private String imeArtikla;

   @Column
   private String opis;

   @Column(nullable = false)
   private Integer kolicina;

   @Column(nullable = false)
   private Integer izposojeno = 0;

   @Column(name = "cena_najema", nullable = false)
   private BigDecimal cenaNajema;

   @Column
   private LocalDateTime pregledano;

   // konstruktorja
   public Artikel() {
   }

   public Artikel(String imeArtikla, String opis, Integer kolicina, BigDecimal cenaNajema) {
      this.imeArtikla = imeArtikla;
      this.opis = opis;
      this.kolicina = kolicina;
      this.cenaNajema = cenaNajema;
   }

   // getters and setters
   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public String getImeArtikla() {
      return imeArtikla;
   }

   public void setImeArtikla(String imeArtikla) {
      this.imeArtikla = imeArtikla;
   }

   public String getOpis() {
      return opis;
   }

   public void setOpis(String opis) {
      this.opis = opis;
   }

   public Integer getKolicina() {
      return kolicina;
   }

   public void setKolicina(Integer kolicina) {
      this.kolicina = kolicina;
   }

   public BigDecimal getCenaNajema() {
      return cenaNajema;
   }

   public void setCenaNajema(BigDecimal cenaNajema) {
      this.cenaNajema = cenaNajema;
   }

   public LocalDateTime getPregledano() {
      return pregledano;
   }

   public void setPregledano(LocalDateTime pregledano) {
      this.pregledano = pregledano;
   }

   public Integer getIzposojeno() {
      return izposojeno;
   }

   public void setIzposojeno(Integer izposojeno) {
      this.izposojeno = izposojeno;
   }
}
