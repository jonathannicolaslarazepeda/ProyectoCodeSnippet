package com.codesnippet.view;

import com.codesnippet.controller.UsuarioController;
import com.codesnippet.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel Swing para el CRUD de usuarios.
 * Aqui tengo toda la interfaz grafica para crear, leer, actualizar y eliminar usuarios.
 * Es como la pantalla donde el usuario ve la tabla con los usuarios y puede meter datos nuevos.
 */
public class UsuariosPanel extends JPanel {

    // Creo una instancia del controlador para poder llamar a los metodos de negocio
    private UsuarioController controller = new UsuarioController();

    // Componentes de la interfaz grafica
    private JTable tabla;                    // La tabla donde se muestran los usuarios
    private DefaultTableModel modelo;        // El modelo de datos de la tabla (las columnas y filas)
    private JTextField txtNombre;            // Campo de texto para el nombre
    private JTextField txtEmail;             // Campo de texto para el email
    private JTextField txtGrupo;             // Campo de texto para el grupo/curso
    private Long idSeleccionado;             // Guardo el ID del usuario que tengo seleccionado en la tabla (para editar o eliminar)

    // Constructor del panel. Aqui inicializo todo cuando se crea el panel.
    public UsuariosPanel() {
        // Uso BorderLayout para organizar los componentes: formulario arriba, tabla en el centro, botones abajo
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);  // Le pongo el color de fondo del tema

        // Llamo a los metodos que crean cada parte de la interfaz
        crearFormulario();
        crearTabla();
        cargarTabla();  // Cargo los datos desde la base de datos para mostrarlos en la tabla
    }

    // Metodo para crear el formulario con los campos de texto y los botones de guardar/limpiar
    private void crearFormulario() {
        // Creo un panel con GridLayout de 4 filas y 2 columnas para organizar los campos
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del usuario"));  // Le pongo un titulo al panel

        // Creo los campos de texto y les aplico el estilo del tema
        txtNombre = new JTextField(); txtEmail = new JTextField(); txtGrupo = new JTextField();
        AppTheme.campo(txtNombre); AppTheme.campo(txtEmail); AppTheme.campo(txtGrupo);

        // Agrego las etiquetas (labels) y los campos de texto al panel
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Grupo/curso:")); panel.add(txtGrupo);

        // Creo los botones y les aplico el estilo
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        AppTheme.boton(btnGuardar); AppTheme.boton(btnLimpiar);
        panel.add(btnGuardar); panel.add(btnLimpiar);

        // Agrego los listeners (escuchadores) para que los botones hagan algo cuando les den clic
        btnGuardar.addActionListener(e -> guardar());   // Cuando guardo, llamo al metodo guardar()
        btnLimpiar.addActionListener(e -> limpiar());   // Cuando limpio, llamo al metodo limpiar()

        // Agrego el panel del formulario en la parte superior (NORTH) del panel principal
        add(panel, BorderLayout.NORTH);
    }

    // Metodo para crear la tabla donde se muestran los usuarios
    private void crearTabla() {
        // Creo el modelo de la tabla con las columnas que quiero mostrar
        // El "0" al final es el numero inicial de filas (empiezo con 0 y las voy agregando despues)
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Grupo"}, 0) {
            // Sobreescribo este metodo para que las celdas NO sean editables desde la tabla
            // Si no hago esto, el usuario podria editar los datos directamente en la tabla y no quiero eso
            public boolean isCellEditable(int fila, int columna) { return false; }
        };

        // Creo la tabla con el modelo y le aplico el estilo del tema
        tabla = new JTable(modelo);
        AppTheme.tabla(tabla);

        // Agrego un listener para detectar cuando el usuario selecciona una fila de la tabla
        // Esto es para que cuando seleccione un usuario, se carguen sus datos en el formulario (para editar)
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());

        // Creo un panel para los botones de accion (eliminar)
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(AppTheme.FONDO);
        JButton btnEliminar = new JButton("Eliminar");
        AppTheme.botonBorrar(btnEliminar);  // Le pongo un estilo especial (probablemente rojo) para indicar que es peligroso
        btnEliminar.addActionListener(e -> eliminar());  // Cuando le den clic, llamo al metodo eliminar()
        panelBotones.add(btnEliminar);

        // Agrego la tabla en el centro (envuelta en un JScrollPane para que tenga scroll si hay muchos usuarios)
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        // Agrego el panel de botones en la parte inferior
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Metodo para guardar un usuario nuevo o actualizar uno existente
    private void guardar() {
        try {
            // Creo un objeto Usuario con los datos que hay en los campos de texto
            Usuario usuario = new Usuario(txtNombre.getText(), txtEmail.getText(), txtGrupo.getText());
            // Si hay un idSeleccionado, significa que estoy editando un usuario existente, asi que le pongo el ID
            usuario.setId(idSeleccionado);

            // Llamo al controlador para que guarde el usuario (el decide si es crear o actualizar)
            controller.guardar(usuario);

            // Recargo la tabla para que se vea el usuario nuevo o los cambios
            cargarTabla();
            // Limpio el formulario para que quede listo para el siguiente usuario
            limpiar();

            // Muestro un mensaje de exito
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente");
        } catch (Exception ex) {
            // Si hay algun error (por ejemplo, email duplicado), muestro el mensaje de error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo para eliminar el usuario seleccionado
    private void eliminar() {
        // Primero verifico que haya un usuario seleccionado
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
            return;  // Salgo del metodo, no hago nada mas
        }

        // Muestro un dialogo de confirmacion para que el usuario este seguro de que quiere eliminar
        int opcion = JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);

        // Si el usuario confirma (presiona YES), entonces elimino
        if (opcion == JOptionPane.YES_OPTION) {
            controller.eliminar(idSeleccionado);  // Llamo al controlador para eliminar
            cargarTabla();  // Recargo la tabla para que desaparezca el usuario eliminado
            limpiar();      // Limpio el formulario
        }
    }

    // Metodo para cargar todos los usuarios desde la base de datos y mostrarlos en la tabla
    private void cargarTabla() {
        // Primero borro todas las filas que hay en la tabla (para no duplicar datos)
        modelo.setRowCount(0);

        // Pido al controlador que me traiga todos los usuarios
        List<Usuario> lista = controller.listar();

        // Recorro la lista y voy agregando cada usuario como una fila nueva en la tabla
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getGrupo()});
        }
    }

    // Metodo que se ejecuta cuando el usuario selecciona una fila de la tabla
    private void seleccionarFila() {
        // Obtengo el indice de la fila seleccionada
        int fila = tabla.getSelectedRow();

        // Si hay una fila seleccionada (fila >= 0), cargo los datos en el formulario
        if (fila >= 0) {
            // Obtengo el ID de la primera columna y lo guardo en idSeleccionado
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            // Cargo los demas datos en los campos de texto
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtEmail.setText(modelo.getValueAt(fila, 2).toString());
            // Para el grupo, verifico que no sea null antes de ponerlo (por si el usuario no tiene grupo)
            txtGrupo.setText(modelo.getValueAt(fila, 3) == null ? "" : modelo.getValueAt(fila, 3).toString());
        }
    }

    // Metodo para limpiar el formulario y dejarlo listo para crear un usuario nuevo
    private void limpiar() {
        idSeleccionado = null;  // Quito el ID seleccionado para que el siguiente guardado sea un usuario nuevo
        txtNombre.setText(""); txtEmail.setText(""); txtGrupo.setText("");  // Vacio los campos de texto
        tabla.clearSelection();  // Deselecciono la fila que estaba seleccionada en la tabla
    }
}