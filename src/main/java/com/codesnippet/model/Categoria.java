package com.codesnippet.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad para clasificar snippets en grupos generales.
 * Esta clase se mapea a la tabla "categories" en la base de datos.
 * Sirve para organizar los snippets por temas (ej: "Estructuras de control", "POO", "Bases de datos").
 */
@Entity
@Table(name = "categories") // Le especifico a JPA el nombre exacto de la tabla en la base de datos
public class Categoria {

    // Clave primaria. La base de datos se encarga de generar el ID automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;

    // El nombre de la categoría. Es obligatorio (nullable = false) y debe ser único (unique = true)
    // para evitar que se guarden categorías duplicadas por error. Le pongo un límite de 60 caracteres.
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    // Una descripción opcional de la categoría, con un límite de 250 caracteres.
    @Column(length = 250)
    private String descripcion;

    // Relación Uno a Muchos: Una categoría puede tener muchos snippets asociados.
    // El "mappedBy = categoria" le dice a JPA que la relación la controla el atributo "categoria" dentro de la clase Snippet.
    // La inicializo con un ArrayList vacío para evitar errores de NullPointerException al intentar agregar snippets.
    @OneToMany(mappedBy = "categoria")
    private List<Snippet> snippets = new ArrayList<Snippet>();

    // Constructor vacío. JPA lo necesita sí o sí para poder crear las instancias
    // de la clase cuando hace consultas o guarda datos en la base de datos.
    public Categoria() {
    }

    // Constructor que uso yo en el código para crear objetos Categoria nuevos
    // rápidamente pasándole el nombre y la descripción desde el formulario.
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ===== GETTERS Y SETTERS =====
    // Los genero para poder acceder y modificar los valores de los atributos de forma controlada.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Snippet> snippets) {
        this.snippets = snippets;
    }

    // Sobreescribo este método para que, cuando muestre la categoría en un JComboBox
    // o en algún mensaje, se vea solo el nombre (ej: "POO") en lugar de la dirección de memoria del objeto.
    @Override
    public String toString() {
        return nombre;
    }
}