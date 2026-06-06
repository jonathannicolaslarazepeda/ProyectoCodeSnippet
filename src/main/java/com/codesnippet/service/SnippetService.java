package com.codesnippet.service;

import com.codesnippet.dao.SnippetDAO;
import com.codesnippet.model.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio con la logica principal de snippets.
 */
public class SnippetService {

    private SnippetDAO snippetDAO = new SnippetDAO();
    private ValidacionService validacionService = new ValidacionService();

    public void guardarSnippet(Snippet snippet) {
        validarSnippet(snippet);
        if (snippet.getFechaCreacion() == null) {
            snippet.setFechaCreacion(LocalDate.now());
        }
        snippet.setFechaModificacion(LocalDate.now());

        if (snippet.getId() == null) {
            snippetDAO.insertar(snippet);
        } else {
            snippetDAO.actualizar(snippet);
        }
    }

    public void eliminarSnippet(Long id) {
        snippetDAO.eliminar(id);
    }

    public List<Snippet> listarSnippets() {
        return snippetDAO.listarTodos();
    }

    public Snippet buscarPorId(Long id) {return snippetDAO.buscarPorId(id);}

    public List<Snippet> buscar(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return listarSnippets();
        }
        return snippetDAO.buscarPorPalabra(palabra);
    }

    private void validarSnippet(Snippet snippet) {
        if (snippet == null) {
            throw new IllegalArgumentException("No hay datos del snippet");
        }
        validacionService.validarObligatorio(snippet.getTitulo(), "titulo");
        validacionService.validarObligatorio(snippet.getCodigoFuente(), "codigo fuente");
        if (snippet.getUsuario() == null) { throw new IllegalArgumentException("Debe elegir un usuario"); }
        if (snippet.getLenguaje() == null) { throw new IllegalArgumentException("Debe elegir un lenguaje"); }
        if (snippet.getCategoria() == null) { throw new IllegalArgumentException("Debe elegir una categoria"); }
    }
}
