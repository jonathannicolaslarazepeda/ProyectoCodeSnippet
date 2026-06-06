package com.codesnippet.view;

import com.codesnippet.controller.*;
import com.codesnippet.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

 /**
  * Panel principal para crear, buscar y consultar snippets
  **/

public class SnippetsPanel extends JPanel {

    private SnippetController snippetController = new SnippetController();
    private UsuarioController usuarioController = new UsuarioController();
    private LenguajeController lenguajeController = new LenguajeController();
    private CategoriaController categoriaController = new CategoriaController();
    private EtiquetaController etiquetaController = new EtiquetaController();

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtTitulo;
    private JTextField txtBuscar;
    private JTextArea txtDescripcion;
    private JTextArea txtCodigo;
    private JComboBox<Usuario> cmbUsuario;
    private JComboBox<Lenguaje> cmbLenguaje;
    private JComboBox<Categoria> cmbCategoria;
    private JList<Etiqueta> listaEtiquetas;
    private Long idSeleccionado;

    public SnippetsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);
        crearFormulario();
        crearTabla();
        cargarCombos();
        cargarTabla(snippetController.listar());
    }

    private void crearFormulario() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del snippet"));

        /* Caja Arriba*/

        JPanel arriba = new JPanel(new GridLayout(4, 2, 8, 8));
        arriba.setBackground(AppTheme.PANEL);
        txtTitulo = new JTextField(); AppTheme.campo(txtTitulo);
        cmbUsuario = new JComboBox<Usuario>(); AppTheme.combo(cmbUsuario);
        cmbLenguaje = new JComboBox<Lenguaje>(); AppTheme.combo(cmbLenguaje);
        cmbCategoria = new JComboBox<Categoria>(); AppTheme.combo(cmbCategoria);

        arriba.add(new JLabel("Titulo:")); arriba.add(txtTitulo);
        arriba.add(new JLabel("Usuario:")); arriba.add(cmbUsuario);
        arriba.add(new JLabel("Lenguaje:")); arriba.add(cmbLenguaje);
        arriba.add(new JLabel("Categoria:")); arriba.add(cmbCategoria);

        panel.add(arriba, BorderLayout.NORTH);

        /* Cajas de Descripcion y Texto*/

        txtDescripcion = new JTextArea(3, 30); AppTheme.area(txtDescripcion);
        txtCodigo = new JTextArea(12, 50); AppTheme.area(txtCodigo);
        listaEtiquetas = new JList<Etiqueta>();
        listaEtiquetas.setForeground(Color.BLACK);
        listaEtiquetas.setBackground(Color.WHITE);

        JPanel panelDescripcion = new JPanel(new BorderLayout());
        panelDescripcion.setBackground(AppTheme.PANEL);
        panelDescripcion.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

        JPanel panelCodigo = new JPanel(new BorderLayout());
        panelCodigo.setBackground(AppTheme.PANEL);
        panelCodigo.add(new JScrollPane(txtCodigo), BorderLayout.CENTER);

        panelDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción"));
        panelCodigo.setBorder(BorderFactory.createTitledBorder("Código"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelDescripcion, panelCodigo);
        split.setResizeWeight(0.35);

        panel.add(split, BorderLayout.CENTER);

        /*
        txtDescripcion = new JTextArea(3, 30); AppTheme.area(txtDescripcion);
        txtCodigo = new JTextArea(12, 50); AppTheme.area(txtCodigo);
        listaEtiquetas = new JList<Etiqueta>();
        listaEtiquetas.setForeground(Color.BLACK);
        listaEtiquetas.setBackground(Color.WHITE);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(txtDescripcion), new JScrollPane(txtCodigo));
        split.setResizeWeight(0.35);
        panel.add(split, BorderLayout.CENTER);
        */

        /* Cajas de Abajo*/

        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setBackground(AppTheme.PANEL);
        abajo.add(new JLabel("Etiquetas (CTRL para varias):"), BorderLayout.NORTH);
        abajo.add(new JScrollPane(listaEtiquetas), BorderLayout.CENTER);

        /* Botones */

        JPanel botones = new JPanel(); botones.setBackground(AppTheme.PANEL);
        JButton btnGuardar = new JButton("Guardar snippet");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnEliminar = new JButton("Eliminar");
        AppTheme.boton(btnGuardar); AppTheme.boton(btnLimpiar); AppTheme.botonBorrar(btnEliminar);
        botones.add(btnGuardar); botones.add(btnLimpiar); botones.add(btnEliminar);
        abajo.add(botones, BorderLayout.SOUTH);

        panel.add(abajo, BorderLayout.SOUTH);

        /* Listener */

        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnEliminar.addActionListener(e -> eliminar());

        add(panel, BorderLayout.NORTH);
    }

    private void crearTabla() {

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.setBackground(AppTheme.FONDO);

        JPanel buscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscador.setBackground(AppTheme.FONDO);

        txtBuscar = new JTextField(30); AppTheme.campo(txtBuscar);
        JButton btnBuscar = new JButton("Buscar"); JButton btnTodos = new JButton("Ver todos");
        AppTheme.boton(btnBuscar); AppTheme.boton(btnTodos);
        buscador.add(new JLabel("Buscar:")); buscador.add(txtBuscar); buscador.add(btnBuscar); buscador.add(btnTodos);

        centro.add(buscador, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Titulo", "Lenguaje", "Categoria", "Usuario", "Fecha"}, 0) {
            public boolean isCellEditable(int fila, int columna) { return false; }
        };

        tabla = new JTable(modelo);
        AppTheme.tabla(tabla);
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);

        /* Listener */

        btnBuscar.addActionListener(e -> cargarTabla(snippetController.buscar(txtBuscar.getText())));
        btnTodos.addActionListener(e -> cargarTabla(snippetController.listar()));

        add(centro, BorderLayout.CENTER);
    }

    private void cargarCombos() {

        cmbUsuario.removeAllItems();
        for (Usuario u : usuarioController.listar()) { cmbUsuario.addItem(u); }

        cmbLenguaje.removeAllItems();
        for (Lenguaje l : lenguajeController.listar()) { cmbLenguaje.addItem(l); }

        cmbCategoria.removeAllItems();
        for (Categoria c : categoriaController.listar()) { cmbCategoria.addItem(c); }

        DefaultListModel<Etiqueta> modeloLista = new DefaultListModel<Etiqueta>();
        for (Etiqueta e : etiquetaController.listar()) { modeloLista.addElement(e); }

        listaEtiquetas.setModel(modeloLista);

    }

    private void guardar() {

        try {
            Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
            Lenguaje lenguaje = (Lenguaje) cmbLenguaje.getSelectedItem();

            Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
            Snippet snippet = new Snippet(txtTitulo.getText(), txtDescripcion.getText(), txtCodigo.getText(), usuario, lenguaje, categoria);
            snippet.setId(idSeleccionado);

            snippet.setEtiquetas(new ArrayList<Etiqueta>(listaEtiquetas.getSelectedValuesList()));
            snippetController.guardar(snippet);
            cargarTabla(snippetController.listar());

            limpiar();

            JOptionPane.showMessageDialog(this, "Snippet guardado correctamente");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void eliminar() {

        if (idSeleccionado == null) { JOptionPane.showMessageDialog(this, "Selecciona un snippet"); return; }
        if (JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            snippetController.eliminar(idSeleccionado);
            cargarTabla(snippetController.listar());
            limpiar();
        }

    }

    private void cargarTabla(List<Snippet> snippets) {
        modelo.setRowCount(0);
        for (Snippet s : snippets) {
            modelo.addRow(new Object[]{s.getId(), s.getTitulo(), s.getLenguaje(), s.getCategoria(), s.getUsuario(), s.getFechaCreacion()});
        }
    }

    private void seleccionarFila() {

        int fila = tabla.getSelectedRow();
        if (fila < 0) {return;}

        idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
        Snippet snippet = snippetController.buscarPorId(idSeleccionado);

        if(snippet == null){return;}

        txtTitulo.setText(snippet.getTitulo());
        txtDescripcion.setText(snippet.getDescripcion());
        txtCodigo.setText(snippet.getCodigoFuente());
        cmbUsuario.setSelectedItem(snippet.getUsuario());
        cmbLenguaje.setSelectedItem(snippet.getLenguaje());
        cmbCategoria.setSelectedItem(snippet.getCategoria());

    }

    /*private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
            // Para no complicar el panel, el codigo se vuelve a completar manualmente si se quiere editar.
        }
    }*/

    private void limpiar() {

        idSeleccionado = null;

        txtTitulo.setText(""); txtDescripcion.setText(""); txtCodigo.setText(""); txtBuscar.setText("");
        listaEtiquetas.clearSelection(); tabla.clearSelection();
    }
}
