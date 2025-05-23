package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "riviste")
public class Rivista extends ElementoCatalogo {
    public enum Periodicita {SETTIMANALE, MENSILE, SEMESTRALE}
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagina, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagina);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Periodicità: %s", periodicita);
    }
}


