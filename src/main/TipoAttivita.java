package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public DifficoltaEnum getDifficolta() {
        return difficolta;
    }

    public Map<String, SessioneAttivita> getElencoSessioni() {
        return Collections.unmodifiableMap(elencoSessioni);
    }

    public void inserisciSessioneAttivita(LocalDateTime dataOra, int capienzaMassima, Duration durata) {
        String id = this.id + "S" + contatoreSessioni;
        SessioneAttivita s = new SessioneAttivita(id, dataOra, capienzaMassima, durata);
        this.contatoreSessioni++;
        elencoSessioni.put(id, s);
    }

    public Map<String, SessioneAttivita> getSessioniSenzaGuida() {
        Map<String, SessioneAttivita> sessioniSenzaGuida = new HashMap<>();
        boolean bool;
        for (Map.Entry<String, SessioneAttivita> entry : elencoSessioni.entrySet()) {
            SessioneAttivita sessione = entry.getValue();
            bool = sessione.hasGuida();
            if (!bool) {
                sessioniSenzaGuida.put(entry.getKey(), sessione);
            }
        }
        return sessioniSenzaGuida;
    }

    public void eliminaSessione(String sessioneId) {
        SessioneAttivita sessione = elencoSessioni.get(sessioneId);
        if (sessione.getPartecipantiAttuali() == 0) {
            Guida guida = sessione.getGuida();
            if (guida != null) {
                guida.rimuoviSessione(sessioneId);
            }
            this.elencoSessioni.remove(sessioneId);
            System.out.println("Sessione rimossa correttamente");
        } else {
            System.out.println("Impossibile rimuovere la sessione: sono presenti prenotazioni");
        }
    }

    public boolean equalsNome(String nomeAttivita) {
        return Objects.equals(nome, nomeAttivita);
    }

    public Map<String, SessioneAttivita> ottieniSessioniDisponibili() {
        Map<String, SessioneAttivita> sessioniDisponibili = new HashMap<>();

        for (Map.Entry<String, SessioneAttivita> entry : elencoSessioni.entrySet()) {
            SessioneAttivita sessione = entry.getValue();
            if (sessione.isDisponibile()) {
                sessioniDisponibili.put(sessione.getId(), sessione);
            }
        }

        if (sessioniDisponibili.isEmpty()) {
            System.out.println("Nessuna sessione disponibile");
        }

        return sessioniDisponibili;
    }

    public SessioneAttivita getSessione(String sessioneId) {
        return elencoSessioni.get(sessioneId);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Descrizione: " + descrizione + ", Prezzo: " + String.format("%.2f", prezzo) + "€, Difficoltà: " + difficolta;
    }
}
