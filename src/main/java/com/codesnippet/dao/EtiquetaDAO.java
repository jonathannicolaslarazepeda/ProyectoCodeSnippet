package com.codesnippet.dao;

import com.codesnippet.model.Etiqueta;
import com.codesnippet.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Etiqueta. Contiene CRUD sin usar GenericDAO.
 */
public class EtiquetaDAO {

    public void insertar(Etiqueta etiqueta) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(etiqueta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Etiqueta etiqueta) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(etiqueta);
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
            Etiqueta etiqueta = em.find(Etiqueta.class, id);
            if (etiqueta != null) {
                em.remove(etiqueta);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public Etiqueta buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Etiqueta.class, id);
        } finally {
            em.close();
        }
    }

    public List<Etiqueta> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Etiqueta e ORDER BY e.id", Etiqueta.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<Etiqueta>();
        } finally {
            em.close();
        }
    }
}
