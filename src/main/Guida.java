package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Guida {
    private String id;
    private String codiceAttestato;
    private String nome;
    private String specializzazione;
    private Map<String, SessioneAttivita> sessioniAssegnate;

    public Guida(String id, String nome, String codiceAttestato, String specializzazione) {
        this.id = id;
        this.nome = nome;
        this.codiceAttestato = codiceAttestato;
        this.specializzazione = specializzazione;
        this.sessioniAssegnate = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public String getCodiceAttestato() {
        return codiceAttestato;
    }

    public Map<String, SessioneAttivita> getSessioniAssegnate() {
        return sessioniAssegnate;
    }

    public boolean isDisponibile(LocalDateTime dataOra, Duration durata) {
        boolean bool;
        for (Map.Entry<String, SessioneAttivita> entry : sessioniAssegnate.entrySet()) {
            SessioneAttivita s = entry.getValue();
            bool = s.compareDataOra(dataOra, durata);
            if (bool) {
                return false;
            }
        }
        return true;
    }

    public void assegnaGuida(SessioneAttivita sessione) {
        sessioniAssegnate.put(sessione.getId(), sessione);
        System.out.println("Sessione assegnata alla guida");
    }

    public void rimuoviSessione(String sessioneId) {
        sessioniAssegnate.remove(sessioneId);
        System.out.println("Sessione rimossa dalla guida");
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' + ", nome='" + nome + '\'' + ", codiceAttestato='" + codiceAttestato + '\'' + ", specializzazione='" + specializzazione + '\'';
    }

}

