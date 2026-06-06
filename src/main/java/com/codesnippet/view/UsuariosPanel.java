package com.codesnippet.view;

import com.codesnippet.controller.UsuarioController;
import com.codesnippet.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel Swing para el CRUD de usuarios.
 */
public class UsuariosPanel extends JPanel {

    private UsuarioController controller = new UsuarioController();
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtGrupo;
    private Long idSeleccionado;

    public UsuariosPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);
        crearFormulario();
        crearTabla();
        cargarTabla();
    }

    private void crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del usuario"));

        txtNombre = new JTextField(); txtEmail = new JTextField(); txtGrupo = new JTextField();
        AppTheme.campo(txtNombre); AppTheme.campo(txtEmail); AppTheme.campo(txtGrupo);

        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Grupo/curso:")); panel.add(txtGrupo);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        AppTheme.boton(btnGuardar); AppTheme.boton(btnLimpiar);
        panel.add(btnGuardar); panel.add(btnLimpiar);

        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());
        add(panel, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Grupo"}, 0) {
            public boolean isCellEditable(int fila, int columna) { return false; }
        };
        tabla = new JTable(modelo);
        AppTheme.tabla(tabla);
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(AppTheme.FONDO);
        JButton btnEliminar = new JButton("Eliminar");
        AppTheme.botonBorrar(btnEliminar);
        btnEliminar.addActionListener(e -> eliminar());
        panelBotones.add(btnEliminar);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            Usuario usuario = new Usuario(txtNombre.getText(), txtEmail.getText(), txtGrupo.getText());
            usuario.setId(idSeleccionado);
            controller.guardar(usuario);
            cargarTabla();
            limpiar();
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controller.eliminar(idSeleccionado);
            cargarTabla();
            limpiar();
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Usuario> lista = controller.listar();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getGrupo()});
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtEmail.setText(modelo.getValueAt(fila, 2).toString());
            txtGrupo.setText(modelo.getValueAt(fila, 3) == null ? "" : modelo.getValueAt(fila, 3).toString());
        }
    }

    private void limpiar() {
        idSeleccionado = null;
        txtNombre.setText(""); txtEmail.setText(""); txtGrupo.setText("");
        tabla.clearSelection();
    }
}
