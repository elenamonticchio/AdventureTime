package main;

public class BigliettoSessioneFactory extends BigliettoFactory {
    private final float prezzoFinale;
    private final SessioneAttivita sessione;
    private String id;
    private boolean isRidotto;

    public BigliettoSessioneFactory(String id, float prezzoBase, SessioneAttivita sessione, boolean isRidotto) {
        this.id = id;
        this.prezzoFinale = calcolaPrezzo(prezzoBase, isRidotto);
        this.sessione = sessione;
    }

    public float calcolaPrezzo(float prezzoBase, boolean isRidotto) {
        return isRidotto ? prezzoBase * 0.8f : prezzoBase;
    }

    @Override
    public Biglietto creaBiglietto() {
        return new BigliettoSessione(id, prezzoFinale, sessione);
    }
}
