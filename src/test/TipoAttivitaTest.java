package test;

import main.DifficoltaEnum;
import main.Guida;
import main.SessioneAttivita;
import main.TipoAttivita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TipoAttivitaTest {

    private TipoAttivita tipoAttivita;

    @BeforeEach
    public void setUp() {
        tipoAttivita = new TipoAttivita("A0", "Trekking", "Percorso montano", 30.0f, DifficoltaEnum.FACILE);
    }

    @Test
    public void testInserisciSessioneAttivita() {
        LocalDateTime dataOra = LocalDateTime.of(2025, 7, 1, 9, 0);
        Duration durata = Duration.ofHours(2);

        tipoAttivita.inserisciSessioneAttivita(dataOra, 15, durata);

        Map<String, SessioneAttivita> sessioni = tipoAttivita.getElencoSessioni();

        assertEquals(1, sessioni.size());

        assertTrue(sessioni.containsKey("A0S0"));

        SessioneAttivita sessione = sessioni.get("A0S0");
        assertEquals(dataOra, sessione.getDataOra());
        assertEquals(durata, sessione.getDurata());
        assertEquals(15, sessione.getCapienzaMassima());
        assertEquals("A0S0", sessione.getId());
    }

    @Test
    public void testInserimentoSessioniMultiple() {
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.of(2025, 7, 1, 10, 0), 10, Duration.ofHours(2));
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.of(2025, 7, 2, 14, 0), 8, Duration.ofHours(3));
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.of(2025, 7, 3, 9, 30), 12, Duration.ofHours(1));

        Map<String, SessioneAttivita> sessioni = tipoAttivita.getElencoSessioni();
        assertEquals(3, sessioni.size());
        assertTrue(sessioni.containsKey("A0S0"));
        assertTrue(sessioni.containsKey("A0S1"));
        assertTrue(sessioni.containsKey("A0S2"));
    }

    @Test
    void testGetSessioniSenzaGuida_soloSessioniSenzaGuida() {
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.now(), 10, Duration.ofHours(2));

        Map<String, SessioneAttivita> senzaGuida = tipoAttivita.getSessioniSenzaGuida();

        assertEquals(1, senzaGuida.size());
        assertTrue(senzaGuida.values().stream().noneMatch(SessioneAttivita::hasGuida));
    }

    @Test
    void testGetSessioniSenzaGuida_conSessioneConGuida() {
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.now(), 10, Duration.ofHours(2));
        SessioneAttivita sessione = tipoAttivita.getElencoSessioni().values().iterator().next();

        Guida guida = new Guida("G1", "Marco", "1234", "Trekking");
        sessione.setGuida(guida);

        Map<String, SessioneAttivita> senzaGuida = tipoAttivita.getSessioniSenzaGuida();

        assertEquals(0, senzaGuida.size());
    }

    @Test
    void testGetSessioniSenzaGuida_miste() {
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.now(), 10, Duration.ofHours(2));
        tipoAttivita.inserisciSessioneAttivita(LocalDateTime.now().plusDays(1), 10, Duration.ofHours(2));

        SessioneAttivita sessioneConGuida = tipoAttivita.getElencoSessioni().values().iterator().next();
        sessioneConGuida.setGuida(new Guida("G2", "Laura", "Bianchi", "Trekking"));

        Map<String, SessioneAttivita> senzaGuida = tipoAttivita.getSessioniSenzaGuida();

        assertEquals(1, senzaGuida.size());
        assertTrue(senzaGuida.values().stream().noneMatch(SessioneAttivita::hasGuida));
    }
}
