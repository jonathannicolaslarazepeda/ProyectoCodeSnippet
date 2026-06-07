# Uso de inteligencia artificial

Durante el desarrollo se ha utilizado IA como apoyo:

### Promt 1:

package com.codesnippet.dao;

import com.codesnippet.model.Usuario;
import com.codesnippet.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {

    public void insertar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
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
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) { em.getTransaction().rollback(); }
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public List<Usuario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u ORDER BY u.id", Usuario.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<Usuario>();
        } finally {
            em.close();
        }
    }
}
 Basado en esta clase, creame las clases SnippetDAO, LenguajeDAO, EtiquetaDAO, CategoriaDAO, en donde
esten todos los elementos del CRUD y listar todos.


### Promt 2:

package com.codesnippet.controller;

import com.codesnippet.dao.UsuarioDAO;
import com.codesnippet.model.Usuario;
import com.codesnippet.service.ValidacionService;
import java.util.List;

/**
* Controlador de usuarios. Valida datos antes de llamar al DAO.
  */
  public class UsuarioController {

  private UsuarioDAO dao = new UsuarioDAO();
  private ValidacionService validacion = new ValidacionService();

  public void guardar(Usuario usuario) {
  validacion.validarObligatorio(usuario.getNombre(), "nombre");
  validacion.validarEmail(usuario.getEmail());
  if (usuario.getId() == null) {
  dao.insertar(usuario);
  } else {
  dao.actualizar(usuario);
  }
  }

  public void eliminar(Long id) { dao.eliminar(id); }
  public List<Usuario> listar() { return dao.listarTodos(); }
  public Usuario buscarPorId(Long id) { return dao.buscarPorId(id); }
  }

Basado en esta clase, creame las clases SnippeController, LenguajeController, EtiquetaController, CategoriaController,
e3n donde tenga los mismos metodos adaptados.

### Promt 3:

Basado en el proyecto que te paso, creame la documentacion del proyecto en Markdown, En donde está el modelo
de negocio y especificaciones tecnicas, y un readme sencillo.

### Promt 4:

Revisa la estructura de mi programa en esta foto.

### Promt 5:

¿Porque necesito un Snipper service si en las demas clases el controller pasa directamente al dao?

### Promt 6:

Hazme una clase main basica, en donde pueda probar el crud de todas las tablas. 

### Promt 7:

Has una interfaz Swing para este programa.

### Promt 8:

¿Porque es necesario el AppTheme?
