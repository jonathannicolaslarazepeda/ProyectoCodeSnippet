package com.codesnippet.controller;

import com.codesnippet.dao.EtiquetaDAO;
import com.codesnippet.model.Etiqueta;
import com.codesnippet.service.ValidacionService;
import java.util.List;

/**
 * Controlador de Etiqueta. Une Swing con el DAO.
 */
public class EtiquetaController {

    private EtiquetaDAO dao = new EtiquetaDAO();
    private ValidacionService validacion = new ValidacionService();

    public void guardar(Etiqueta etiqueta) {
        validacion.validarObligatorio(etiqueta.getNombre(), "nombre");
        if (etiqueta.getId() == null) {
            dao.insertar(etiqueta);
        } else {
            dao.actualizar(etiqueta);
        }
    }

    public void eliminar(Long id) {
        dao.eliminar(id);
    }

    public List<Etiqueta> listar() {
        return dao.listarTodos();
    }

    public Etiqueta buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}
