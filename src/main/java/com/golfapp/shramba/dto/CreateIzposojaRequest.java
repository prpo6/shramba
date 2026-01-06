package com.golfapp.shramba.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CreateIzposojaRequest {

    private String rezervacijaId;
    private LocalDateTime zacetek;
    private LocalDateTime konec;
    private List<Postavka> postavke;

    public static class Postavka {
        private UUID artikelId;
        private Integer kolicina;

        public UUID getArtikelId() { 
            return artikelId; 
        }

        public void setArtikelId(UUID artikelId) { 
            this.artikelId = artikelId; 
        }

        public Integer getKolicina() { 
            return kolicina;
        }
        public void setKolicina(Integer kolicina) { 
            this.kolicina = kolicina; 
        }
    }

    public String getRezervacijaId() { 
        return rezervacijaId; 
    }

    public void setRezervacijaId(String rezervacijaId) { 
        this.rezervacijaId = rezervacijaId; 
    }

    public LocalDateTime getZacetek() { return zacetek; }
    public void setZacetek(LocalDateTime zacetek) { this.zacetek = zacetek; }

    public LocalDateTime getKonec() { return konec; }
    public void setKonec(LocalDateTime konec) { this.konec = konec; }

    public List<Postavka> getPostavke() { return postavke; }
    public void setPostavke(List<Postavka> postavke) { this.postavke = postavke; }
}
