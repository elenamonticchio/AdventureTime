package test;

import main.Guida;
import main.SessioneAttivita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessioneAttivitaTest {
    private SessioneAttivita sessione;

    @BeforeEach
    public void setUp() {
        this.sessione = new SessioneAttivita("S1", LocalDateTime.now(), 20, Duration.ofHours(2));
    }

    @Test
    void testHasGuidaFalse() {
        assertFalse(this.sessione.hasGuida());
    }

    @Test
    void testHasGuidaTrue() {
        Guida guida = new Guida("G1", "Marco", "1234", "Trekking");
        this.sessione.setGuida(guida);
        assertTrue(sessione.hasGuida());
    }

    @Test
    void testCompareDataOraOverlappingTrue() {
        LocalDateTime inizio1 = LocalDateTime.of(2025, 6, 17, 10, 0);
        Duration durata1 = Duration.ofHours(2);
        SessioneAttivita sessione = new SessioneAttivita("S1", inizio1, 20, durata1);

        LocalDateTime inizio2 = LocalDateTime.of(2025, 6, 17, 11, 0);
        Duration durata2 = Duration.ofHours(1);

        assertTrue(sessione.compareDataOra(inizio2, durata2), "Le due sessioni si sovrappongono");
    }

    @Test
    void testCompareDataOraNonOverlappingFalse() {
        LocalDateTime inizio1 = LocalDateTime.of(2025, 6, 17, 10, 0);
        Duration durata1 = Duration.ofHours(2);
        SessioneAttivita sessione = new SessioneAttivita("S1", inizio1, 20, durata1);

        LocalDateTime inizio2 = LocalDateTime.of(2025, 6, 17, 12, 30);
        Duration durata2 = Duration.ofHours(1);

        assertFalse(sessione.compareDataOra(inizio2, durata2), "Le due sessioni non si sovrappongono");
    }

    @Test
    void testCompareDataOraTouchingTrue() {
        LocalDateTime inizio1 = LocalDateTime.of(2025, 6, 17, 10, 0);
        Duration durata1 = Duration.ofHours(2);
        SessioneAttivita sessione = new SessioneAttivita("S1", inizio1, 20, durata1);

        LocalDateTime inizio2 = LocalDateTime.of(2025, 6, 17, 12, 0);
        Duration durata2 = Duration.ofHours(1);

        assertTrue(sessione.compareDataOra(inizio2, durata2));
    }
}
