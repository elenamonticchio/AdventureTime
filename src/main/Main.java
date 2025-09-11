package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        AdventureTime adventureTime = AdventureTime.getInstance();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int scelta;
        String nome;
        String attivitaId;
        String sessioneId;
        String condizione;
        String eta;
        boolean isRidotto;

        do {
            switch (scelta = menu(bf)) {
                case 1:
                    TipoAttivita attivitaCorrente;
                    do {
                        System.out.print("Nome attività: ");
                        nome = readLineSafe(bf);
                        adventureTime.ottieniInfoAttivitaESessioni(nome);
                        attivitaCorrente = adventureTime.getAttivitaCorrente();

                        if (attivitaCorrente != null && attivitaCorrente.ottieniSessioniDisponibili().isEmpty()) {
                            System.out.println("Nessuna sessione disponibile per questa attività. Riprova.");
                        }
                    } while (attivitaCorrente == null || attivitaCorrente.ottieniSessioniDisponibili().isEmpty());
                    System.out.print("Id sessione: ");
                    sessioneId = readLineSafe(bf);
                    System.out.print("Età inferiore a 12 anni o superiore a 65 anni? s/n: ");
                    eta = readLineSafe(bf);
                    isRidotto = eta.equalsIgnoreCase("s");
                    adventureTime.acquistaBigliettoSessione(sessioneId, isRidotto);
                    break;
                case 2:
                    System.out.print("Nome attività: ");
                    nome = readLineSafe(bf);
                    System.out.print("Descrizione: ");
                    String descrizione = readLineSafe(bf);
                    System.out.print("Prezzo: ");
                    float prezzo = Float.parseFloat(readLineSafe(bf));
                    System.out.print("Difficoltà (FACILE, MEDIA, DIFFICILE): ");
                    DifficoltaEnum diff = DifficoltaEnum.valueOf(readLineSafe(bf).toUpperCase());
                    adventureTime.inserisciNuovaAttivita(nome, descrizione, prezzo, diff);

                    System.out.print("Vuoi inserire delle sessioni? s/n: ");
                    condizione = readLineSafe(bf);
                    while (Objects.equals(condizione, "s")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        System.out.print("Data e ora sessione (formato: GG/MM/AAAA HH:MM es. 31/12/2023 15:30): ");
                        String input = readLineSafe(bf);
                        LocalDateTime dataOra = LocalDateTime.parse(input, formatter);
                        System.out.print("Capienza massima: ");
                        int capienzaMassima = Integer.parseInt(readLineSafe(bf));
                        System.out.print("Durata in ore: ");
                        int ore = Integer.parseInt(readLineSafe(bf));
                        Duration durata = Duration.ofHours(ore);
                        adventureTime.inserisciSessioneAttivita(dataOra, capienzaMassima, durata);

                        System.out.print("Vuoi inserire altre sessioni? s/n: ");
                        condizione = readLineSafe(bf);
                    }

                    adventureTime.confermaInserimento();
                    break;
                case 3:
                    adventureTime.monitoraCapacita();
                    break;
                case 4:
                    adventureTime.visualizzaElencoAttivita();
                    break;
                case 5:
                    Biglietto biglietto;
                    String bigliettoId;
                    do {
                        Map<String, Biglietto> elencoBiglietti = adventureTime.getElencoBiglietti();
                        System.out.print("Codice biglietto: ");
                        bigliettoId = readLineSafe(bf);
                        biglietto = elencoBiglietti.get(bigliettoId);
                        if (biglietto == null) {
                            System.out.println("Codice biglietto non valido. Riprova.");
                        }
                    } while (biglietto == null);

                    if (biglietto instanceof BigliettoSessione) {
                        SessioneAttivita sessione = ((BigliettoSessione) biglietto).getSessione();
                        float prezzoBiglietto = biglietto.getPrezzo();
                        LocalDateTime dataOra = sessione.getDataOra();
                        if (LocalDateTime.now().isAfter(dataOra)) {
                            System.out.println("Sessione Scaduta.");
                        } else {
                            adventureTime.rimborsaBigliettoSessione(bigliettoId);
                        }
                    }
                    break;

                case 6:
                    adventureTime.visualizzaElencoAttivita();

                    if (!adventureTime.getElencoAttivita().isEmpty()) {
                        System.out.print("Id attività: ");
                        attivitaId = readLineSafe(bf);
                        adventureTime.visualizzaSessioniAttivita(attivitaId);

                        do {
                            System.out.print("Id sessione: ");
                            sessioneId = readLineSafe(bf);
                            adventureTime.eliminaSessione(sessioneId);

                            System.out.print("Vuoi eliminare altre sessioni? s/n: ");
                            condizione = readLineSafe(bf);
                        } while (condizione.contentEquals("s"));
                    }
                    break;
                case 7:
                    System.out.print("Nome guida: ");
                    nome = readLineSafe(bf);
                    System.out.print("Codice attestato: ");
                    String codiceAttestato = readLineSafe(bf);
                    System.out.print("Specializzazione: ");
                    String specializzazione = readLineSafe(bf);
                    adventureTime.registraGuida(nome, codiceAttestato, specializzazione);
                    break;
                case 8:
                    adventureTime.visualizzaElencoAttivita();

                    if (!adventureTime.getElencoAttivita().isEmpty()) {
                        System.out.print("Id attività: ");
                        attivitaId = readLineSafe(bf);
                        adventureTime.selezionaAttivita(attivitaId);

                        do {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                            System.out.print("Data e ora sessione (formato: GG/MM/AAAA HH:MM es. 31/12/2023 15:30): ");
                            String input = readLineSafe(bf);
                            LocalDateTime dataOra = LocalDateTime.parse(input, formatter);
                            System.out.print("Capienza massima: ");
                            int capienzaMassima = Integer.parseInt(readLineSafe(bf));
                            System.out.print("Durata in ore: ");
                            int ore = Integer.parseInt(readLineSafe(bf));
                            Duration durata = Duration.ofHours(ore);
                            adventureTime.inserisciSessioneAttivita(dataOra, capienzaMassima, durata);

                            System.out.print("Vuoi inserire altre sessioni? s/n: ");
                            condizione = readLineSafe(bf);
                        } while (condizione.contentEquals("s"));

                        adventureTime.confermaInserimento();
                    }
                    break;
                case 9:
                    adventureTime.visualizzaElencoAttivita();

                    if (!adventureTime.getElencoAttivita().isEmpty()) {
                        System.out.print("Id attività: ");
                        attivitaId = readLineSafe(bf);

                        Map<String, SessioneAttivita> sessioniSenzaGuida = adventureTime.mostraSessioniSenzaGuida(attivitaId);

                        if (sessioniSenzaGuida.isEmpty()) {
                            System.out.println("Nessuna sessione senza guida disponibile.");
                            break;
                        }

                        Map<String, Guida> guideDisponibili;
                        SessioneAttivita sessione;

                        do {
                            System.out.print("Id sessione: ");
                            sessioneId = readLineSafe(bf);
                            sessione = sessioniSenzaGuida.get(sessioneId);

                            if (sessione == null) {
                                System.out.println("Id sessione non valido. Riprova.");
                                guideDisponibili = Map.of();
                                continue;
                            }

                            guideDisponibili = adventureTime.mostraGuideDisponibili(sessione);
                            if (guideDisponibili.isEmpty()) {
                                System.out.println("Nessuna guida compatibile.");
                            }
                        } while (guideDisponibili.isEmpty());

                        System.out.print("Id guida: ");
                        String guidaId = readLineSafe(bf);
                        adventureTime.assegnaGuida(sessione, guideDisponibili.get(guidaId));
                    }
                    break;

                case 10:
                    System.out.print("Età inferiora a 12 anni o superiore a 65 anni? s/n: ");
                    eta = readLineSafe(bf);
                    isRidotto = eta.equalsIgnoreCase("s");
                    adventureTime.acquistaBigliettoIngresso(isRidotto);
                    break;
                case 0:
                    System.out.println("Uscita.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }

        } while (scelta != 0);
    }

    public static int menu(BufferedReader bf) {
        try {
            System.out.println("\nMENU:");
            System.out.println("1. Acquista Biglietto Sessione");
            System.out.println("2. Inserisci Nuova Attività");
            System.out.println("3. Monitora Capacità Parco");
            System.out.println("4. Visualizza Elenco Attività");
            System.out.println("5. Rimborsa Biglietto Sessione");
            System.out.println("6. Elimina Sessione Attività");
            System.out.println("7. Registra Nuova Guida");
            System.out.println("8. Inserisci Sessione Attività");
            System.out.println("9. Assegna Guida");
            System.out.println("10. Acquista Biglietto Ingresso");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            return Integer.parseInt(bf.readLine());
        } catch (IOException e) {
            System.out.println("Errore lettura da tastiera");
            return -1;
        }
    }

    public static String readLineSafe(BufferedReader bf) {
        try {
            return bf.readLine();
        } catch (IOException e) {
            System.out.println("Errore input.");
            return "";
        }
    }
}
