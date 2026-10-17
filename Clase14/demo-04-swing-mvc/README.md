# Demo 04 — Formulario Swing con MVC

**Bloque 4.** El CRUD completo desde una ventana, con las tres capas trabajando juntas.

## Qué muestra

- [FrmEstudiante.java](src/main/java/com/umg/registro/vista/FrmEstudiante.java) (vista):
  - Campos: `txtId` (no editable), `txtCarne`, `txtNombre`, `txtCorreo` y `JSpinner` de semestre (1–10).
  - Botones Nuevo, Guardar, Actualizar, Eliminar y Buscar.
  - `JTable` no editable.
  - Métodos de ayuda: `limpiar()`, `mostrarMensaje()`, `mostrarError()` y `cargarTabla()`.
  - **No tiene SQL ni lógica.**
- [EstudianteControlador.java](src/main/java/com/umg/registro/controlador/EstudianteControlador.java):
  - Registra los `ActionListener` y valida los campos obligatorios.
  - Pide confirmación antes de eliminar y recarga la tabla después de cada operación.
  - Al seleccionar una fila, pasa sus datos al formulario (`ListSelectionListener`, `getValueIsAdjusting()`, correo `null`).
- [App.java](src/main/java/com/umg/registro/App.java): arranca con `SwingUtilities.invokeLater`.

## Ejecutar

```bash
cd demo-04-swing-mvc
mvn compile exec:java
```

## Uso del formulario

| Botón | Qué hace |
| --- | --- |
| Nuevo | Limpia el formulario y recarga la tabla. |
| Guardar | Inserta un estudiante nuevo (el formulario no debe tener id). |
| Actualizar | Modifica el estudiante seleccionado en la tabla. |
| Eliminar | Pide confirmación y elimina el estudiante seleccionado. |
| Buscar | Busca por carné exacto si hay carné; si no, por parte del nombre. |

## Qué observar en clase

- Haga clic en la fila de **Sofía**: su correo es `NULL` y el formulario lo muestra vacío sin errores.
- Guarde un estudiante sin nombre: el **controlador** lo detiene antes de llegar a la BD.
- Manejo de errores **básico**, a propósito. Pruebe:
  - Guardar un carné repetido (`0907-23-12345`).
  - Eliminar a María José (tiene cursos asignados).
  - Apagar la BD con `docker compose stop` y presionar Nuevo.

  En los tres casos el usuario ve el mismo mensaje: *"Ocurrió un error con la base de datos."* ¿Qué pasó en realidad? No lo sabe. El demo 5 resuelve esto.
