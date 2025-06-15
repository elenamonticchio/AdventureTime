package main;

import java.time.Duration;
import java.time.LocalDateTime;

public class SessioneAttivita {
    private String id;
    private LocalDateTime dataOra;
    private int capienzaMassima;
    private Duration durata;
    private Guida guida;

    public SessioneAttivita(String id, LocalDateTime dataOra, int capienzaMassima, Duration durata) {
        this.id = id;
        this.dataOra = dataOra;
        this.capienzaMassima = capienzaMassima;
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' + ", dataOra=" + dataOra + ", capienzaMassima=" + capienzaMassima + ", durata=" + durata;
    }
}

