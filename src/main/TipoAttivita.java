package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TipoAttivita {
    private final String nome;
    private final String descrizione;
    private final float prezzo;
    private final DifficoltaEnum difficolta;
    private final Map<String, SessioneAttivita> elencoSessioni;
    private String id;
    private int contatoreSessioni = 0;

    public TipoAttivita(String id, String nome, String descrizione, float prezzo, DifficoltaEnum difficolta) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.difficolta = difficolta;
        this.id = id;
        this.elencoSessioni = new HashMap<>();
    }

    public String getId() {
        return id;
    }
    
    public void inserisciSessioneAttivita(LocalDateTime dataOra, int capienzaMassima, Duration durata) {
        String id = this.id + "S" + contatoreSessioni;
        SessioneAttivita s = new SessioneAttivita(id, dataOra, capienzaMassima, durata);
        this.contatoreSessioni++;
        elencoSessioni.put(id, s);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Descrizione: " + descrizione + ", Prezzo: " + String.format("%.2f", prezzo) + "€, Difficoltà: " + difficolta;
    }
}
