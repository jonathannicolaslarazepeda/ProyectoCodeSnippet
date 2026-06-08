package com.codesnippet.service;

import com.codesnippet.dao.SnippetDAO;
import com.codesnippet.model.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio con la logica principal de snippets.
 * Aqui manejo todo lo relacionado con los snippets antes de que lleguen a la base de datos,
 * como las validaciones de datos y la gestion automatica de las fechas.
 * No se utiliza
 */
public class SnippetService {

/*
    private SnippetDAO dao = new SnippetDAO();
    private ValidacionService validacion = new ValidacionService();

    public void guardar(Snippet snippet) {
        validacion.validarObligatorio(snippet.getTitulo(), "nombre");
        if (snippet.getId() == null) {
            dao.insertar(snippet);
        } else {
            dao.actualizar(snippet);
        }
    }

    public void eliminar(Long id) {
        dao.eliminar(id);
    }

    public List<Snippet> listar() {
        return dao.listarTodos();
    }

    public Snippet buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
    */
}