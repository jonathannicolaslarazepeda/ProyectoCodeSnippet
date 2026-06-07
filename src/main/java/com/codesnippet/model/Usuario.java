package com.codesnippet.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa a un usuario o alumno en mi sistema.
 * Las anotaciones de JPA le dicen a Hibernate cómo mapear esta clase
 * a la tabla "users" en la base de datos.
 */
@Entity
@Table(name = "users") // Le especifico el nombre exacto de la tabla en la BD
public class Usuario {

    // Este es el identificador único. Con @Id le digo que es la clave primaria,
    // y con GenerationType.IDENTITY la base de datos se encarga de generar el número automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    // El nombre del usuario. Lo pongo como obligatorio (nullable = false)
    // y le limito el tamaño a 80 caracteres para evitar datos incorrectos.
    @Column(nullable = false, length = 80)
    private String nombre;

    // El correo electrónico. También es obligatorio y le pongo unique = true
    // para que no pueda haber dos usuarios registrados con el mismo email.
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // El grupo o curso al que pertenece (ej: "1º DAW").
    // Este campo es opcional, por eso no le pongo la restricción de nullable.
    @Column(length = 30)
    private String grupo;

    // Aquí guardo la lista de snippets que ha creado este usuario.
    // Uso @OneToMany porque un usuario puede tener muchos snippets.
    // El mappedBy = "usuario" le indica a JPA que la relación la controla el atributo "usuario" dentro de la clase Snippet.
    // La inicializo con un ArrayList vacío para evitar errores de NullPointerException.
    @OneToMany(mappedBy = "usuario")
    private List<Snippet> snippets = new ArrayList<Snippet>();

    // Constructor vacío. JPA lo necesita sí o sí para poder crear las instancias
    // de la clase al traer o guardar datos en la base de datos.
    public Usuario() { }

    // Constructor que yo uso en el código para crear objetos Usuario nuevos
    // rápidamente pasándole los datos desde el formulario.
    public Usuario(String nombre, String email, String grupo) {
        this.nombre = nombre;
        this.email = email;
        this.grupo = grupo;
    }

    // Getters y Setters: los genero para poder acceder y modificar
    // los valores de los atributos de forma controlada.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public List<Snippet> getSnippets() { return snippets; }
    public void setSnippets(List<Snippet> snippets) { this.snippets = snippets; }

    // Sobreescribo este método para que, cuando muestre el usuario en un JComboBox
    // o en un mensaje de texto, se vea algo legible como "Juan (1º DAW)"
    // en lugar de la dirección de memoria rara del objeto.
    @Override
    public String toString() {
        return nombre + " (" + grupo + ")";
    }
}