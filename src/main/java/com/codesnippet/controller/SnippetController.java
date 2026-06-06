package com.codesnippet.controller;

import com.codesnippet.model.Lenguaje;
import com.codesnippet.model.Snippet;
import com.codesnippet.service.SnippetService;
import java.util.List;

/**
 * Controlador de snippets. La vista llama aqui para no acceder directa al DAO.
 */
public class SnippetController {

    private SnippetService snippetService = new SnippetService();

    public void guardar(Snippet snippet) {
        snippetService.guardarSnippet(snippet);
    }

    public void eliminar(Long id) {
        snippetService.eliminarSnippet(id);
    }

    public List<Snippet> listar() {
        return snippetService.listarSnippets();
    }

    public List<Snippet> buscar(String palabra) {
        return snippetService.buscar(palabra);
    }

    public Snippet buscarPorId(Long id) { return snippetService.buscarPorId(id); }
}
