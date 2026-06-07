package com.codesnippet.dao;

import com.codesnippet.model.Lenguaje;
import com.codesnippet.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Lenguaje. Contiene CRUD sin usar GenericDAO.
 */
public class LenguajeDAO {

    public void insertar(Lenguaje lenguaje) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lenguaje);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Lenguaje lenguaje) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(lenguaje);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Lenguaje lenguaje = em.find(Lenguaje.class, id);
            if (lenguaje != null) {
                em.remove(lenguaje);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public Lenguaje buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Lenguaje.class, id);
        } finally {
            em.close();
        }
    }

    public List<Lenguaje> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Lenguaje l ORDER BY l.id", Lenguaje.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<Lenguaje>();
        } finally {
            em.close();
        }
    }
}
