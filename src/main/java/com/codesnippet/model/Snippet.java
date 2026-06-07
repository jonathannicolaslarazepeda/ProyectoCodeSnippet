package com.codesnippet.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad principal del proyecto. Guarda un fragmento de codigo.
 * Esta clase representa el snippet en sí y tiene todas las relaciones
 * con las demás tablas (usuario, lenguaje, categoría y etiquetas).
 */
@Entity
@Table(name = "snippets") // Le digo a JPA que esta clase se guarda en la tabla "snippets"
public class Snippet {

    // Clave primaria. La base de datos genera el ID automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_snippet")
    private Long id;

    // El título del snippet. Es obligatorio y tiene un límite de 120 caracteres.
    @Column(nullable = false, length = 120)
    private String titulo;

    // Una descripción opcional para explicar qué hace el código.
    @Column(length = 500)
    private String descripcion;

    // Aquí va el código fuente. Uso @Lob y columnDefinition = "TEXT"
    // porque el código puede ser muy largo y no cabe en un VARCHAR normal.
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String codigoFuente;

    // Fechas para saber cuándo se creó y cuándo se modificó por última vez.
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;

    // Relación Muchos a Uno: Muchos snippets pueden pertenecer a un mismo usuario.
    // nullable = false porque un snippet SIEMPRE debe tener un usuario asignado.
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario usuario;

    // Relación Muchos a Uno: Muchos snippets pueden ser del mismo lenguaje.
    @ManyToOne
    @JoinColumn(name = "id_language", nullable = false)
    private Lenguaje lenguaje;

    // Relación Muchos a Uno: Muchos snippets pueden estar en la misma categoría.
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Categoria categoria;

    // Relación Muchos a Muchos: Un snippet puede tener varias etiquetas,
    // y una etiqueta puede estar en varios snippets.
    // Uso @JoinTable para que JPA cree una tabla intermedia llamada "snippet_tags"
    // que guarde las claves foráneas de ambos lados.
    @ManyToMany
    @JoinTable(
            name = "snippet_tags",
            joinColumns = @JoinColumn(name = "id_snippet"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private List<Etiqueta> etiquetas = new ArrayList<Etiqueta>(); // La inicializo vacía para evitar errores de null.

    // Constructor vacío: JPA lo necesita obligatoriamente para poder crear los objetos.
    public Snippet() {
    }

    // Constructor que uso yo desde el código para crear un snippet nuevo.
    // Aquí inicializo las fechas con el día de hoy automáticamente.
    public Snippet(String titulo, String descripcion, String codigoFuente, Usuario usuario, Lenguaje lenguaje, Categoria categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.codigoFuente = codigoFuente;
        this.usuario = usuario;
        this.lenguaje = lenguaje;
        this.categoria = categoria;
        this.fechaCreacion = LocalDate.now();
        this.fechaModificacion = LocalDate.now();
    }

    // ===== GETTERS Y SETTERS =====
    // Los genero para poder acceder y modificar los atributos de forma controlada.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoFuente() {
        return codigoFuente;
    }

    public void setCodigoFuente(String codigoFuente) {
        this.codigoFuente = codigoFuente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    // Sobreescribo toString para que, si muestro el objeto en algún lado,
    // se vea el título del snippet en lugar de la dirección de memoria del objeto.
    @Override
    public String toString() {
        return titulo;
    }
}