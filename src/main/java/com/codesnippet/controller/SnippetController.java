package com.codesnippet.controller;

import com.codesnippet.dao.SnippetDAO;
import com.codesnippet.model.Lenguaje;
import com.codesnippet.model.Snippet;
import com.codesnippet.service.SnippetService;
import com.codesnippet.service.ValidacionService;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador de snippets. La vista llama aqui para no acceder directa al DAO.
 */


public class SnippetController {

    // Instancio el DAO para hacer las consultas a la base de datos
    private SnippetDAO snippetDAO = new SnippetDAO();
    // Instancio el servicio de validacion para revisar que los datos sean correctos antes de guardar
    private ValidacionService validacionService = new ValidacionService();

    /**
     * Metodo para guardar un snippet nuevo o actualizar uno existente.
     */
    public void guardarSnippet(Snippet snippet) {
        // Primero valido que todos los campos obligatorios esten completos
        validarSnippet(snippet);

        // Si la fecha de creacion es nula, significa que es un snippet nuevo, asi que le pongo la fecha de hoy
        if (snippet.getFechaCreacion() == null) {
            snippet.setFechaCreacion(LocalDate.now());
        }
        // Siempre actualizo la fecha de modificacion, ya sea que sea nuevo o que lo este editando
        snippet.setFechaModificacion(LocalDate.now());

        // Si el ID es nulo, es un registro nuevo, asi que lo inserto
        if (snippet.getId() == null) {
            snippetDAO.insertar(snippet);
        } else {
            // Si ya tiene un ID, significa que lo estoy editando, asi que lo actualizo
            snippetDAO.actualizar(snippet);
        }
    }

    /**
     * Metodo para eliminar un snippet de la base de datos usando su ID.
     */
    public void eliminarSnippet(Long id) {
        snippetDAO.eliminar(id);
    }

    /**
     * Metodo para obtener la lista completa de todos los snippets guardados.
     */
    public List<Snippet> listarSnippets() {
        return snippetDAO.listarTodos();
    }

    /**
     * Metodo para buscar un snippet especifico por su identificador unico.
     */
    public Snippet buscarPorId(Long id) {
        return snippetDAO.buscarPorId(id);
    }

    /**
     * Metodo para buscar snippets que contengan una palabra clave en su titulo o codigo.
     */
    public List<Snippet> buscar(String palabra) {
        // Primero reviso si la palabra esta vacia o es nula. Si es asi, devuelvo todos los snippets
        if (palabra == null || palabra.trim().isEmpty()) {
            return listarSnippets();
        }
        // Si hay una palabra, le pido al DAO que haga la busqueda especifica
        return snippetDAO.buscarPorPalabra(palabra);
    }

    /**
     * Metodo privado que uso para asegurarme de que el snippet tenga todos los datos obligatorios.
     * Lo llamo antes de cualquier operacion de guardado.
     */
    private void validarSnippet(Snippet snippet) {
        // Verifico que el objeto en si no sea nulo
        if (snippet == null) {
            throw new IllegalArgumentException("No hay datos del snippet");
        }

        // Uso el servicio de validacion para revisar los campos de texto obligatorios
        validacionService.validarObligatorio(snippet.getTitulo(), "titulo");
        validacionService.validarObligatorio(snippet.getCodigoFuente(), "codigo fuente");

        // Verifico que se hayan seleccionado las relaciones obligatorias (no pueden ser nulas)
        if (snippet.getUsuario() == null) { throw new IllegalArgumentException("Debe elegir un usuario"); }
        if (snippet.getLenguaje() == null) { throw new IllegalArgumentException("Debe elegir un lenguaje"); }
        if (snippet.getCategoria() == null) { throw new IllegalArgumentException("Debe elegir una categoria"); }
    }

}
