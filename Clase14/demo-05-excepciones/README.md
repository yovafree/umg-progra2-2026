# Demo 05 — Manejo de excepciones SQL

**Bloque 5.** La misma aplicación del demo 4, pero con errores que el usuario entiende.

## Qué cambió respecto al demo 4

| Archivo | Cambio |
| --- | --- |
| [DatosException.java](src/main/java/com/umg/registro/modelo/DatosException.java) | **Nueva.** Extiende `RuntimeException` y guarda la `SQLException` original como causa. |
| [EstudianteDAO.java](src/main/java/com/umg/registro/modelo/EstudianteDAO.java) | Ya **no** declara `throws SQLException`. Cada error pasa por `traducir()`, que decide el mensaje según `getSQLState()` y escribe el detalle técnico en `System.err`. |
| | `eliminar()` usa una **transacción** (`setAutoCommit(false)`, `commit`, `rollback`): primero borra las asignaciones y luego al estudiante. |
| | `eliminarSinTransaccion()` es nuevo y sirve para comparar. |
| [EstudianteControlador.java](src/main/java/com/umg/registro/controlador/EstudianteControlador.java) | Solo atrapa `DatosException` y muestra `ex.getMessage()`. Ya no importa `java.sql`. |
| [FrmEstudiante.java](src/main/java/com/umg/registro/vista/FrmEstudiante.java) | El spinner de semestre permite de **0 a 15 a propósito**, para poder provocar el error CHECK. |

### Traducción de SQLState

| SQLState | Restricción | Mensaje al usuario |
| --- | --- | --- |
| `23505` | UNIQUE | Ya existe un estudiante con ese carné. |
| `23502` | NOT NULL | Falta un dato obligatorio (carné o nombre). |
| `23514` | CHECK | El semestre debe estar entre 1 y 10. |
| `23503` | FOREIGN KEY | No se puede eliminar: el estudiante tiene cursos asignados. |
| `08xxx` | Conexión | No hay conexión con la base de datos… |
| otro | — | Ocurrió un error inesperado en la base de datos… |

## Ejecutar

```bash
cd demo-05-excepciones
mvn compile exec:java
```

Deje la consola visible junto a la ventana. Ahí aparece el detalle técnico (`[EstudianteDAO] SQLState=...`) mientras el usuario solo ve el mensaje amigable.

## Catálogo de errores: cómo provocar cada uno desde la interfaz

| # | Error | Pasos | SQLState | Mensaje esperado |
| --- | --- | --- | --- | --- |
| 1 | **Carné duplicado** | Nuevo → carné `0907-23-12345`, cualquier nombre → Guardar | `23505` | Ya existe un estudiante con ese carné. |
| 2 | **Semestre fuera de rango** | Nuevo → carné y nombre nuevos, semestre `0` o `11`–`15` → Guardar | `23514` | El semestre debe estar entre 1 y 10. |
| 3 | **BD apagada** | En la terminal: `docker compose stop` → en la ventana: Nuevo | `08001` | No hay conexión con la base de datos… |
| 4 | **Tabla mal escrita** | Renombre la tabla (ver abajo) → Nuevo | `42P01` | Ocurrió un error inesperado… (cae en el `default`) |
| 5 | **Eliminar con asignaciones** | En el controlador ponga `USAR_TRANSACCION = false` → ejecute → seleccione a María José → Eliminar | `23503` | No se puede eliminar: el estudiante tiene cursos asignados. |
| extra | **Dato obligatorio nulo** | Comente la validación `camposValidos()` en `guardar()` → Guardar sin nombre | `23502` | Falta un dato obligatorio… |

Detalles de algunos casos:

- **Caso 3.** Para volver: `docker compose start` y presione Nuevo. El programa se recupera sin reiniciarse, porque cada operación abre una conexión nueva.
- **Caso 4.** Simula que el SQL apunta a una tabla que no existe:
  ```bash
  docker exec umg-postgres-universidad psql -U postgres -d universidad -c "ALTER TABLE estudiante RENAME TO estudiantes;"
  # ...probar en la ventana... y luego restaurar:
  docker exec umg-postgres-universidad psql -U postgres -d universidad -c "ALTER TABLE estudiantes RENAME TO estudiante;"
  ```
  Otra forma es cambiar `FROM estudiante` por `FROM estudiantes` en `listar()` del DAO.
- **Caso 5.** Vuelva a poner `USAR_TRANSACCION = true` y elimine otra vez a María José. Ahora sí se elimina, junto con sus 2 asignaciones, en una sola transacción. La consola muestra `commit: ... eliminado junto con 2 asignación(es)`.

> Después de la clase, restaure los datos: `docker compose down -v` y `docker compose up -d`.

## Qué observar en clase

- **Demo 4 contra demo 5:** con los mismos errores, el demo 4 siempre dice lo mismo y el demo 5 explica qué pasó.
- El mensaje para el usuario y el detalle técnico van por caminos distintos: ventana y consola.
- `traducir()` decide por el **SQLState** y no por el texto del error, que cambia según el idioma y la versión del servidor.
- El controlador ya no conoce JDBC. Si mañana cambia la base de datos, el controlador no cambia.
