package com.codesnippet.view;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de CodeSnippet.
 */
public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("CodeSnippet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        JLabel titulo = AppTheme.titulo("CodeSnippet");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setForeground(Color.BLACK);
        pestanas.addTab("Snippets", new SnippetsPanel());
        pestanas.addTab("Usuarios", new UsuariosPanel());
        pestanas.addTab("Lenguajes", new LenguajesPanel());
        pestanas.addTab("Categorias", new CategoriasPanel());
        pestanas.addTab("Etiquetas", new EtiquetasPanel());
        add(pestanas, BorderLayout.CENTER);

        JLabel pie = new JLabel("© Jonathan Lara y Sebastian Martinez");
        pie.setHorizontalAlignment(SwingConstants.CENTER);
        pie.setForeground(AppTheme.AZUL_OSCURO);
        add(pie, BorderLayout.SOUTH);

        AppTheme.prepararVentana(this);

    }
}
