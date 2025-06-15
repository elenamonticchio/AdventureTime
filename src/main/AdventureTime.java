package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AdventureTime {
    private static AdventureTime adventureTime;
    private final Map<String, TipoAttivita> elencoAttivita;
    private final Map<String, Guida> elencoGuide;
    private int contatoreAttivita = 0;
    private int contatoreGuide = 0;
    private TipoAttivita attivitaCorrente;

    public AdventureTime() {
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
}
