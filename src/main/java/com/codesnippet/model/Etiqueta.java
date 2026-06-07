package com.codesnippet.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una etiqueta o tag reutilizable.
 * Esta clase se mapea a la tabla "tags" en la base de datos.
 * Sirve para clasificar los snippets con palabras clave (ej: "bucles", "arrays", "POO").
 */
@Entity
@Table(name = "tags") // Le especifico a JPA el nombre exacto de la tabla en la base de datos
public class Etiqueta {

    // Clave primaria. La base de datos se encarga de generar el ID automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Long id;

    // El nombre de la etiqueta. Es obligatorio (nullable = false) y debe ser único (unique = true)
    // para evitar que se guarden etiquetas duplicadas por error. Le pongo un límite de 50 caracteres.
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    // Relación Muchos a Muchos: Una etiqueta puede estar asociada a muchos snippets,
    // y un snippet puede tener muchas etiquetas.
    // Uso mappedBy = "etiquetas" porque la tabla intermedia que une ambas entidades
    // la define y controla la clase Snippet.
    // La inicializo con un ArrayList vacío para evitar errores de NullPointerException.
    @ManyToMany(mappedBy = "etiquetas")
    private List<Snippet> snippets = new ArrayList<Snippet>();

    // Constructor vacío. JPA lo necesita sí o sí para poder crear las instancias
    // de la clase cuando hace consultas o guarda datos en la base de datos.
    public Etiqueta() { }

    // Constructor que uso yo en el código para crear objetos Etiqueta nuevos
    // rápidamente pasándole solo el nombre desde el formulario.
    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }

    // ===== GETTERS Y SETTERS =====
    // Los genero para poder acceder y modificar los valores de los atributos de forma controlada.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Snippet> getSnippets() { return snippets; }
    public void setSnippets(List<Snippet> snippets) { this.snippets = snippets; }

    // Sobreescribo este método para que, cuando muestre la etiqueta en un JList o JComboBox,
    // se vea solo el nombre (ej: "Java") en lugar de la dirección de memoria rara del objeto.
    @Override
    public String toString() {
        return nombre;
    }
}