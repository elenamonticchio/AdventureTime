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

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public String getId() {
        return id;
    }

    public Duration getDurata() {
        return durata;
    }

    public Guida getGuida() {
        return guida;
    }

    public boolean hasGuida() {
        return guida != null;
    }

    public boolean compareDataOra(LocalDateTime dataOra, Duration durata) {
        LocalDateTime thisFineSessione = this.dataOra.plus(this.durata);
        LocalDateTime fineSessione = dataOra.plus(durata);
        boolean startsBeforeOrEqualsThisEnd = dataOra.isBefore(thisFineSessione) || dataOra.isEqual(thisFineSessione);
        boolean endsAfterOrEqualsThisStart = fineSessione.isAfter(this.dataOra) || fineSessione.isEqual(this.dataOra);
        return startsBeforeOrEqualsThisEnd && endsAfterOrEqualsThisStart;
    }

    public void assegnaGuida(Guida guida) {
        this.guida = guida;
        System.out.println("Package.Guida assegnata alla sessione");
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' + ", dataOra=" + dataOra + ", capienzaMassima=" + capienzaMassima + ", durata=" + durata;
    }
}

