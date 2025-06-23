package main;

public class BigliettoSessione extends Biglietto {
    private final SessioneAttivita sessione;

    public BigliettoSessione(String id, float prezzo, SessioneAttivita sessione) {
        super(id, prezzo);
        this.sessione = sessione;
    }
}
