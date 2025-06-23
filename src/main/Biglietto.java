package main;

public abstract class Biglietto {
    private final String id;
    private final float prezzo;

    public Biglietto(String id, float prezzo) {
        this.id = id;
        this.prezzo = prezzo;
    }

    public String getId() {
        return id;
    }

    public float getPrezzo() {
        return prezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" + "id='" + id + "', prezzo='" + prezzo + "€'}";
    }
}
