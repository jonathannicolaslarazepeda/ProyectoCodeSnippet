package com.codesnippet.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que almacena los lenguajes de programación.
 * Esta clase se mapea a la tabla "languages" en la base de datos.
 * Sirve para clasificar los snippets (ej: Java, Python, JavaScript).
 */
@Entity
@Table(name = "languages") // Le especifico a JPA el nombre exacto de la tabla en la BD
public class Lenguaje {

    // Clave primaria. La base de datos se encarga de generar el ID automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_language")
    private Long id;

    // El nombre del lenguaje. Es obligatorio (nullable = false) y debe ser único (unique = true)
    // para evitar que se guarden duplicados como "Java" y "java" por error.
    @Column(nullable = false, unique = true, length = 40)
    private String nombre;

    // Una descripción opcional del lenguaje, con un límite de 200 caracteres.
    @Column(length = 200)
    private String descripcion;

    // Relación Uno a Muchos: Un lenguaje puede tener muchos snippets asociados.
    // El "mappedBy = lenguaje" le dice a JPA que la relación la controla el atributo "lenguaje" dentro de la clase Snippet.
    // La inicializo con un ArrayList vacío para evitar errores de NullPointerException al intentar agregar snippets.
    @OneToMany(mappedBy = "lenguaje")
    private List<Snippet> snippets = new ArrayList<Snippet>();

    // Constructor vacío. JPA lo necesita sí o sí para poder crear las instancias
    // de la clase cuando hace consultas o guarda datos en la base de datos.
    public Lenguaje() { }

    // Constructor que uso yo en el código para crear objetos Lenguaje nuevos
    // rápidamente pasándole el nombre y la descripción desde el formulario.
    public Lenguaje(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ===== GETTERS Y SETTERS =====
    // Los genero para poder acceder y modificar los valores de los atributos de forma controlada.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Snippet> getSnippets() { return snippets; }
    public void setSnippets(List<Snippet> snippets) { this.snippets = snippets; }

    // Sobreescribo este método para que, cuando muestre el lenguaje en un JComboBox
    // o en algún mensaje, se vea solo el nombre (ej: "Java") en lugar de la dirección de memoria del objeto.
    @Override
    public String toString() {
        return nombre;
    }
}