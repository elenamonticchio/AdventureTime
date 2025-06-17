package test;

import main.AdventureTime;
import main.DifficoltaEnum;
import main.SessioneAttivita;
import main.TipoAttivita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AdventureTimeTest {
    private AdventureTime adventureTime;


    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field instance = AdventureTime.class.getDeclaredField("adventureTime");
        instance.setAccessible(true);
        instance.set(null, null);

        Field contatoreAttivita = AdventureTime.class.getDeclaredField("contatoreAttivita");
        contatoreAttivita.setAccessible(true);
        contatoreAttivita.set(AdventureTime.getInstance(), 0);
    }


    @Test
    void testInserisciNuovaAttivita() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        String nome = "Arrampicata";
        String descrizione = "Arrampicata per principianti";
        float prezzo = 25.5f;
        DifficoltaEnum difficolta = DifficoltaEnum.FACILE;

        adventureTime.inserisciNuovaAttivita(nome, descrizione, prezzo, difficolta);
        adventureTime.confermaInserimento();

        Map<String, TipoAttivita> elenco = adventureTime.getElencoAttivita();
        assertEquals(1, elenco.size());

        TipoAttivita attivita = elenco.values().iterator().next();
        assertEquals(nome, attivita.getNome());
        assertEquals(descrizione, attivita.getDescrizione());
        assertEquals(prezzo, attivita.getPrezzo());
        assertEquals(difficolta, attivita.getDifficolta());
    }

    @Test
    void testInserimentoMultiploAttivita() {
        AdventureTime at = AdventureTime.getInstance();

        at.inserisciNuovaAttivita("Kayak sul Lago", "Giro in kayak sul lago", 30.0f, DifficoltaEnum.FACILE);
        at.confermaInserimento(); // ID A0

        at.inserisciNuovaAttivita("Arrampicata Sportiva", "Sessione di arrampicata indoor", 70.0f, DifficoltaEnum.DIFFICILE);
        at.confermaInserimento(); // ID A1

        Map<String, TipoAttivita> elencoAttivita = at.getElencoAttivita();
        assertEquals(2, elencoAttivita.size());
        assertNotNull(elencoAttivita.get("A0"));
        assertNotNull(elencoAttivita.get("A1"));
    }

    @Test
    void testInserisciSessioneAttivita() {
        AdventureTime at = AdventureTime.getInstance();
        at.inserisciNuovaAttivita("Canyoning", "Discesa nei canyon", 90.0f, DifficoltaEnum.MEDIA);

        LocalDateTime dataOra = LocalDateTime.now();
        int capienzaMassima = 10;
        Duration durata = Duration.ofHours(4);

        at.inserisciSessioneAttivita(dataOra, capienzaMassima, durata);
        at.confermaInserimento();

        TipoAttivita attivita = at.getElencoAttivita().get("A0");
        assertNotNull(attivita);
        assertEquals(1, attivita.getElencoSessioni().size());
        assertTrue(attivita.getElencoSessioni().containsKey("A0S0"));
    }

    @Test
    public void testInserimentoMultiploSessioniAttivita() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Arrampicata", "Scalata su roccia", 50.0f, DifficoltaEnum.MEDIA);


        adventureTime.inserisciSessioneAttivita(LocalDateTime.of(2025, 7, 1, 10, 0), 10, Duration.ofHours(2));
        adventureTime.inserisciSessioneAttivita(LocalDateTime.of(2025, 7, 2, 14, 0), 8, Duration.ofHours(3));
        adventureTime.inserisciSessioneAttivita(LocalDateTime.of(2025, 7, 3, 9, 30), 12, Duration.ofHours(1));


        adventureTime.confermaInserimento();


        Map<String, TipoAttivita> attivitaMappa = adventureTime.getElencoAttivita();
        assertEquals(1, attivitaMappa.size());

        TipoAttivita attivita = attivitaMappa.values().iterator().next();
        Map<String, SessioneAttivita> sessioni = attivita.getElencoSessioni();


        assertEquals(3, sessioni.size());


        sessioni.values().forEach(System.out::println);
    }

    @Test
    public void testVisualizzaElencoAttivitaVuoto() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        adventureTime.visualizzaElencoAttivita();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Nessuna attività disponibile al momento."));
    }

    @Test
    public void testVisualizzaElencoAttivitaConAttivita() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        adventureTime.inserisciNuovaAttivita("Trekking", "Giro in montagna", 40.0f, DifficoltaEnum.MEDIA);
        adventureTime.confermaInserimento();

        adventureTime.visualizzaElencoAttivita();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Elenco delle Attività"));
        assertTrue(output.contains("Trekking"));
        assertTrue(output.contains("Giro in montagna"));
        assertTrue(output.contains("40,00")); // il prezzo è formattato con due decimali
        assertTrue(output.contains("MEDIA"));
    }
}