package com.golfapp.shramba.dto;

import java.util.UUID;

public class RazpolozljivostResponse {
    private UUID artikelId;
    private int skupaj;
    private int zasedeno;
    private int naVoljo;

    public RazpolozljivostResponse(UUID artikelId, int skupaj, int zasedeno, int naVoljo) {
        this.artikelId = artikelId;
        this.skupaj = skupaj;
        this.zasedeno = zasedeno;
        this.naVoljo = naVoljo;
    }

    public UUID getArtikelId() { 
        return artikelId; 
    }

    public void setArtikelId(UUID artikelId) { 
        this.artikelId = artikelId; 
    }

    public int getSkupaj() { 
        return skupaj; 
    }

    public void setSkupaj(int skupaj) { 
        this.skupaj = skupaj; 
    }

    public int getZasedeno() { 
        return zasedeno; 
    }

    public void setZasedeno(int zasedeno) { 
        this.zasedeno = zasedeno; 
    }

    public int getNaVoljo() { 
        return naVoljo; 
    }

    public void setNaVoljo(int naVoljo) { 
        this.naVoljo = naVoljo; 
    }
}
