# CodeSnippet - Baul de Codigo

Proyecto final de Programacion de 1 DAW.

**Alumnos:** Jonathan Lara y Sebastian Martinez.

## Resumen

CodeSnippet es una aplicacion de escritorio hecha en Java Swing para guardar y organizar fragmentos de codigo.
Permite gestionar usuarios, lenguajes, categorias, etiquetas y snippets.

## Tecnologias

- Java
- Swing
- Maven
- Hibernate / JPA
- MySQL o MariaDB
- MVC y DAO

## Como ejecutar

1. Crear la base de datos con `database/schema.sql`.
2. Opcionalmente insertar datos con `database/data.sql`.
3. Revisar usuario y contrasena en `persistence.xml`.
4. Ejecutar `com.codesnippet.App` desde IntelliJ IDEA.

## Estructura

- `model`: entidades JPA.
- `dao`: CRUD de cada entidad, sin GenericDAO.
- `service`: validaciones y logica de snippets.
- `controller`: conexion entre Swing y el resto de capas.
- `view`: ventanas y paneles Swing.
- `util`: configuracion JPA.
