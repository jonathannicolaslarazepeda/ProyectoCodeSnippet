package com.codesnippet.dao;

import com.codesnippet.model.Snippet;
import com.codesnippet.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Snippet. Tiene CRUD y busquedas del proyecto.
 */
public class SnippetDAO {

    public void insertar(Snippet snippet) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(snippet);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Snippet snippet) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(snippet);
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
            Snippet snippet = em.find(Snippet.class, id);
            if (snippet != null) {
                snippet.getEtiquetas().clear();
                em.remove(snippet);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public Snippet buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Snippet.class, id);
        } finally {
            em.close();
        }
    }

    public List<Snippet> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Snippet s ORDER BY s.id", Snippet.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<Snippet>();
        } finally {
            em.close();
        }
    }

/* Metodos que devuelven lsitas de snippets por campos y ordenados alfabeticamente */

    public List<Snippet> buscarPorPalabra(String palabra) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Snippet s WHERE LOWER(s.titulo) LIKE :p OR LOWER(s.descripcion) LIKE :p OR LOWER(s.codigoFuente) LIKE :p ORDER BY s.fechaCreacion DESC", Snippet.class)
                    .setParameter("p", "%" + palabra.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<Snippet>();
        } finally {
            em.close();
        }
    }

    public List<Snippet> buscarPorLenguaje(Long idLenguaje) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Snippet s WHERE s.lenguaje.id = :id ORDER BY s.titulo", Snippet.class)
                    .setParameter("id", idLenguaje)
                    .getResultList();
        } finally { em.close(); }
    }

    public List<Snippet> buscarPorCategoria(Long idCategoria) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Snippet s WHERE s.categoria.id = :id ORDER BY s.titulo", Snippet.class)
                    .setParameter("id", idCategoria)
                    .getResultList();
        } finally { em.close(); }
    }

    public List<Snippet> buscarPorEtiqueta(Long idEtiqueta) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Snippet s JOIN s.etiquetas e WHERE e.id = :id ORDER BY s.titulo", Snippet.class)
                    .setParameter("id", idEtiqueta)
                    .getResultList();
        } finally { em.close(); }
    }
}
