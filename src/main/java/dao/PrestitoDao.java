package dao;

import entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PrestitoDao {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
    private final EntityManager em = emf.createEntityManager();

    public void save(Prestito p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public Prestito getById(Long id){
        return em.find(Prestito.class, id);
    }

    public void remove(Long id){
        Prestito p = getById(id);

        if (p != null) {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        }
        else {
            System.out.println("Prestito con id " + id + " non trovato");
        }


    }

    //Metodo Ricerca di tutti i prestiti scaduti e non ancora restituiti
    public List<Prestito> getPrestitiScadutiNonRestituiti(){
        TypedQuery<Prestito> query = em.createNamedQuery("getPrestitiScadutiNonRestituiti",
                Prestito.class);
        return query.getResultList();
    }

    //Metodo Ricerca degli elementi attualmente in prestito dato un numero di tessera utente
    public List<Prestito> getPrestitiAttiviPerUtente(String numeroTessera) {
        TypedQuery<Prestito> query = em.createNamedQuery("getPrestitiAttiviPerUtente", Prestito.class)
                .setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }



    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }
}
