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
