# Especificaciones del modelo de negocio

## Objetivo

La aplicacion permite al alumnado crear un repositorio de fragmentos de codigo clasificados.

## Entidades

### Usuario
Representa a Jonathan, Sebastian u otros alumnos que guardan snippets.

### Lenguaje
Permite indicar si el snippet es Java, SQL, HTML, CSS, JavaScript, etc.

### Categoria
Sirve para clasificar el fragmento de forma general, por ejemplo POO, Bases de Datos o Interfaz Grafica.

### Etiqueta
Permite una clasificacion mas flexible. Un snippet puede tener varias etiquetas.

### Snippet
Es la entidad principal. Guarda titulo, descripcion, codigo fuente, fechas, usuario, lenguaje, categoria y etiquetas.

## Regla principal

Un snippet pertenece a un usuario, a un lenguaje y a una categoria. Ademas puede tener muchas etiquetas.
