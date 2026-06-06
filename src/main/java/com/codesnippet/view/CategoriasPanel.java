package com.codesnippet.view;

import com.codesnippet.controller.CategoriaController;
import com.codesnippet.model.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Panel Swing para gestionar categorias. */
public class CategoriasPanel extends JPanel {
    private CategoriaController controller = new CategoriaController();
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private Long idSeleccionado;

    public CategoriasPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);
        crearFormulario();
        crearTabla();
        cargarTabla();
    }

    private void crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos de categorias"));
        txtNombre = new JTextField(); AppTheme.campo(txtNombre);
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        txtDescripcion = new JTextField(); AppTheme.campo(txtDescripcion); panel.add(new JLabel("Descripcion:")); panel.add(txtDescripcion);
        JButton btnGuardar = new JButton("Guardar"); JButton btnLimpiar = new JButton("Limpiar");
        AppTheme.boton(btnGuardar); AppTheme.boton(btnLimpiar);
        panel.add(btnGuardar); panel.add(btnLimpiar);
        btnGuardar.addActionListener(e -> guardar()); btnLimpiar.addActionListener(e -> limpiar());
        add(panel, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripcion"}, 0) { public boolean isCellEditable(int f, int c) { return false; } };
        tabla = new JTable(modelo); AppTheme.tabla(tabla);
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
        JButton btnEliminar = new JButton("Eliminar"); AppTheme.botonBorrar(btnEliminar); btnEliminar.addActionListener(e -> eliminar());
        JPanel p = new JPanel(); p.setBackground(AppTheme.FONDO); p.add(btnEliminar);
        add(new JScrollPane(tabla), BorderLayout.CENTER); add(p, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            Categoria obj = new Categoria(txtNombre.getText(), txtDescripcion.getText()); obj.setId(idSeleccionado);
            controller.guardar(obj); cargarTabla(); limpiar(); JOptionPane.showMessageDialog(this, "Guardado correctamente");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void eliminar() {
        if (idSeleccionado == null) { JOptionPane.showMessageDialog(this, "Selecciona un registro"); return; }
        if (JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.eliminar(idSeleccionado); cargarTabla(); limpiar();
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Categoria> lista = controller.listar();
        for (Categoria o : lista) { modelo.addRow(new Object[]{o.getId(), o.getNombre(), o.getDescripcion()}); }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtDescripcion.setText(modelo.getValueAt(fila, 2) == null ? "" : modelo.getValueAt(fila, 2).toString());
        }
    }

    private void limpiar() { idSeleccionado = null; txtNombre.setText(""); txtDescripcion.setText(""); tabla.clearSelection(); }
}
