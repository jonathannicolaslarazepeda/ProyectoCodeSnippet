package com.codesnippet.view;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Clase de estilos para no repetir colores en todas las ventanas.
 */
public class AppTheme {

    public static final Color FONDO = new Color(232, 245, 245);
    public static final Color PANEL = new Color(255, 255, 255);
    public static final Color AZUL_OSCURO = new Color(7, 40, 90);
    public static final Color TURQUESA = new Color(0, 140, 150);
    public static final Color BOTON = new Color(160, 220, 220);
    public static final Color BORRAR = new Color(240, 190, 170);

    public static void prepararVentana(JFrame frame) {
        frame.getContentPane().setBackground(FONDO);
        frame.setLocationRelativeTo(null);
    }

    public static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(AZUL_OSCURO);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        return label;
    }

    public static void boton(JButton boton) {
        boton.setBackground(BOTON);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
    }

    public static void botonBorrar(JButton boton) {
        boton.setBackground(BORRAR);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
    }

    public static void campo(JTextField campo) {
        campo.setForeground(Color.BLACK);
        campo.setBackground(Color.WHITE);
    }

    public static void area(JTextArea area) {
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
    }

    public static void combo(JComboBox<?> combo) {
        combo.setForeground(Color.BLACK);
        combo.setBackground(Color.WHITE);
    }

    public static void tabla(JTable tabla) {
        tabla.setForeground(Color.BLACK);
        tabla.setBackground(Color.WHITE);
        tabla.setGridColor(new Color(200, 220, 220));
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setSelectionBackground(new Color(190, 230, 230));
        JTableHeader header = tabla.getTableHeader();
        header.setForeground(Color.BLACK);
        header.setBackground(new Color(210, 240, 240));
        header.setFont(new Font("Arial", Font.BOLD, 13));
    }
}
