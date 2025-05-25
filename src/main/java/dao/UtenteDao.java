package dao;

import entities.Utente;
import jakarta.persistence.*;

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

    public Utente getByNumeroTessera(String numeroTessera) {
        try {
            TypedQuery<Utente> query = em.createQuery(
                    "SELECT u FROM Utente u WHERE u.numeroTessera = :numeroTessera", Utente.class);
            query.setParameter("numeroTessera", numeroTessera);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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
