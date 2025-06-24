package main;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BigliettoIngressoFactory extends BigliettoFactory {
    private final float prezzoFinale;
    private String id;

    public BigliettoIngressoFactory(String id, float prezzoBase, boolean isRidotto) {
        this.id = id;
        this.prezzoFinale = calcolaPrezzo(prezzoBase, isRidotto);
    }

    public float calcolaPrezzo(float prezzoBase, boolean isRidotto) {
        float prezzo = isRidotto ? prezzoBase * 0.8f : prezzoBase;

        DayOfWeek giorno = LocalDate.now().getDayOfWeek();
        if (giorno == DayOfWeek.SATURDAY || giorno == DayOfWeek.SUNDAY) {
            prezzo *= 1.2f;
        }

        return prezzo;
    }

    @Override
    public Biglietto creaBiglietto() {
        return new BigliettoIngresso(id, prezzoFinale);
    }
}
