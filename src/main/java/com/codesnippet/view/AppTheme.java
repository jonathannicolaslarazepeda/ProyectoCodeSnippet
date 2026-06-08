package com.codesnippet.view;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Clase de estilos para no repetir colores en todas las ventanas.
 * La hice para centralizar todo el diseño en un solo lugar.
 * Así, si quiero cambiar un color o una fuente, solo lo modifico aquí y se aplica a toda la app.
 */

public class AppTheme {

    // Defino los colores que voy a usar en toda la aplicación como constantes

    public static final Color FONDO = new Color(232, 245, 245);       // Color de fondo general de las ventanas
    public static final Color PANEL = new Color(255, 255, 255);       // Color blanco para los paneles internos
    public static final Color AZUL_OSCURO = new Color(7, 40, 90);     // Color para los títulos principales
    public static final Color TURQUESA = new Color(0, 140, 150);      // Color de acento (por si lo necesito después)
    public static final Color BOTON = new Color(160, 220, 220);       // Color estándar para botones normales (Guardar, Limpiar)
    public static final Color BORRAR = new Color(240, 190, 170);      // Color diferente para el botón de Eliminar (para que destaque como acción peligrosa)

    /**
     * Metodo para configurar la ventana principal.
     * Le pongo el color de fondo y la centro en la pantalla.
     */

    public static void prepararVentana(JFrame frame) {
        frame.getContentPane().setBackground(FONDO);
        frame.setLocationRelativeTo(null); // Esto hace que la ventana aparezca en el centro de la pantalla
    }

    /**
     * Crea una etiqueta (JLabel) con el estilo que uso para los títulos grandes.
     */

    public static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(AZUL_OSCURO);
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Letra grande y en negrita
        return label;
    }

    /**
     * Aplica el estilo estándar a los botones normales.
     * Les quito el borde de foco (focusPainted) para que se vean más limpios al hacer clic.
     */

    public static void boton(JButton boton) {
        boton.setBackground(BOTON);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
    }

    /**
     * Aplica un estilo especial al botón de Eliminar.
     * Uso un color diferente (naranja/rojizo) para que el usuario sepa que es una acción destructiva.
     */

    public static void botonBorrar(JButton boton) {
        boton.setBackground(BORRAR);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
    }

    /**
     * Estilo básico para los campos de texto de una sola línea (JTextField).
     */

    public static void campo(JTextField campo) {
        campo.setForeground(Color.BLACK);
        campo.setBackground(Color.WHITE);
    }

    /**
     * Estilo para las áreas de texto grandes (JTextArea), como donde va el código.
     * Uso la fuente "Consolas" porque es monoespaciada y el código se lee mucho mejor.
     */

    public static void area(JTextArea area) {
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
    }

    /**
     * Estilo para los desplegables (JComboBox).
     */

    public static void combo(JComboBox<?> combo) {
        combo.setForeground(Color.BLACK);
        combo.setBackground(Color.WHITE);
    }

    /**
     * Aqui personalizo toda la tabla (JTable) para que se vea bien.
     * Cambio colores de fondo, texto, líneas de la cuadrícula y, muy importante, el encabezado.
     */

    public static void tabla(JTable tabla) {

        tabla.setForeground(Color.BLACK);
        tabla.setBackground(Color.WHITE);
        tabla.setGridColor(new Color(200, 220, 220)); // Color suave para las líneas de la cuadrícula
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setSelectionBackground(new Color(190, 230, 230)); // Color cuando selecciono una fila

        // Personalizo el encabezado de la tabla para que destaque

        JTableHeader header = tabla.getTableHeader();
        header.setForeground(Color.BLACK);
        header.setBackground(new Color(210, 240, 240));
        header.setFont(new Font("Arial", Font.BOLD, 13));

    }
}