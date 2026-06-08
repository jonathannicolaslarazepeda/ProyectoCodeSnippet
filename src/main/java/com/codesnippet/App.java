package com.codesnippet;

import com.codesnippet.view.MainFrame;
import com.codesnippet.util.JPAUtil;
import javax.swing.*;

/**
 * Clase de arranque de la aplicacion.
 * Desde aqui es donde se ejecuta todo el programa cuando le doy a "Run".
 */
public class App {

    // El metodo main es el punto de entrada. Java empieza a ejecutar el codigo desde aqui.

    public static void main(String[] args) {

        // Uso SwingUtilities.invokeLater para que la ventana se cree en el hilo de eventos de Swing.
        // Si no lo hiciera asi, la interfaz grafica podria congelarse o dar problemas de concurrencia.

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Aqui creo la ventana principal (MainFrame) y la hago visible en la pantalla
                new MainFrame().setVisible(true);
            }
        });

        // Este ShutdownHook es un "gancho" que se ejecuta automaticamente cuando se cierra la aplicacion.
        // Lo uso para asegurarme de que la conexion a la base de datos se cierre correctamente,
        // asi no quedan procesos colgados ni se corrompen los datos.

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                // Llamo al metodo cerrar de JPAUtil para liberar el EntityManagerFactory
                JPAUtil.cerrar();
            }
        }));
    }

}