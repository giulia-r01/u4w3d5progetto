package entities;

import jakarta.persistence.*;

@NamedQuery(name = "cercaPerTitolo", query = "SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)")
@NamedQuery(name = "cercaPerAnno", query = "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno")
@Entity
@Table(name = "elementi_catalogo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementoCatalogo {
    @Id
    private String isbn;
    private String titolo;
    @Column(name = "anno_pubblicazione")
    private int annoPubblicazione;
    @Column(name = "numero_pagine")
    private int numeroPagine;

    public ElementoCatalogo(String isbn, String titolo, int annoPubblicazione, int numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    public ElementoCatalogo() {}

    public String getIsbn() { return isbn; }
    public String getTitolo() { return titolo; }
    public int getAnnoPubblicazione() { return annoPubblicazione; }
    public int getNumeroPagine() { return numeroPagine; }

    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setAnnoPubblicazione(int annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }
    public void setNumeroPagine(int numeroPagine) { this.numeroPagine = numeroPagine; }

    @Override
    public String toString() {
        return String.format("ElementoCatalogo{isbn='%s', titolo='%s', annoPubblicazione=%d, numeroPagine=%d}",
                isbn, titolo, annoPubblicazione, numeroPagine);
    }
}
