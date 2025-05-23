package entities;

import dao.ElementoCatalogoDao;
import dao.PrestitoDao;
import java.util.List;

public class ArchivioCatalogo {
    private ElementoCatalogoDao elementoCatalogoDao = new ElementoCatalogoDao();
    private PrestitoDao prestitoDao = new PrestitoDao();

    // ✅ Carica dati dal database
    public List<ElementoCatalogo> caricaDatiDaDatabase() {
        return elementoCatalogoDao.getAll();
    }

    // ✅ Aggiungi un elemento nel database
    public void aggiungiElemento(ElementoCatalogo elemento) {
        elementoCatalogoDao.save(elemento);
        System.out.println("✅ Elemento aggiunto con successo: " + elemento.getTitolo());
    }

    // ✅ Cerca un elemento per ISBN nel database
    public ElementoCatalogo cercaPerIsbn(String isbn) {
        ElementoCatalogo elemento = elementoCatalogoDao.getById(isbn);
        if (elemento == null) {
            throw new ElementoNonTrovatoException("ISBN non trovato: " + isbn);
        }
        return elemento;
    }

    // ✅ Cerca per titolo usando Named Query
    public List<ElementoCatalogo> cercaPerTitolo(String titolo) {
        return elementoCatalogoDao.cercaPerTitolo(titolo);
    }

    // ✅ Cerca per anno usando Named Query
    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return elementoCatalogoDao.cercaPerAnno(anno);
    }

    // Cerca per autore
    public List<Libro> cercaPerAutore(String autore) {
        return elementoCatalogoDao.cercaPerAutore(autore);
    }

    // Cerca prestiti attivi per utente
    public List<Prestito> getPrestitiAttiviPerUtente(String numeroTessera) {
        return prestitoDao.getPrestitiAttiviPerUtente(numeroTessera);
    }

    // Cerca prestiti scaduti e non restituiti
    public List<Prestito> getPrestitiScadutiNonRestituiti() {
        return prestitoDao.getPrestitiScadutiNonRestituiti();
    }

    // Registra un prestito
    public void registraPrestito(Prestito prestito) {
        prestitoDao.save(prestito);
        System.out.println("Prestito registrato!");
    }

    // Rimuovi elemento nel database
    public void eliminaElemento(String isbn) {
        elementoCatalogoDao.remove(isbn);
        System.out.println("Elemento rimosso con successo!");
    }

    // Aggiorna elemento nel database
    public void aggiornaElemento(String isbn, ElementoCatalogo nuovo) {
        ElementoCatalogo elementoDaAggiornare = elementoCatalogoDao.getById(isbn);
        if (elementoDaAggiornare == null) {
            throw new ElementoNonTrovatoException("Elemento da aggiornare non trovato con ISBN: " + isbn);
        }

        elementoDaAggiornare.setTitolo(nuovo.getTitolo());
        elementoDaAggiornare.setAnnoPubblicazione(nuovo.getAnnoPubblicazione());
        elementoDaAggiornare.setNumeroPagine(nuovo.getNumeroPagine());

        if (elementoDaAggiornare instanceof Libro libro && nuovo instanceof Libro nuovoLibro) {
            libro.setAutore(nuovoLibro.getAutore());
            libro.setGenere(nuovoLibro.getGenere());
        }

        if (elementoDaAggiornare instanceof Rivista rivista && nuovo instanceof Rivista nuovaRivista) {
            rivista.setPeriodicita(nuovaRivista.getPeriodicita());
        }

        elementoCatalogoDao.update(elementoDaAggiornare);
        System.out.println("Elemento con ISBN " + isbn + " aggiornato con successo!");
    }

    public void stampaCatalogo() {
        List<ElementoCatalogo> catalogo = caricaDatiDaDatabase();
        if (catalogo == null || catalogo.isEmpty()) {
            System.out.println("Il catalogo è vuoto.");
        } else {
            catalogo.forEach(System.out::println);
        }
    }
}