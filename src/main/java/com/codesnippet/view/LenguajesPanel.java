package com.codesnippet.view;

import com.codesnippet.controller.LenguajeController;
import com.codesnippet.model.Lenguaje;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel Swing para gestionar lenguajes.
 * Aqui tengo la interfaz para crear, ver y eliminar lenguajes de programacion.
 * Es muy parecido al panel de usuarios pero mas simple porque solo tiene nombre y descripcion.
 */
public class LenguajesPanel extends JPanel {

    // Instancio el controlador para poder llamar a los metodos de negocio
    private LenguajeController controller = new LenguajeController();

    // Componentes de la interfaz grafica
    private JTable tabla;                      // Tabla donde se muestran los lenguajes
    private DefaultTableModel modelo;          // Modelo de datos de la tabla
    private JTextField txtNombre;              // Campo para el nombre del lenguaje
    private JTextField txtDescripcion;         // Campo para la descripcion del lenguaje
    private Long idSeleccionado;               // Guardo el ID del lenguaje seleccionado (para editar o eliminar)

    // Constructor del panel. Aqui inicializo todo cuando se crea el panel.
    public LenguajesPanel() {
        // Uso BorderLayout para organizar: formulario arriba, tabla en el centro, botones abajo
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);

        // Llamo a los metodos que crean cada parte de la interfaz
        crearFormulario();
        crearTabla();
        cargarTabla();  // Cargo los datos desde la base de datos para mostrarlos en la tabla
    }

    // Metodo para crear el formulario con los campos de texto y los botones
    private void crearFormulario() {
        // Creo un panel con GridLayout de 4 filas y 2 columnas para organizar los campos
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos de lenguajes"));  // Le pongo un titulo al panel

        // Creo los campos de texto y les aplico el estilo del tema
        txtNombre = new JTextField(); AppTheme.campo(txtNombre);
        // Agrego la etiqueta y el campo al panel
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);

        txtDescripcion = new JTextField(); AppTheme.campo(txtDescripcion);
        panel.add(new JLabel("Descripcion:")); panel.add(txtDescripcion);

        // Creo los botones y les aplico el estilo
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        AppTheme.boton(btnGuardar);
        AppTheme.boton(btnLimpiar);
        panel.add(btnGuardar); panel.add(btnLimpiar);

        // Agrego los listeners para que los botones hagan algo cuando les den clic
        btnGuardar.addActionListener(e -> guardar());   // Cuando guardo, llamo al metodo guardar()
        btnLimpiar.addActionListener(e -> limpiar());   // Cuando limpio, llamo al metodo limpiar()

        // Agrego el panel del formulario en la parte superior del panel principal
        add(panel, BorderLayout.NORTH);
    }

    // Metodo para crear la tabla donde se muestran los lenguajes
    private void crearTabla() {
        // Creo el modelo de la tabla con las columnas que quiero mostrar
        // El "0" es el numero inicial de filas
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripcion"}, 0) {
            // Sobreescribo para que las celdas NO sean editables desde la tabla
            public boolean isCellEditable(int f, int c) { return false; }
        };

        // Creo la tabla con el modelo y le aplico el estilo del tema
        tabla = new JTable(modelo);
        AppTheme.tabla(tabla);

        // Agrego un listener para detectar cuando el usuario selecciona una fila de la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());

        // Creo el boton de eliminar con su estilo especial (probablemente rojo)
        JButton btnEliminar = new JButton("Eliminar");
        AppTheme.botonBorrar(btnEliminar);
        btnEliminar.addActionListener(e -> eliminar());  // Cuando le den clic, llamo al metodo eliminar()

        // Creo un panel para el boton de eliminar
        JPanel p = new JPanel();
        p.setBackground(AppTheme.FONDO);
        p.add(btnEliminar);

        // Agrego la tabla en el centro (envuelta en un JScrollPane para que tenga scroll)
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        // Agrego el panel del boton en la parte inferior
        add(p, BorderLayout.SOUTH);
    }

    // Metodo para guardar un lenguaje nuevo o actualizar uno existente
    private void guardar() {
        try {
            // Creo un objeto Lenguaje con los datos que hay en los campos de texto
            Lenguaje obj = new Lenguaje(txtNombre.getText(), txtDescripcion.getText());
            // Si hay un idSeleccionado, significa que estoy editando, asi que le pongo el ID
            obj.setId(idSeleccionado);

            // Llamo al controlador para que guarde el lenguaje
            controller.guardar(obj);

            // Recargo la tabla para ver los cambios
            cargarTabla();

            // Limpio el formulario
            limpiar();

            // Muestro mensaje de exito
            JOptionPane.showMessageDialog(this, "Guardado correctamente");
        } catch (Exception ex) {
            // Si hay algun error, muestro el mensaje de error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo para eliminar el lenguaje seleccionado
    private void eliminar() {
        // Primero verifico que haya un lenguaje seleccionado
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro");
            return;
        }

        // Muestro dialogo de confirmacion
        if (JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // Si confirma, elimino el lenguaje
            controller.eliminar(idSeleccionado);

            // Recargo la tabla
            cargarTabla();

            // Limpio el formulario
            limpiar();
        }
    }

    // Metodo para cargar todos los lenguajes desde la base de datos y mostrarlos en la tabla
    private void cargarTabla() {
        // Borro todas las filas actuales
        modelo.setRowCount(0);

        // Pido al controlador que me traiga todos los lenguajes
        List<Lenguaje> lista = controller.listar();

        // Recorro la lista y voy agregando cada lenguaje como una fila nueva en la tabla
        for (Lenguaje o : lista) {
            modelo.addRow(new Object[]{o.getId(), o.getNombre(), o.getDescripcion()});
        }
    }

    // Metodo que se ejecuta cuando el usuario selecciona una fila de la tabla
    private void seleccionarFila() {
        // Obtengo el indice de la fila seleccionada
        int fila = tabla.getSelectedRow();

        // Si hay una fila seleccionada, cargo los datos en el formulario
        if (fila >= 0) {
            // Obtengo el ID de la primera columna
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            // Cargo el nombre en el campo de texto
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            // Cargo la descripcion (verifico que no sea null antes de ponerlo)
            txtDescripcion.setText(modelo.getValueAt(fila, 2) == null ? "" : modelo.getValueAt(fila, 2).toString());
        }
    }

    // Metodo para limpiar el formulario y dejarlo listo para crear un lenguaje nuevo
    private void limpiar() {
        idSeleccionado = null;  // Quito el ID para que el siguiente guardado sea un lenguaje nuevo
        txtNombre.setText("");  // Vacio el campo de nombre
        txtDescripcion.setText("");  // Vacio el campo de descripcion
        tabla.clearSelection();  // Deselecciono la fila que estaba seleccionada
    }
}