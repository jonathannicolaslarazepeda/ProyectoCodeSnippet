package com.codesnippet.view;

import com.codesnippet.controller.CategoriaController;
import com.codesnippet.model.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel Swing para gestionar categorias.
 * Aqui tengo la interfaz para crear, ver y eliminar categorias.
 * Es muy similar al panel de lenguajes, ya que ambas tienen nombre y descripcion.
 */
public class CategoriasPanel extends JPanel {

    // Instancio el controlador para manejar la logica de negocio de las categorias
    private CategoriaController controller = new CategoriaController();

    // Componentes de la interfaz grafica
    private JTable tabla;                      // Tabla donde se muestran las categorias
    private DefaultTableModel modelo;          // Modelo de datos de la tabla
    private JTextField txtNombre;              // Campo de texto para el nombre de la categoria
    private JTextField txtDescripcion;         // Campo de texto para la descripcion de la categoria
    private Long idSeleccionado;               // Guardo el ID de la categoria seleccionada (para editar o eliminar)

    // Constructor del panel. Aqui inicializo todo cuando se crea la pestaña.
    public CategoriasPanel() {
        // Uso BorderLayout para organizar los componentes
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);

        // Llamo a los metodos que construyen la interfaz y cargan los datos
        crearFormulario();
        crearTabla();
        cargarTabla();
    }

    // Metodo para crear el formulario de arriba con los campos y los botones
    private void crearFormulario() {
        // Creo un panel con GridLayout de 4 filas y 2 columnas para organizar los campos
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos de categorias"));

        // Creo los campos de texto y les aplico el estilo del tema
        txtNombre = new JTextField(); AppTheme.campo(txtNombre);
        // Agrego la etiqueta y el campo al panel
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);

        txtDescripcion = new JTextField(); AppTheme.campo(txtDescripcion);
        panel.add(new JLabel("Descripcion:")); panel.add(txtDescripcion);

        // Creo los botones de accion y les aplico el estilo
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        AppTheme.boton(btnGuardar);
        AppTheme.boton(btnLimpiar);

        panel.add(btnGuardar); panel.add(btnLimpiar);

        // Agrego los listeners para que los botones ejecuten sus metodos al hacer clic
        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());

        // Agrego todo el formulario en la parte superior (NORTH) del panel principal
        add(panel, BorderLayout.NORTH);
    }

    // Metodo para crear la tabla y el boton de eliminar
    private void crearTabla() {
        // Creo el modelo de la tabla con las columnas "ID", "Nombre" y "Descripcion"
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripcion"}, 0) {
            // Sobreescribo este metodo para que el usuario no pueda editar las celdas directamente
            public boolean isCellEditable(int f, int c) { return false; }
        };

        // Creo la tabla y le aplico el estilo visual
        tabla = new JTable(modelo);
        AppTheme.tabla(tabla);

        // Agrego un listener para detectar cuando selecciono una fila y poder editarla
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());

        // Creo el boton de eliminar con su estilo de alerta (probablemente rojo)
        JButton btnEliminar = new JButton("Eliminar");
        AppTheme.botonBorrar(btnEliminar);
        btnEliminar.addActionListener(e -> eliminar());

        // Pongo el boton de eliminar en un panel separado para alinearlo abajo
        JPanel p = new JPanel();
        p.setBackground(AppTheme.FONDO);
        p.add(btnEliminar);

        // Agrego la tabla en el centro (con scroll por si hay muchas categorias)
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        // Agrego el panel del boton eliminar en la parte inferior (SOUTH)
        add(p, BorderLayout.SOUTH);
    }

    // Metodo para guardar una categoria nueva o actualizar una existente
    private void guardar() {
        try {
            // Creo el objeto Categoria con los textos de los campos
            Categoria obj = new Categoria(txtNombre.getText(), txtDescripcion.getText());

            // Si hay un ID seleccionado, se lo asigno para que sepa que es una actualizacion
            obj.setId(idSeleccionado);

            // Mando a guardar al controlador
            controller.guardar(obj);

            // Recargo la tabla para que aparezca el cambio
            cargarTabla();

            // Limpio el formulario
            limpiar();

            // Muestro mensaje de exito
            JOptionPane.showMessageDialog(this, "Guardado correctamente");
        } catch (Exception ex) {
            // Si hay un error (ej: nombre duplicado), muestro el mensaje que viene de la excepcion
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo para eliminar la categoria seleccionada
    private void eliminar() {
        // Primero verifico que el usuario haya seleccionado algo
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro");
            return;
        }

        // Muestro un dialogo de confirmacion para evitar borrados accidentales
        if (JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // Si dice que si, mando a eliminar al controlador
            controller.eliminar(idSeleccionado);

            // Recargo la tabla y limpio el formulario
            cargarTabla();
            limpiar();
        }
    }

    // Metodo para cargar todas las categorias de la base de datos en la tabla
    private void cargarTabla() {
        // Borro todas las filas que haya actualmente para no duplicar datos
        modelo.setRowCount(0);

        // Pido la lista completa de categorias al controlador
        List<Categoria> lista = controller.listar();

        // Recorro la lista y agrego cada categoria como una fila nueva en la tabla
        for (Categoria o : lista) {
            modelo.addRow(new Object[]{o.getId(), o.getNombre(), o.getDescripcion()});
        }
    }

    // Metodo que se ejecuta cuando hago clic en una fila de la tabla
    private void seleccionarFila() {
        // Obtengo el indice de la fila seleccionada
        int fila = tabla.getSelectedRow();

        // Si hay una fila valida seleccionada, cargo sus datos en el formulario
        if (fila >= 0) {
            // Guardo el ID para saber cual estoy editando
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            // Pongo el nombre en el campo de texto
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            // Pongo la descripcion (verifico que no sea null antes de ponerlo)
            txtDescripcion.setText(modelo.getValueAt(fila, 2) == null ? "" : modelo.getValueAt(fila, 2).toString());
        }
    }

    // Metodo para dejar el formulario limpio y listo para crear una categoria nueva
    private void limpiar() {
        idSeleccionado = null;       // Reseteo el ID para que el proximo guardado sea uno nuevo
        txtNombre.setText("");       // Vacio el campo de texto
        txtDescripcion.setText("");  // Vacio el campo de descripcion
        tabla.clearSelection();      // Quito la seleccion de la fila en la tabla
    }
}