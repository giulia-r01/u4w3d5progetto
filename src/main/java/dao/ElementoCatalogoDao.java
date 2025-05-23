package dao;

import entities.ElementoCatalogo;
import entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ElementoCatalogoDao {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
    private final EntityManager em = emf.createEntityManager();

    public void save(ElementoCatalogo e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public ElementoCatalogo getById(String isbn) {
        return em.find(ElementoCatalogo.class, isbn);
    }

    public boolean remove(String isbn) {
        ElementoCatalogo e = getById(isbn);
        if (e != null) {
            em.getTransaction().begin();
            em.remove(e);
            em.getTransaction().commit();
            return true;
        } else {
            System.out.println("Elemento non trovato con ISBN: " + isbn);
            return false;
        }
    }

    public List<Libro> cercaPerAutore(String autore) {
        TypedQuery<Libro> query = em.createNamedQuery("cercaPerAutore", Libro.class)
                .setParameter("autore", autore);
        return query.getResultList();
    }

    public List<ElementoCatalogo> cercaPerTitolo(String titolo) {
        TypedQuery<ElementoCatalogo> query = em.createNamedQuery("cercaPerTitolo", ElementoCatalogo.class)
                .setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }

    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        TypedQuery<ElementoCatalogo> query = em.createNamedQuery("cercaPerAnno", ElementoCatalogo.class)
                .setParameter("anno", anno);
        return query.getResultList();
    }

    public void update(ElementoCatalogo e) {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    public List<ElementoCatalogo> getAll() {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e", ElementoCatalogo.class);
        return query.getResultList();
    }

    public void close() {
        if (em.isOpen()) {
            em.close();
        }
        emf.close();
    }
}