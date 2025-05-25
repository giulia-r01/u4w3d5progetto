package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQuery(name = "cercaPerAutore", query = "SELECT l FROM Libro l WHERE LOWER(l.autore) LIKE LOWER(CONCAT('%', :autore, '%'))")
@Entity
@Table(name = "libri")
public class Libro extends ElementoCatalogo {
    private String autore;
    private String genere;

    public Libro() {
        super();
    }

    public Libro(String isbn, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }


    public String getAutore() { return autore; }
    public String getGenere() { return genere; }

    public void setAutore(String autore) { this.autore = autore; }
    public void setGenere(String genere) { this.genere = genere; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Autore: %s | Genere: %s", autore, genere);
    }
}