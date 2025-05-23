package dao;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtenteDao {
    private EntityManager em;

    public UtenteDao(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
        em = emf.createEntityManager();
    }

    public void save(Utente u){
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public Utente getById(Long id){
        return em.find(Utente.class, id);
    }

    public void remove(Long id){
        Utente u = getById(id);

        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        }
        else {
            System.out.println("Utente con id " + id + " non trovato");
        }


    }

    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }
}
