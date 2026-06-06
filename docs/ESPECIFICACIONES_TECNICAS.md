# Especificaciones tecnicas

## Arquitectura

Se utiliza una arquitectura en capas:

- Vista: Swing.
- Controlador: clases controller.
- Servicio: validaciones y logica.
- DAO: persistencia con Hibernate.
- Modelo: entidades JPA.

## Hibernate

La configuracion esta en `src/main/resources/META-INF/persistence.xml`.
Se usa `JPAUtil` para crear los EntityManager.

## DAO

Cada entidad tiene su DAO propio.

## Swing

La ventana principal usa `JFrame` y `JTabbedPane`.
Los formularios usan `JTextField`, `JTextArea`, `JComboBox`, `JList`, `JButton`, `JTable` y `JOptionPane`.
