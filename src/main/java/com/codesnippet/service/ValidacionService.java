package com.codesnippet.service;

/**
 * Servicio con validaciones simples usadas antes de guardar datos.
 */
public class ValidacionService {

    public boolean textoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public void validarObligatorio(String texto, String nombreCampo) {
        if (textoVacio(texto)) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio");
        }
    }

    public void validarEmail(String email) {
        validarObligatorio(email, "email");
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El email no tiene un formato correcto");
        }
    }
}
