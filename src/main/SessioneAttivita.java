package main;

import java.time.Duration;
import java.time.LocalDateTime;

public class SessioneAttivita {
    private String id;
    private LocalDateTime dataOra;
    private int capienzaMassima;
    private Duration durata;
    private Guida guida;
    private int prenotazioniAttuali;

    public SessioneAttivita(String id, LocalDateTime dataOra, int capienzaMassima, Duration durata) {
        this.id = id;
        this.dataOra = dataOra;
        this.capienzaMassima = capienzaMassima;
        this.durata = durata;
        this.prenotazioniAttuali = 0;
    }


    public int getPrenotazioniAttuali() {
        return prenotazioniAttuali;
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

    public void setGuida(Guida guida) {
        this.guida = guida;
        System.out.println("Package.Guida assegnata alla sessione");
    }

    public int getCapienzaMassima() {
        return capienzaMassima;
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

    @Override
    public String toString() {
        return "id='" + id + '\'' + ", dataOra=" + dataOra + ", capienzaMassima=" + capienzaMassima + ", durata=" + durata;
    }
}

