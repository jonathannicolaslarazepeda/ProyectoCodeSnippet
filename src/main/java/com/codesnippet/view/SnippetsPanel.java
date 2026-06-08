package com.codesnippet.view;

import com.codesnippet.controller.*;
import com.codesnippet.model.*;
import com.codesnippet.service.SnippetService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel principal para crear, buscar y consultar snippets.
 * Aqui es donde el usuario puede gestionar todos los fragmentos de codigo.
 * Tiene un formulario para meter los datos, una tabla para verlos y un buscador.
 **/

public class SnippetsPanel extends JPanel {

    // Instancio todos los controladores que necesito para manejar las diferentes entidades
    private SnippetController snippetController = new SnippetController();
    private UsuarioController usuarioController = new UsuarioController();
    private LenguajeController lenguajeController = new LenguajeController();
    private CategoriaController categoriaController = new CategoriaController();
    private EtiquetaController etiquetaController = new EtiquetaController();

    // Componentes de la interfaz grafica
    private JTable tabla;                      // Tabla donde se muestran los snippets
    private DefaultTableModel modelo;          // Modelo de datos de la tabla
    private JTextField txtTitulo;              // Campo para el titulo del snippet
    private JTextField txtBuscar;              // Campo para buscar snippets
    private JTextArea txtDescripcion;          // Area de texto para la descripcion
    private JTextArea txtCodigo;               // Area de texto para el codigo fuente
    private JComboBox<Usuario> cmbUsuario;     // Combo para seleccionar el usuario
    private JComboBox<Lenguaje> cmbLenguaje;   // Combo para seleccionar el lenguaje de programacion
    private JComboBox<Categoria> cmbCategoria; // Combo para seleccionar la categoria
    private JList<Etiqueta> listaEtiquetas;    // Lista para seleccionar las etiquetas (puede ser multiple)
    private Long idSeleccionado;               // Guardo el ID del snippet que tengo seleccionado (para editar o eliminar)

    // Constructor del panel. Aqui inicializo todo cuando se crea el panel.
    public SnippetsPanel() {
        // Uso BorderLayout para organizar: formulario arriba, tabla en el centro
        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.FONDO);

        // Llamo a los metodos que crean cada parte de la interfaz
        crearFormulario();
        crearTabla();
        actualizarDatos();  // Cargo los datos en los combos (usuarios, lenguajes, categorias, etiquetas)
        cargarTabla(snippetController.listarSnippets());  // Cargo todos los snippets en la tabla
    }

    // Metodo para crear todo el formulario con los campos, combos y botones
    private void crearFormulario() {
        // Creo un panel principal para el formulario
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(AppTheme.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del snippet"));

        /* Caja Arriba - Aqui van los campos basicos: titulo, usuario, lenguaje y categoria */

        // Creo un panel con GridLayout para organizar los campos en 4 filas y 2 columnas
        JPanel arriba = new JPanel(new GridLayout(4, 2, 8, 8));
        arriba.setBackground(AppTheme.PANEL);

        // Creo los campos y combos, y les aplico el estilo del tema
        txtTitulo = new JTextField(); AppTheme.campo(txtTitulo);
        cmbUsuario = new JComboBox<Usuario>(); AppTheme.combo(cmbUsuario);
        cmbLenguaje = new JComboBox<Lenguaje>(); AppTheme.combo(cmbLenguaje);
        cmbCategoria = new JComboBox<Categoria>(); AppTheme.combo(cmbCategoria);

        // Agrego las etiquetas y los campos al panel
        arriba.add(new JLabel("Titulo:")); arriba.add(txtTitulo);
        arriba.add(new JLabel("Usuario:")); arriba.add(cmbUsuario);
        arriba.add(new JLabel("Lenguaje:")); arriba.add(cmbLenguaje);
        arriba.add(new JLabel("Categoria:")); arriba.add(cmbCategoria);

        // Agrego esta seccion en la parte superior del panel del formulario
        panel.add(arriba, BorderLayout.NORTH);

        /* Cajas de Descripcion y Texto - Aqui van las areas de texto grandes */

        // Creo las areas de texto para la descripcion y el codigo fuente
        txtDescripcion = new JTextArea(3, 30); AppTheme.area(txtDescripcion);
        txtCodigo = new JTextArea(12, 50); AppTheme.area(txtCodigo);

        // Creo la lista de etiquetas con colores especificos (fondo blanco, texto negro)
        listaEtiquetas = new JList<Etiqueta>();
        listaEtiquetas.setForeground(Color.BLACK);
        listaEtiquetas.setBackground(Color.WHITE);

        // Creo un panel para la descripcion con su borde y titulo
        JPanel panelDescripcion = new JPanel(new BorderLayout());
        panelDescripcion.setBackground(AppTheme.PANEL);
        panelDescripcion.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

        // Creo un panel para el codigo con su borde y titulo
        JPanel panelCodigo = new JPanel(new BorderLayout());
        panelCodigo.setBackground(AppTheme.PANEL);
        panelCodigo.add(new JScrollPane(txtCodigo), BorderLayout.CENTER);

        panelDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción"));
        panelCodigo.setBorder(BorderFactory.createTitledBorder("Código"));

        // Uso un JSplitPane para poner la descripcion y el codigo uno al lado del otro
        // El usuario puede redimensionar arrastrando la barra del medio
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelDescripcion, panelCodigo);
        split.setResizeWeight(0.35);  // La descripcion ocupa el 35% del espacio inicial

        // Agrego el split en el centro del panel del formulario
        panel.add(split, BorderLayout.CENTER);

        /*
        Codigo alternativo que tenia antes (lo dejo comentado por si lo necesito recuperar)
        txtDescripcion = new JTextArea(3, 30); AppTheme.area(txtDescripcion);
        txtCodigo = new JTextArea(12, 50); AppTheme.area(txtCodigo);
        listaEtiquetas = new JList<Etiqueta>();
        listaEtiquetas.setForeground(Color.BLACK);
        listaEtiquetas.setBackground(Color.WHITE);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(txtDescripcion), new JScrollPane(txtCodigo));
        split.setResizeWeight(0.35);
        panel.add(split, BorderLayout.CENTER);
        */

        /* Cajas de Abajo - Aqui va la lista de etiquetas y los botones */

        // Creo un panel para la parte inferior del formulario
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setBackground(AppTheme.PANEL);
        abajo.add(new JLabel("Etiquetas (CTRL para varias):"), BorderLayout.NORTH);
        abajo.add(new JScrollPane(listaEtiquetas), BorderLayout.CENTER);

        /* Botones - Creo los botones de guardar, limpiar y eliminar */

        JPanel botones = new JPanel(); botones.setBackground(AppTheme.PANEL);
        JButton btnGuardar = new JButton("Guardar snippet");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnEliminar = new JButton("Eliminar");
        // Les aplico los estilos del tema (el de borrar tiene un estilo especial, probablemente rojo)
        AppTheme.boton(btnGuardar); AppTheme.boton(btnLimpiar); AppTheme.botonBorrar(btnEliminar);
        botones.add(btnGuardar); botones.add(btnLimpiar); botones.add(btnEliminar);
        abajo.add(botones, BorderLayout.SOUTH);

        // Agrego la parte inferior al panel del formulario
        panel.add(abajo, BorderLayout.SOUTH);

        /* Listener - Agrego los escuchadores para que los botones hagan algo cuando les den clic */

        btnGuardar.addActionListener(e -> guardar());   // Guardar el snippet
        btnLimpiar.addActionListener(e -> limpiar());   // Limpiar el formulario
        btnEliminar.addActionListener(e -> eliminar()); // Eliminar el snippet seleccionado

        // Agrego todo el panel del formulario en la parte superior del panel principal
        add(panel, BorderLayout.NORTH);
    }

    // Metodo para crear la tabla y el buscador
    private void crearTabla() {

        // Creo un panel para la parte central (tabla + buscador)
        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.setBackground(AppTheme.FONDO);

        // Creo el panel del buscador
        JPanel buscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscador.setBackground(AppTheme.FONDO);

        txtBuscar = new JTextField(30); AppTheme.campo(txtBuscar);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnTodos = new JButton("Ver todos");
        AppTheme.boton(btnBuscar);
        AppTheme.boton(btnTodos);

        // Agrego los componentes al panel del buscador
        buscador.add(new JLabel("Buscar:"));
        buscador.add(txtBuscar);
        buscador.add(btnBuscar);
        buscador.add(btnTodos);

        // Agrego el buscador en la parte superior
        centro.add(buscador, BorderLayout.NORTH);

        // Creo el modelo de la tabla con las columnas que quiero mostrar
        // El "0" es el numero inicial de filas
        modelo = new DefaultTableModel(new String[]{"ID", "Titulo", "Lenguaje", "Categoria", "Usuario", "Fecha"}, 0) {
            // Sobreescribo para que las celdas NO sean editables
            public boolean isCellEditable(int fila, int columna) { return false; }
        };

        // Creo la tabla y le aplico el estilo
        tabla = new JTable(modelo);
        AppTheme.tabla(tabla);

        // Agrego un listener para detectar cuando el usuario selecciona una fila
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());

        // Agrego la tabla en el centro (con scroll)
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);

        /* Listener - Agrego los escuchadores para los botones de buscar */

        // Cuando le dan clic a Buscar, filtro los snippets por el texto del campo
        btnBuscar.addActionListener(e -> cargarTabla(snippetController.buscar(txtBuscar.getText())));
        // Cuando le dan clic a Ver todos, cargo todos los snippets sin filtro
        btnTodos.addActionListener(e -> cargarTabla(snippetController.listarSnippets()));

        // Agrego todo el panel central al panel principal
        add(centro, BorderLayout.CENTER);
    }

    // Metodo para cargar los datos en los combos y en la lista de etiquetas
    private void cargarCombos() {

        // Limpio el combo de usuarios y lo lleno con todos los usuarios de la BD
        cmbUsuario.removeAllItems();
        for (Usuario u : usuarioController.listar()) { cmbUsuario.addItem(u); }

        // Limpio el combo de lenguajes y lo lleno con todos los lenguajes
        cmbLenguaje.removeAllItems();
        for (Lenguaje l : lenguajeController.listar()) { cmbLenguaje.addItem(l); }

        // Limpio el combo de categorias y lo lleno con todas las categorias
        cmbCategoria.removeAllItems();
        for (Categoria c : categoriaController.listar()) { cmbCategoria.addItem(c); }

        // Para la lista de etiquetas, uso un DefaultListModel porque JList no usa DefaultTableModel
        DefaultListModel<Etiqueta> modeloLista = new DefaultListModel<Etiqueta>();
        for (Etiqueta e : etiquetaController.listar()) { modeloLista.addElement(e); }

        // Asigno el modelo a la lista de etiquetas
        listaEtiquetas.setModel(modeloLista);

    }

    // Metodo para guardar un snippet nuevo o actualizar uno existente
    private void guardar() {

        try {
            // Obtengo los objetos seleccionados en los combos
            Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
            Lenguaje lenguaje = (Lenguaje) cmbLenguaje.getSelectedItem();
            Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();

            // Creo el objeto Snippet con los datos del formulario
            Snippet snippet = new Snippet(txtTitulo.getText(), txtDescripcion.getText(), txtCodigo.getText(), usuario, lenguaje, categoria);
            // Si hay un idSeleccionado, significa que estoy editando, asi que le pongo el ID
            snippet.setId(idSeleccionado);

            // Obtengo las etiquetas seleccionadas en la lista (puede ser varias si el usuario uso CTRL)
            // Las convierto a ArrayList porque el Snippet espera una lista
            snippet.setEtiquetas(new ArrayList<Etiqueta>(listaEtiquetas.getSelectedValuesList()));

            // Llamo al controlador para guardar el snippet
            snippetController.guardarSnippet(snippet);

            // Recargo la tabla para ver los cambios
            cargarTabla(snippetController.listarSnippets());

            // Limpio el formulario
            limpiar();

            // Muestro mensaje de exito
            JOptionPane.showMessageDialog(this, "Snippet guardado correctamente");

        } catch (Exception ex) {
            // Si hay algun error, muestro el mensaje
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // Metodo para eliminar el snippet seleccionado
    private void eliminar() {

        // Primero verifico que haya un snippet seleccionado
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un snippet");
            return;
        }

        // Muestro dialogo de confirmacion
        if (JOptionPane.showConfirmDialog(this, "Seguro que quieres eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // Si confirma, elimino el snippet
            snippetController.eliminarSnippet(idSeleccionado);
            // Recargo la tabla
            cargarTabla(snippetController.listarSnippets());
            // Limpio el formulario
            limpiar();
        }

    }

    // Metodo para cargar los snippets en la tabla
    private void cargarTabla(List<Snippet> snippets) {
        // Borro todas las filas actuales
        modelo.setRowCount(0);

        // Recorro la lista de snippets y voy agregando cada uno como una fila
        for (Snippet s : snippets) {
            modelo.addRow(new Object[]{s.getId(), s.getTitulo(), s.getLenguaje(), s.getCategoria(), s.getUsuario(), s.getFechaCreacion()});
        }
    }

    // Metodo que se ejecuta cuando el usuario selecciona una fila de la tabla
    private void seleccionarFila() {

        // Obtengo el indice de la fila seleccionada
        int fila = tabla.getSelectedRow();
        // Si no hay fila seleccionada, salgo del metodo
        if (fila < 0) {return;}

        // Obtengo el ID de la primera columna
        idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());

        // Busco el snippet completo por su ID (porque en la tabla solo muestro algunos campos)
        Snippet snippet = snippetController.buscarPorId(idSeleccionado);

        // Si no encuentro el snippet, salgo
        if(snippet == null){return;}

        // Cargo los datos del snippet en el formulario para que el usuario pueda editarlos
        txtTitulo.setText(snippet.getTitulo());
        txtDescripcion.setText(snippet.getDescripcion());
        txtCodigo.setText(snippet.getCodigoFuente());
        cmbUsuario.setSelectedItem(snippet.getUsuario());
        cmbLenguaje.setSelectedItem(snippet.getLenguaje());
        cmbCategoria.setSelectedItem(snippet.getCategoria());

    }

    /*
    Version anterior del metodo seleccionarFila que tenia antes
    Esta version solo cargaba el titulo y dejaba que el usuario reescribiera el codigo manualmente
    La nueva version carga todos los datos automaticamente

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Long.parseLong(modelo.getValueAt(fila, 0).toString());
            txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
            // Para no complicar el panel, el codigo se vuelve a completar manualmente si se quiere editar.
        }
    }
    */

    // Metodo para limpiar el formulario y dejarlo listo para crear un snippet nuevo
    private void limpiar() {

        idSeleccionado = null;  // Quito el ID para que el siguiente guardado sea un snippet nuevo

        // Vacio todos los campos de texto
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtCodigo.setText("");
        txtBuscar.setText("");

        // Deselecciono las etiquetas y la fila de la tabla
        listaEtiquetas.clearSelection();
        tabla.clearSelection();
    }

    // Metodo publico para refrescar combos y tabla
    public void actualizarDatos() {

        cargarCombos();
        cargarTabla(snippetController.listarSnippets());

    }
}