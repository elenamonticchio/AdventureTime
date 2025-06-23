package main;

public class BigliettoIngressoFactory extends BigliettoFactory {
    private final float prezzo;
    private String id;

    public BigliettoIngressoFactory(String id, float prezzo) {
        this.id = id;
        this.prezzo = prezzo;
    }

    @Override
    public Biglietto creaBiglietto() {
        return new BigliettoIngresso(id, prezzo);
    }
}
