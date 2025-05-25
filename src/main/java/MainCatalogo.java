import entities.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainCatalogo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArchivioCatalogo archivio = new ArchivioCatalogo();

        int scelta = -1;
        do {
            System.out.println("\nCatalogo Bibliotecario");
            System.out.println("1 Aggiungi un elemento");
            System.out.println("2 Cerca un elemento per ISBN");
            System.out.println("3 Rimuovi un elemento per ISBN");
            System.out.println("4 Cerca elementi per anno di pubblicazione");
            System.out.println("5 Cerca libri per autore");
            System.out.println("6 Aggiorna un elemento esistente");
            System.out.println("7 Mostra prestiti attivi per utente");
            System.out.println("8 Mostra prestiti scaduti e non restituiti");
            System.out.println("9 Visualizza il catalogo");
            System.out.println("0 Esci");

            System.out.print("\nScegli un'opzione: ");

            try {
                scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1 -> aggiungiElemento(scanner, archivio);
                    case 2 -> cercaPerIsbn(scanner, archivio);
                    case 3 -> rimuoviElemento(scanner, archivio);
                    case 4 -> cercaPerAnno(scanner, archivio);
                    case 5 -> cercaPerAutore(scanner, archivio);
                    case 6 -> aggiornaElemento(scanner, archivio);
                    case 7 -> mostraPrestitiAttivi(scanner, archivio);
                    case 8 -> mostraPrestitiScaduti(archivio);
                    case 9 -> archivio.stampaCatalogo();
                    case 0 -> System.out.println("Uscita dal catalogo bibliotecario.");
                    default -> System.out.println("Scelta non valida, riprova!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input non valido! Inserisci un numero.");
                scanner.nextLine(); // reset input
            } catch (Exception e) {
                System.out.println("Errore inatteso: " + e.getMessage());
            }
        } while (scelta != 0);

        scanner.close();
    }

    private static void aggiungiElemento(Scanner scanner, ArchivioCatalogo archivio) {
        try {
            System.out.println("Scegli tipo di elemento (1=Libro, 2=Rivista): ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Titolo: ");
            String titolo = scanner.nextLine();
            System.out.print("Anno pubblicazione: ");
            int anno = scanner.nextInt();
            System.out.print("Numero pagine: ");
            int pagine = scanner.nextInt();
            scanner.nextLine();

            if (tipo == 1) {
                System.out.print("Autore: ");
                String autore = scanner.nextLine();
                System.out.print("Genere: ");
                String genere = scanner.nextLine();
                archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
            } else if (tipo == 2) {
                Rivista.Periodicita periodicita = null;
                while (periodicita == null) {
                    System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                    String input = scanner.nextLine().toUpperCase();
                    try {
                        periodicita = Rivista.Periodicita.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Periodicità non valida. Riprova.");
                    }
                }
                archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, periodicita));
            } else {
                System.out.println("Tipo non valido.");
            }
        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta: " + e.getMessage() + " Verifica che l'ISBN sia univoco");
        }
    }

    private static void cercaPerIsbn(Scanner scanner, ArchivioCatalogo archivio) {
        try {
            System.out.print("Inserisci ISBN da cercare: ");
            String isbn = scanner.nextLine();
            ElementoCatalogo elemento = archivio.cercaPerIsbn(isbn);
            System.out.println("Elemento trovato:\n" + elemento);
        } catch (ElementoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rimuoviElemento(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("Inserisci ISBN dell'elemento da eliminare: ");
        String isbn = scanner.nextLine();
        archivio.eliminaElemento(isbn);
    }

    private static void cercaPerAnno(Scanner scanner, ArchivioCatalogo archivio) {
        try {
            System.out.print("Inserisci anno: ");
            int anno = scanner.nextInt();
            scanner.nextLine();
            List<ElementoCatalogo> risultati = archivio.cercaPerAnno(anno);
            if (risultati.isEmpty()) {
                System.out.println("Nessun elemento trovato per l’anno: " + anno);
            } else {
                risultati.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Anno non valido.");
            scanner.nextLine();
        }
    }

    private static void cercaPerAutore(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("Inserisci autore: ");
        String autore = scanner.nextLine();
        List<Libro> risultati = archivio.cercaPerAutore(autore);
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro trovato per l'autore: " + autore);
        } else {
            risultati.forEach(System.out::println);
        }
    }

    private static void aggiornaElemento(Scanner scanner, ArchivioCatalogo archivio) {
        try {
            System.out.print("Inserisci ISBN dell'elemento da aggiornare: ");
            String isbn = scanner.nextLine();

            ElementoCatalogo originale = archivio.cercaPerIsbn(isbn);
            if (originale == null) {
                System.out.println("Elemento non trovato.");
                return;
            }

            System.out.print("Nuovo titolo: ");
            String titolo = scanner.nextLine();
            System.out.print("Nuovo anno: ");
            int anno = scanner.nextInt();
            System.out.print("Nuove pagine: ");
            int pagine = scanner.nextInt();
            scanner.nextLine();

            ElementoCatalogo nuovo = null;
            if (originale instanceof Libro) {
                System.out.print("Nuovo autore: ");
                String autore = scanner.nextLine();
                System.out.print("Nuovo genere: ");
                String genere = scanner.nextLine();
                nuovo = new Libro(isbn, titolo, anno, pagine, autore, genere);
            } else if (originale instanceof Rivista) {
                Rivista.Periodicita periodicita = null;
                while (periodicita == null) {
                    System.out.print("Nuova periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                    try {
                        periodicita = Rivista.Periodicita.valueOf(scanner.nextLine().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Periodicità non valida.");
                    }
                }
                nuovo = new Rivista(isbn, titolo, anno, pagine, periodicita);
            }

            archivio.aggiornaElemento(isbn, nuovo);
        } catch (ElementoNonTrovatoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante l’aggiornamento: " + e.getMessage());
        }
    }

    private static void mostraPrestitiAttivi(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("Numero tessera utente: ");
        String tessera = scanner.nextLine();
        List<Prestito> prestiti = archivio.getPrestitiAttiviPerUtente(tessera);
        if (prestiti.isEmpty()) {
            System.out.println("Nessun prestito attivo per questa tessera.");
        } else {
            prestiti.forEach(System.out::println);
        }
    }

    private static void mostraPrestitiScaduti(ArchivioCatalogo archivio) {
        List<Prestito> scaduti = archivio.getPrestitiScadutiNonRestituiti();
        if (scaduti.isEmpty()) {
            System.out.println("Nessun prestito scaduto non restituito.");
        } else {
            scaduti.forEach(System.out::println);
        }
    }
}
