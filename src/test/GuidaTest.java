package test;

import main.Guida;
import main.SessioneAttivita;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GuidaTest {

    @Test
    void testIsDisponibileTrue() {
        Guida guida = new Guida("G1", "Anna", "A123", "Storia");
        LocalDateTime dataOra = LocalDateTime.of(2025, 6, 18, 10, 0);
        Duration durata = Duration.ofHours(2);

        assertTrue(guida.isDisponibile(dataOra, durata), "La guida dovrebbe essere disponibile senza sessioni assegnate");
    }

    @Test
    void testIsDisponibileFalseQuandoSessioneSovrapposta() {
        Guida guida = new Guida("G1", "Anna", "A123", "Storia");
        SessioneAttivita sessione = new SessioneAttivita("S1", LocalDateTime.of(2025, 6, 18, 10, 0), 20, Duration.ofHours(2));
        guida.assegnaGuida(sessione);

        LocalDateTime nuovaData = LocalDateTime.of(2025, 6, 18, 11, 0);
        Duration nuovaDurata = Duration.ofHours(1);

        assertFalse(guida.isDisponibile(nuovaData, nuovaDurata), "La guida non dovrebbe essere disponibile per sessione sovrapposta");
    }

    @Test
    void testIsDisponibileTrueQuandoSessioneNonSovrapposta() {
        Guida guida = new Guida("G1", "Anna", "A123", "Storia");
        SessioneAttivita sessione = new SessioneAttivita("S1", LocalDateTime.of(2025, 6, 18, 10, 0), 20, Duration.ofHours(2));
        guida.assegnaGuida(sessione);

        LocalDateTime nuovaData = LocalDateTime.of(2025, 6, 18, 13, 0); // Dopo la fine
        Duration nuovaDurata = Duration.ofHours(1);

        assertTrue(guida.isDisponibile(nuovaData, nuovaDurata), "La guida dovrebbe essere disponibile per sessione non sovrapposta");
    }

    @Test
    void testAssegnaGuidaAggiungeSessione() {
        Guida guida = new Guida("G1", "Anna", "A123", "Storia");
        SessioneAttivita sessione = new SessioneAttivita("S1", LocalDateTime.of(2025, 6, 19, 9, 0), 15, Duration.ofHours(1));

        guida.assegnaGuida(sessione);

        assertTrue(guida.getSessioniAssegnate().containsKey("S1"));
        assertEquals(sessione, guida.getSessioniAssegnate().get("S1"));
    }
}
