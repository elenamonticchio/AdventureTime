package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AdventureTime {
    private static AdventureTime adventureTime;
    private final Map<String, TipoAttivita> elencoAttivita;
    private final Map<String, Guida> elencoGuide;
    private int contatoreAttivita;
    private int contatoreGuide;
    private TipoAttivita attivitaCorrente;
    private int capienzaMassima;
    private int visitatoriAttuali;

    public AdventureTime() {
        this.contatoreAttivita = 0;
        this.contatoreGuide = 0;
        this.capienzaMassima = 320;
        this.visitatoriAttuali = 0;
        this.elencoAttivita = new HashMap<>();
        this.elencoGuide = new HashMap<>();
    }

    public static AdventureTime getInstance() {
        if (adventureTime == null) {
            adventureTime = new AdventureTime();
        } else {
            System.out.println("Istanza già creata");
        }

        return adventureTime;
    }

    public Map<String, TipoAttivita> getElencoAttivita() {
        return Collections.unmodifiableMap(elencoAttivita);
    }

    public Map<String, Guida> getElencoGuide() {
        return Collections.unmodifiableMap(elencoGuide);
    }

    public TipoAttivita getAttivitaCorrente() {
        return attivitaCorrente;
    }

    public void inserisciNuovaAttivita(String nome, String descrizione, float prezzo, DifficoltaEnum difficolta) {
        String id = "A" + contatoreAttivita;
        this.attivitaCorrente = new TipoAttivita(id, nome, descrizione, prezzo, difficolta);
        this.contatoreAttivita++;
        System.out.println("Attività Inserita");
    }

    public void inserisciSessioneAttivita(LocalDateTime dataOra, int capienzaMassima, Duration durata) {
        if (attivitaCorrente != null) {
            this.attivitaCorrente.inserisciSessioneAttivita(dataOra, capienzaMassima, durata);
            System.out.println("Sessione Inserita");
        }
    }

    public void confermaInserimento() {
        if (attivitaCorrente != null) {
            String idAttivita = this.attivitaCorrente.getId();
            this.elencoAttivita.put(idAttivita, attivitaCorrente);
            System.out.println("Operazione Inserimento Attività Conclusa");
        }
    }

    public void visualizzaElencoAttivita() {
        if (elencoAttivita.isEmpty()) {
            System.out.println("Nessuna attività disponibile al momento.");
            return;
        }

        System.out.println("--- Elenco delle Attività ---");
        for (Map.Entry<String, TipoAttivita> entry : elencoAttivita.entrySet()) {
            TipoAttivita attivita = entry.getValue();

            System.out.println(attivita);
        }
        System.out.println("----------------------------");
    }

    public void registraGuida(String nome, String codiceAttestato, String specializzazione) {
        String id = "G" + contatoreGuide;
        Guida g = new Guida(id, nome, codiceAttestato, specializzazione);
        this.contatoreGuide++;
        this.elencoGuide.put(id, g);
        System.out.println("Operazione Inserimento Guida Conclusa");
    }

    public Map<String, SessioneAttivita> mostraSessioniSenzaGuida(String attivitaId) {
        TipoAttivita attivita = this.elencoAttivita.get(attivitaId);
        Map<String, SessioneAttivita> sessioniSenzaGuida = attivita.getSessioniSenzaGuida();
        for (Map.Entry<String, SessioneAttivita> entry : sessioniSenzaGuida.entrySet()) {
            SessioneAttivita sessione = entry.getValue();
            System.out.println(sessione);
        }
        return sessioniSenzaGuida;
    }

    public Map<String, Guida> mostraGuideDisponibili(SessioneAttivita sessione) {
        Map<String, Guida> guideDisponibili = new HashMap<>();
        LocalDateTime dataOra = sessione.getDataOra();
        Duration durata = sessione.getDurata();
        boolean bool;
        String sessioneId = sessione.getId();
        int indexOfS = sessioneId.indexOf('S');
        String attivitaId = null;
        if (indexOfS != -1) {
            attivitaId = sessioneId.substring(0, indexOfS);
        }
        for (Map.Entry<String, Guida> entry : elencoGuide.entrySet()) {
            Guida g = entry.getValue();
            if (g.getSpecializzazione().equalsIgnoreCase(elencoAttivita.get(attivitaId).getNome())) {
                bool = g.isDisponibile(dataOra, durata);
            } else {
                bool = false;
            }
            if (bool) {
                guideDisponibili.put(entry.getKey(), g);
            }
        }
        for (Map.Entry<String, Guida> entry : guideDisponibili.entrySet()) {
            Guida g = entry.getValue();
            System.out.println(g);
        }
        return guideDisponibili;
    }

    public void assegnaGuida(SessioneAttivita sessione, Guida guida) {
        guida.assegnaGuida(sessione);
        sessione.setGuida(guida);
    }

    public void visualizzaSessioniAttivita(String attivitaId) {
        this.attivitaCorrente = elencoAttivita.get(attivitaId);
        Map<String, SessioneAttivita> elencoSessioni = this.attivitaCorrente.getElencoSessioni();
        if (elencoSessioni.isEmpty()) {
            System.out.println("Nessuna sessione disponibile per l'attività: " + this.attivitaCorrente.getNome());
            return;
        }
        for (Map.Entry<String, SessioneAttivita> entry : elencoSessioni.entrySet()) {
            SessioneAttivita sessione = entry.getValue();
            System.out.println(sessione);
        }
    }

    public void eliminaSessione(String sessioneId) {
        this.attivitaCorrente.eliminaSessione(sessioneId);
    }

    public void selezionaAttivita(String attivitaId) {
        this.attivitaCorrente = elencoAttivita.get(attivitaId);
    }

    public void monitoraCapacita() {
        System.out.println("Capacità massima: " + capienzaMassima + " visitatori");
        System.out.println("Visitatori attuali: " + visitatoriAttuali);

        double percentualeOccupazione = (double) visitatoriAttuali / capienzaMassima * 100;
        System.out.printf("Occupazione: %.2f%%\n", percentualeOccupazione);

        int rimanenti = capienzaMassima - visitatoriAttuali;
        System.out.println("Posti disponibili: " + rimanenti);

        if (percentualeOccupazione < 60) {
            System.out.println("Stato: Libero");
        } else if (percentualeOccupazione < 90) {
            System.out.println("Stato: Affollato");
        } else {
            System.out.println("Stato: Quasi pieno o pieno");
        }
    }
}
