package main;

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

    @Override
    public String toString() {
        return "id='" + id + '\'' + ", nome='" + nome + '\'' + ", codiceAttestato='" + codiceAttestato + '\'' + ", specializzazione='" + specializzazione + '\'';
    }
}

