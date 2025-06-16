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

        do {
            switch (scelta = menu(bf)) {
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

                    System.out.print("Vuoi inserire delle sessioni? s/n ");
                    String condizione = readLineSafe(bf);

                    while (Objects.equals(condizione, "s")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        System.out.println("Data e ora sessione (formato: GG/MM/AAAA HH:MM)");
                        System.out.println("Esempio: 31/12/2023 15:30");
                        String input = readLineSafe(bf);
                        LocalDateTime dataOra = LocalDateTime.parse(input, formatter);
                        System.out.print("Capienza massima: ");
                        int capienzaMassima = Integer.parseInt(readLineSafe(bf));
                        System.out.print("Durata in ore:");
                        int ore = Integer.parseInt(readLineSafe(bf));
                        Duration durata = Duration.ofHours(ore);

                        adventureTime.inserisciSessioneAttivita(dataOra, capienzaMassima, durata);

                        System.out.print("Vuoi inserire altre sessioni? s/n ");
                        condizione = readLineSafe(bf);
                    }

                    adventureTime.confermaInserimento();
                    break;
                case 4:
                    adventureTime.visualizzaElencoAttivita();
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
                case 9:
                    adventureTime.visualizzaElencoAttivita();
                    if (!adventureTime.getElencoAttivita().isEmpty()) {
                        System.out.print("Id attività: ");
                        String attivitaId = readLineSafe(bf);
                        Map<String, SessioneAttivita> sessioniSenzaGuida = adventureTime.mostraSessioniSenzaGuida(attivitaId);
                        System.out.print("Id sessione: ");
                        String sessioneId = readLineSafe(bf);
                        SessioneAttivita sessione = sessioniSenzaGuida.get(sessioneId);
                        Map<String, Guida> guideDisponibili = adventureTime.mostraGuideDisponibili(sessione);
                        System.out.print("Id guida: ");
                        String guidaId = readLineSafe(bf);
                        adventureTime.assegnaGuida(sessione, guideDisponibili.get(guidaId));
                        System.out.print("Operazione completata");
                    }
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
            System.out.println("2. Inserisci Nuova Attività");
            System.out.println("4. Visualizza Elenco Attività");
            System.out.println("7. Registra Nuova Guida");
            System.out.println("9. Assegna Guida");
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
