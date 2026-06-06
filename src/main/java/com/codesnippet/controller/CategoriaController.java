package com.codesnippet.controller;

import com.codesnippet.dao.CategoriaDAO;
import com.codesnippet.model.Categoria;
import com.codesnippet.service.ValidacionService;
import java.util.List;

/**
 * Controlador de Categoria. Une Swing con el DAO.
 */
public class CategoriaController {

    private CategoriaDAO dao = new CategoriaDAO();
    private ValidacionService validacion = new ValidacionService();

    public void guardar(Categoria categoria) {
        validacion.validarObligatorio(categoria.getNombre(), "nombre");
        if (categoria.getId() == null) {
            dao.insertar(categoria);
        } else {
            dao.actualizar(categoria);
        }
    }

    public void eliminar(Long id) {
        dao.eliminar(id);
    }

    public List<Categoria> listar() {
        return dao.listarTodos();
    }

    public Categoria buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}
