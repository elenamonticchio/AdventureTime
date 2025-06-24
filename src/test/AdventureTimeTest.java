package test;

import main.*;
import org.junit.jupiter.api.Assertions;
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
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Kayak sul Lago", "Giro in kayak sul lago", 30.0f, DifficoltaEnum.FACILE);
        adventureTime.confermaInserimento();

        adventureTime.inserisciNuovaAttivita("Arrampicata Sportiva", "Sessione di arrampicata indoor", 70.0f, DifficoltaEnum.DIFFICILE);
        adventureTime.confermaInserimento();

        Map<String, TipoAttivita> elencoAttivita = adventureTime.getElencoAttivita();
        assertEquals(2, elencoAttivita.size());
        assertNotNull(elencoAttivita.get("A0"));
        assertNotNull(elencoAttivita.get("A1"));
    }

    @Test
    void testInserisciSessioneAttivita() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        adventureTime.inserisciNuovaAttivita("Canyoning", "Discesa nei canyon", 90.0f, DifficoltaEnum.MEDIA);

        LocalDateTime dataOra = LocalDateTime.now();
        int capienzaMassima = 10;
        Duration durata = Duration.ofHours(4);

        adventureTime.inserisciSessioneAttivita(dataOra, capienzaMassima, durata);
        adventureTime.confermaInserimento();

        TipoAttivita attivita = adventureTime.getElencoAttivita().get("A0");
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
        assertTrue(output.contains("40,00"));
        assertTrue(output.contains("MEDIA"));
    }

    @Test
    public void testRegistraGuida() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.registraGuida("Mario Rossi", "AT123", "Escursionismo");

        Map<String, Guida> guide = adventureTime.getElencoGuide();
        assertEquals(1, guide.size());

        Guida g = guide.get("G0");
        assertNotNull(g);
        assertEquals("Mario Rossi", g.getNome());
        assertEquals("AT123", g.getCodiceAttestato());
        assertEquals("Escursionismo", g.getSpecializzazione());
    }

    @Test
    public void testRegistraMultipleGuide() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.registraGuida("Mario Rossi", "AT123", "Escursionismo");
        adventureTime.registraGuida("Luca Bianchi", "AT456", "Arrampicata");

        Map<String, Guida> guide = adventureTime.getElencoGuide();
        assertEquals(2, guide.size());

        assertTrue(guide.containsKey("G0"));
        assertTrue(guide.containsKey("G1"));
    }

    @Test
    public void testMostraSessioniSenzaGuida() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Arrampicata", "Parete di roccia", 50.0f, DifficoltaEnum.MEDIA);
        LocalDateTime ora = LocalDateTime.now().plusDays(1);
        Duration durata = Duration.ofHours(2);
        adventureTime.inserisciSessioneAttivita(ora, 10, durata);
        adventureTime.confermaInserimento();

        String attivitaId = "A0";
        Map<String, SessioneAttivita> sessioniSenzaGuida = adventureTime.mostraSessioniSenzaGuida(attivitaId);
        assertEquals(1, sessioniSenzaGuida.size());
        SessioneAttivita sessione = sessioniSenzaGuida.values().iterator().next();
        assertNull(sessione.getGuida());
    }

    @Test
    public void testMostraGuideDisponibili() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Canoa", "Discesa fluviale", 40.0f, DifficoltaEnum.FACILE);
        LocalDateTime ora = LocalDateTime.now().plusDays(2);
        Duration durata = Duration.ofHours(3);
        adventureTime.inserisciSessioneAttivita(ora, 8, durata);
        adventureTime.confermaInserimento();

        adventureTime.registraGuida("Anna Rossi", "1234", "Canoa");
        String attivitaId = "A0";
        SessioneAttivita sessione = adventureTime.mostraSessioniSenzaGuida(attivitaId)
                .values().iterator().next();

        Map<String, Guida> guideDisponibili = adventureTime.mostraGuideDisponibili(sessione);
        assertEquals(1, guideDisponibili.size());
        assertTrue(guideDisponibili.values().stream().anyMatch(g -> g.getNome().equals("Anna Rossi")));
    }

    @Test
    void testMostraGuideDisponibili_guidaNonSpecializzata() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Escursionismo", "Camminata in montagna", 30.0f, DifficoltaEnum.MEDIA);
        LocalDateTime inizio = LocalDateTime.of(2025, 6, 20, 9, 0);
        Duration durata = Duration.ofHours(2);
        adventureTime.inserisciSessioneAttivita(inizio, 15, durata);
        adventureTime.confermaInserimento();

        String attivitaId = "A0";
        SessioneAttivita sessione = adventureTime.getElencoAttivita().get(attivitaId).getElencoSessioni().get("A0S0");

        adventureTime.registraGuida("Luca", "AT123", "Kayak");

        Map<String, Guida> disponibili = adventureTime.mostraGuideDisponibili(sessione);

        assertTrue(disponibili.isEmpty());
    }

    @Test
    public void testAssegnaGuida() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Trekking", "Sentieri montani", 60.0f, DifficoltaEnum.DIFFICILE);
        LocalDateTime ora = LocalDateTime.now().plusDays(3);
        Duration durata = Duration.ofHours(4);
        adventureTime.inserisciSessioneAttivita(ora, 12, durata);
        adventureTime.confermaInserimento();

        adventureTime.registraGuida("Luca Bianchi", "5678", "Trekking");

        SessioneAttivita sessione = adventureTime.mostraSessioniSenzaGuida("A0")
                .values().iterator().next();
        Guida guida = adventureTime.mostraGuideDisponibili(sessione)
                .values().iterator().next();

        adventureTime.assegnaGuida(sessione, guida);

        assertEquals(guida, sessione.getGuida());
        assertTrue(guida.getSessioniAssegnate().containsValue(sessione));
    }

    @Test
    void testVisualizzaSessioniAttivita() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        adventureTime.inserisciNuovaAttivita("Trekking", "Escursione in montagna", 30.0f, DifficoltaEnum.MEDIA);

        adventureTime.inserisciSessioneAttivita(
                LocalDateTime.of(2025, 6, 20, 10, 0),
                20,
                Duration.ofHours(2)
        );
        adventureTime.inserisciSessioneAttivita(
                LocalDateTime.of(2025, 6, 21, 15, 30),
                15,
                Duration.ofHours(3)
        );

        adventureTime.confermaInserimento();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String attivitaId = adventureTime.getElencoAttivita().keySet().iterator().next();
        adventureTime.visualizzaSessioniAttivita(attivitaId);

        String output = outContent.toString().trim();
        assertTrue(output.contains("A0S0"));
        assertTrue(output.contains("A0S1"));
    }

    @Test
    void testEliminaSessione() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        adventureTime.inserisciNuovaAttivita("Trekking", "Escursione in montagna", 30.0f, DifficoltaEnum.MEDIA);
        adventureTime.inserisciSessioneAttivita(
                LocalDateTime.of(2025, 6, 20, 10, 0),
                20,
                Duration.ofHours(2)
        );
        adventureTime.inserisciSessioneAttivita(
                LocalDateTime.of(2025, 6, 21, 15, 30),
                15,
                Duration.ofHours(3)
        );
        adventureTime.confermaInserimento();

        String attivitaId = adventureTime.getElencoAttivita().keySet().iterator().next();
        adventureTime.visualizzaSessioniAttivita(attivitaId);

        TipoAttivita attivita = adventureTime.getElencoAttivita().get(attivitaId);
        String sessioneId = attivita.getElencoSessioni().keySet().iterator().next();

        adventureTime.eliminaSessione(sessioneId);

        Assertions.assertFalse(attivita.getElencoSessioni().containsKey(sessioneId));
    }

    @Test
    public void testSelezionaAttivita() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        adventureTime.inserisciNuovaAttivita("Trekking", "Escursione in montagna", 30.0f, DifficoltaEnum.MEDIA);
        adventureTime.confermaInserimento();

        String attivitaId = adventureTime.getElencoAttivita().keySet().iterator().next();

        adventureTime.selezionaAttivita(attivitaId);

        assertEquals(attivitaId, adventureTime.getAttivitaCorrente().getId());
    }

    @Test
    void testAcquistaBigliettoSessione_SenzaSconto() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        String nomeAttivita = "Rafting";
        float prezzo = 30.0f;
        adventureTime.inserisciNuovaAttivita(nomeAttivita, "Discesa in fiume", prezzo, DifficoltaEnum.FACILE);
        adventureTime.inserisciSessioneAttivita(LocalDateTime.of(2025, 6, 27, 10, 0), 10, Duration.ofHours(2));
        adventureTime.confermaInserimento();

        String attivitaId = adventureTime.getAttivitaCorrente().getId();
        SessioneAttivita sessione = adventureTime.getAttivitaCorrente().getElencoSessioni().values().iterator().next();
        String sessioneId = sessione.getId();

        adventureTime.acquistaBigliettoSessione(sessioneId, false);

        assertEquals(1, sessione.getPartecipantiAttuali(), "Numero partecipanti deve essere 1");
        Biglietto biglietto = adventureTime.getElencoBiglietti().get("B0");
        assertNotNull(biglietto);
        assertEquals(prezzo, biglietto.getPrezzo(), 0.01);
    }

    @Test
    void testAcquistaBigliettoSessione_Ridotto() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        float prezzoBase = 40.0f;
        adventureTime.inserisciNuovaAttivita("Kayak", "Tour in acqua", prezzoBase, DifficoltaEnum.MEDIA);
        adventureTime.inserisciSessioneAttivita(LocalDateTime.of(2025, 6, 28, 11, 0), 5, Duration.ofHours(1));
        adventureTime.confermaInserimento();

        SessioneAttivita sessione = adventureTime.getAttivitaCorrente().getElencoSessioni().values().iterator().next();
        String sessioneId = sessione.getId();

        adventureTime.acquistaBigliettoSessione(sessioneId, true);

        assertEquals(1, sessione.getPartecipantiAttuali());
        Biglietto biglietto = adventureTime.getElencoBiglietti().get("B0");
        assertNotNull(biglietto);
        float expectedPrezzo = prezzoBase * 0.8f;
        assertEquals(expectedPrezzo, biglietto.getPrezzo(), 0.01);
    }

    @Test
    void testAcquistoBigliettoIngressoNonRidotto() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        int visitatoriPrecedenti = adventureTime.getVisitatoriAttuali();

        adventureTime.acquistaBigliettoIngresso(false);

        assertNotNull(adventureTime.getElencoBiglietti().get("B0"));
        assertEquals(visitatoriPrecedenti + 1, adventureTime.getVisitatoriAttuali());
    }

    @Test
    void testAcquistoBigliettoIngressoRidotto() {
        AdventureTime adventureTime = AdventureTime.getInstance();
        int visitatoriPrecedenti = adventureTime.getVisitatoriAttuali();

        adventureTime.acquistaBigliettoIngresso(true);

        assertNotNull(adventureTime.getElencoBiglietti().get("B0"));
        assertEquals(visitatoriPrecedenti + 1, adventureTime.getVisitatoriAttuali());
    }

    @Test
    void testAcquistoBigliettoIngressoConCapienzaMassima() {
        AdventureTime adventureTime = AdventureTime.getInstance();

        for (int i = 0; i < 320; i++) {
            adventureTime.acquistaBigliettoIngresso(false);
        }

        int visitatoriPrima = adventureTime.getVisitatoriAttuali();

        adventureTime.acquistaBigliettoIngresso(false);

        assertEquals(visitatoriPrima, adventureTime.getVisitatoriAttuali());
        assertNull(adventureTime.getElencoBiglietti().get("B320"));
    }

}