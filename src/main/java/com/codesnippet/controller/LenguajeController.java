package com.codesnippet.controller;

import com.codesnippet.dao.LenguajeDAO;
import com.codesnippet.model.Lenguaje;
import com.codesnippet.service.ValidacionService;
import java.util.List;

/**
 * Controlador de Lenguaje. Une Swing con el DAO.
 */
public class LenguajeController {

    private LenguajeDAO dao = new LenguajeDAO();
    private ValidacionService validacion = new ValidacionService();

    public void guardar(Lenguaje lenguaje) {
        validacion.validarObligatorio(lenguaje.getNombre(), "nombre");
        if (lenguaje.getId() == null) {
            dao.insertar(lenguaje);
        } else {
            dao.actualizar(lenguaje);
        }
    }

    public void eliminar(Long id) {
        dao.eliminar(id);
    }

    public List<Lenguaje> listar() {
        return dao.listarTodos();
    }

    public Lenguaje buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}
