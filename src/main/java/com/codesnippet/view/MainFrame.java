package com.codesnippet.view;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de CodeSnippet.
 * Esta es la ventana que aparece cuando ejecuto la aplicacion.
 * Aqui organizo todas las pestanas con los diferentes paneles (usuarios, snippets, etc).
 */
public class MainFrame extends JFrame {

    // Constructor de la ventana principal. Aqui configuro todo cuando se crea la ventana.
    public MainFrame() {

        // Configuro las propiedades basicas de la ventana
        setTitle("CodeSnippet");  // Titulo que aparece en la barra superior de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cuando cierro la ventana, se cierra toda la aplicacion
        setSize(1200, 800);  // Tamaño inicial de la ventana (ancho x alto en pixeles)
        setLayout(new BorderLayout());  // Uso BorderLayout para organizar los componentes: norte, sur, centro, este, oeste

        // Creo el titulo principal de la aplicacion usando el metodo del tema
        JLabel titulo = AppTheme.titulo("CodeSnippet");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);  // Centro el texto horizontalmente
        add(titulo, BorderLayout.NORTH);  // Lo agrego en la parte superior de la ventana

        // Creo un JTabbedPane para tener pestanas (como las de un navegador web)
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setForeground(Color.BLACK);  // Color del texto de las pestanas

        SnippetsPanel snippetsPanel = new SnippetsPanel();

        // Agrego cada pestana con su panel correspondiente
        // Cada pestana tiene un nombre y un panel que es el contenido que se muestra
        pestanas.addTab("Snippets", snippetsPanel);       // Panel para gestionar snippets
        pestanas.addTab("Usuarios", new UsuariosPanel());       // Panel para gestionar usuarios
        pestanas.addTab("Lenguajes", new LenguajesPanel());     // Panel para gestionar lenguajes de programacion
        pestanas.addTab("Categorias", new CategoriasPanel());   // Panel para gestionar categorias
        pestanas.addTab("Etiquetas", new EtiquetasPanel());     // Panel para gestionar etiquetas

        // Listener para detectar cambio de pestaña
        pestanas.addChangeListener(e -> {

            int indice = pestanas.getSelectedIndex();
            String nombre = pestanas.getTitleAt(indice);

            // Si entro a Snippets, recargo los combos
            if (nombre.equals("Snippets")) {
                snippetsPanel.actualizarDatos();
            }

        });

        // Agrego las pestanas en el centro de la ventana (ocupa la mayor parte del espacio)
        add(pestanas, BorderLayout.CENTER);

        // Creo el pie de pagina con los nombres de los autores
        JLabel pie = new JLabel("© Jonathan Lara y Sebastian Martinez");
        pie.setHorizontalAlignment(SwingConstants.CENTER);  // Centro el texto
        pie.setForeground(AppTheme.AZUL_OSCURO);  // Le pongo el color azul oscuro del tema
        add(pie, BorderLayout.SOUTH);  // Lo agrego en la parte inferior de la ventana

        // Llamo al metodo del tema para aplicar estilos adicionales a la ventana
        AppTheme.prepararVentana(this);

    }
}