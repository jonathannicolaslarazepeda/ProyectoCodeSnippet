package com.codesnippet.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase de utilidad para manejar la conexión a la base de datos.
 * La hice para no tener que repetir el código de conexión en cada DAO.
 * Centralizo aquí la creación del EntityManager para que sea más ordenado.
 */
public class JPAUtil {

    // Creo el EntityManagerFactory una sola vez (es static para que sea compartido en toda la app).
    // Aquí leo el archivo persistence.xml buscando la unidad de persistencia llamada "CodeSnippetPU".
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CodeSnippetPU");

    /**
     * Metodo que me devuelve un EntityManager nuevo cada vez que lo necesito.
     * Lo uso en los DAOs cuando voy a hacer una consulta o guardar algo en la base de datos.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Metodo para cerrar la conexión a la base de datos.
     * Lo llamo desde la clase App.java cuando se cierra el programa (ShutdownHook)
     * para asegurarme de liberar los recursos y no dejar procesos colgados en memoria.
     */
    public static void cerrar() {
        // Verifico que exista y que esté abierta antes de intentar cerrarla, por si acaso
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}