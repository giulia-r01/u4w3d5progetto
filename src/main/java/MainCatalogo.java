
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
            System.out.println("\n📚 Catalogo Bibliotecario");
            System.out.println("1️⃣ Aggiungi un elemento");
            System.out.println("2️⃣ Cerca un elemento per ISBN");
            System.out.println("3️⃣ Rimuovi un elemento per ISBN");
            System.out.println("4️⃣ Cerca elementi per anno di pubblicazione");
            System.out.println("5️⃣ Cerca libri per autore");
            System.out.println("6️⃣ Aggiorna un elemento esistente");
            System.out.println("7️⃣ Mostra prestiti attivi per utente");
            System.out.println("8️⃣ Mostra prestiti scaduti e non restituiti");
            System.out.println("9️⃣ Visualizza il catalogo");
            System.out.println("0️⃣ Esci");

            System.out.print("\nScegli un'opzione: ");

            try {
                scelta = scanner.nextInt();
                scanner.nextLine(); // Pulizia buffer

                switch (scelta) {
                    case 1 -> aggiungiElemento(scanner, archivio);
                    case 2 -> cercaPerIsbn(scanner, archivio);
                    case 3 -> rimuoviElemento(scanner, archivio);
                    case 4 -> cercaPerAnno(scanner, archivio);
                    case 5 -> cercaPerAutore(scanner, archivio);
                    case 6 -> aggiornaElemento(scanner, archivio);
                    case 7 -> {
                        System.out.print("📜 Inserisci numero tessera utente: ");
                        String numeroTessera = scanner.nextLine();
                        List<Prestito> prestiti = archivio.getPrestitiAttiviPerUtente(numeroTessera);
                        if (prestiti.isEmpty()) {
                            System.out.println("Nessun prestito attivo per questa tessera.");
                        } else {
                            prestiti.forEach(System.out::println);
                        }
                    }
                    case 8 -> {
                        List<Prestito> prestitiScaduti = archivio.getPrestitiScadutiNonRestituiti();
                        if (prestitiScaduti.isEmpty()) {
                            System.out.println("Nessun prestito scaduto e non restituito.");
                        } else {
                            prestitiScaduti.forEach(System.out::println);
                        }
                    }
                    case 9 -> archivio.stampaCatalogo();
                    case 0 -> System.out.println("Uscita dal catalogo bibliotecario.");
                    default -> System.out.println("⚠️ Scelta non valida, riprova!");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Carattere non consentito! Assicurati di digitare un numero. Riprova!");
                scanner.nextLine();
            }
        } while (scelta != 0);

        scanner.close();
    }

    private static void aggiungiElemento(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.println("📌 Scegli tipo di elemento (1=Libro, 2=Rivista): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("📖 ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("📖 Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("📅 Anno pubblicazione: ");
        int anno = scanner.nextInt();
        System.out.print("📑 Numero pagine: ");
        int pagine = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("🖊️ Autore: ");
            String autore = scanner.nextLine();
            System.out.print("📚 Genere: ");
            String genere = scanner.nextLine();
            archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
        } else if (tipo == 2) {
            Rivista.Periodicita periodicita = null;
            while (periodicita == null) {
                System.out.print("📅 Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                String periodicitaInput = scanner.nextLine().toUpperCase();
                try {
                    periodicita = Rivista.Periodicita.valueOf(periodicitaInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Errore: Periodicità non valida. Riprova!");
                }
            }
            archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, periodicita));
        } else {
            System.out.println("⚠️ Tipo non valido!");
        }
    }

    private static void cercaPerIsbn(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("🔎 Inserisci ISBN da cercare: ");
        String isbn = scanner.nextLine();

        ElementoCatalogo elemento = archivio.cercaPerIsbn(isbn);
        if (elemento != null) {
            System.out.println("✅ Elemento trovato:\n" + elemento);
        } else {
            System.out.println("❌ Nessun elemento trovato con ISBN " + isbn);
        }
    }

    private static void rimuoviElemento(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("🗑️ Inserisci ISBN dell'elemento da eliminare: ");
        String isbn = scanner.nextLine();
        archivio.eliminaElemento(isbn);
    }

    private static void cercaPerAnno(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("🔎 Inserisci anno di pubblicazione da cercare: ");
        int anno = scanner.nextInt();
        scanner.nextLine();

        List<ElementoCatalogo> risultati = archivio.cercaPerAnno(anno);
        if (risultati.isEmpty()) {
            System.out.println("❌ Nessun elemento trovato per l’anno: " + anno);
        } else {
            risultati.forEach(System.out::println);
        }
    }

    private static void cercaPerAutore(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("🖊️ Inserisci il nome dell'autore: ");
        String autore = scanner.nextLine();

        List<Libro> risultati = archivio.cercaPerAutore(autore);
        if (risultati.isEmpty()) {
            System.out.println("❌ Nessun libro trovato per l'autore: " + autore);
        } else {
            risultati.forEach(System.out::println);
        }
    }

    private static void aggiornaElemento(Scanner scanner, ArchivioCatalogo archivio) {
        System.out.print("🔄 Inserisci ISBN dell'elemento da aggiornare: ");
        String isbn = scanner.nextLine();

        ElementoCatalogo elementoOriginale = archivio.cercaPerIsbn(isbn);
        if (elementoOriginale == null) {
            System.out.println("❌ Errore: Nessun elemento trovato con ISBN " + isbn);
            return;
        }

        System.out.print("🖊️ Nuovo titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("📅 Nuovo anno di pubblicazione: ");
        int anno = scanner.nextInt();
        System.out.print("📑 Nuovo numero di pagine: ");
        int pagine = scanner.nextInt();
        scanner.nextLine();

        archivio.aggiornaElemento(isbn, elementoOriginale);
    }
}
