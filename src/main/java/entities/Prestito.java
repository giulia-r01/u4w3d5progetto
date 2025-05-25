package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@NamedQuery(name="getPrestitiScadutiNonRestituiti",
        query = "select p from Prestito p where p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < CURRENT_DATE")
@NamedQuery(name="getPrestitiAttiviPerUtente",
        query = "select p from Prestito p where p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL")
@Entity
@Table(name = "prestiti")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "elemento_prestato")
    private ElementoCatalogo elementoPrestato;

    @Column(name = "data_inizio_prestito")
    private LocalDate dataInizioPrestito;
    @Column(name = "data_restituzione_prevista")
    private LocalDate dataRestituzionePrevista;
    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;

    public Prestito() {

    }

    public Prestito(Utente utente, ElementoCatalogo elementoPrestato, LocalDate dataInizioPrestito) {
        this.utente = utente;
        this.elementoPrestato = elementoPrestato;
        this.dataInizioPrestito = dataInizioPrestito;
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }


    public Long getId() {
        return id;
    }
    public Utente getUtente() {
        return utente;
    }
    public ElementoCatalogo getElementoPrestato() {
        return elementoPrestato;
    }
    public LocalDate getDataInizioPrestito() {
        return dataInizioPrestito;
    }
    public LocalDate getDataRestituzionePrevista() {
        return dataRestituzionePrevista;
    }
    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    public void setElementoPrestato(ElementoCatalogo elementoPrestato) {
        this.elementoPrestato = elementoPrestato;
    }
    public void setDataInizioPrestito(LocalDate dataInizioPrestito) {
        this.dataInizioPrestito = dataInizioPrestito;
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30); // ricalcolo automatico
    }

    public void setDataRestituzionePrevista(LocalDate dataRestituzionePrevista) {
        this.dataRestituzionePrevista = dataRestituzionePrevista;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataRestituzioneEffettiva(LocalDate dataRestituzioneEffettiva) {
        this.dataRestituzioneEffettiva = dataRestituzioneEffettiva;
    }

    @Override
    public String toString() {
        return "Prestito{" +
                "id=" + id +
                ", utente=" + utente +
                ", elementoPrestato=" + elementoPrestato +
                ", dataInizioPrestito=" + dataInizioPrestito +
                ", dataRestituzionePrevista=" + dataRestituzionePrevista +
                ", dataRestituzioneEffettiva=" + dataRestituzioneEffettiva +
                '}';
    }
}
