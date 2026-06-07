package com.codesnippet.service;

/**
 * Servicio con validaciones simples usadas antes de guardar datos.
 * La cree para no repetir las mismas comprobaciones en todos los controladores.
 * Aqui centralizo la logica para asegurar que los datos que llegan a la base de datos sean correctos.
 */
public class ValidacionService {

    /**
     * Metodo auxiliar que verifica si un texto esta vacio o es nulo.
     * Uso trim() para que tambien considere vacio un texto que solo tenga espacios en blanco.
     */
    public boolean textoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /**
     * Metodo para validar que un campo no este vacio.
     * Si el texto esta vacio, lanzo una excepcion con un mensaje claro indicando que campo falta.
     * Esto hace que el error sea mas facil de entender para el usuario.
     */
    public void validarObligatorio(String texto, String nombreCampo) {
        if (textoVacio(texto)) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio");
        }
    }

    /**
     * Metodo especifico para validar el formato del email.
     * Primero me aseguro de que no este vacio (reutilizando el metodo anterior).
     * Luego hago una comprobacion basica: debe contener una "@" y un punto ".".
     * Si no cumple, lanzo una excepcion con el mensaje de error.
     */
    public void validarEmail(String email) {
        validarObligatorio(email, "email");
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El email no tiene un formato correcto");
        }
    }
}