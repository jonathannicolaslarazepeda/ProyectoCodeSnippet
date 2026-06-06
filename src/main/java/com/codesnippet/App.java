package com.codesnippet;

import com.codesnippet.view.MainFrame;
import com.codesnippet.util.JPAUtil;
import javax.swing.*;

/**
 * Clase de arranque de la aplicacion.
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }

        });

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                JPAUtil.cerrar();
            }
        }));
    }

}
