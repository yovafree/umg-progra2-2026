# Demo 03 — DAO y CRUD por consola

**Bloque 3.** Todas las operaciones (INSERT, SELECT, UPDATE, DELETE) en una sola clase: `EstudianteDAO`.

## Qué muestra

- [EstudianteDAO.java](src/main/java/com/umg/registro/modelo/EstudianteDAO.java):
  - `insertar(Estudiante)`: usa `RETURN_GENERATED_KEYS` y asigna el id generado.
  - `listar()`: `ORDER BY nombre`.
  - `buscarPorCarne(String)`: devuelve `null` si no existe.
  - `buscarPorNombre(String)`: `ILIKE '%texto%'`.
  - `actualizar(Estudiante)` y `eliminar(int)`: devuelven `boolean`.
  - `mapear(ResultSet)`: método privado que convierte una fila en un objeto.
  - Todos usan `PreparedStatement` y try-with-resources, y **todavía** declaran `throws SQLException`.
- [App.java](src/main/java/com/umg/registro/App.java): menú de consola con `Scanner`. **No tiene SQL**.
- [InyeccionSqlDemo.java](src/main/java/com/umg/registro/InyeccionSqlDemo.java): la consulta vulnerable junto a la segura.

## Ejecutar

```bash
cd demo-03-dao-crud
mvn compile exec:java
```

Demo de inyección SQL:

```powershell
mvn compile exec:java "-Dexec.mainClass=com.umg.registro.InyeccionSqlDemo"
```

## Qué observar en clase

1. **Listar** (opción 1): los estudiantes salen ordenados por nombre, no por id.
2. **Insertar** (opción 2) un carné nuevo, por ejemplo `0907-25-11111`. El programa muestra el id que generó PostgreSQL.
3. **Insertar** un carné repetido (`0907-23-12345`). El error sale "crudo", con su SQLState `23505`.
4. **Eliminar** el id 1: falla con `23503` porque María José tiene cursos asignados. El id 4 (Carlos) sí se puede eliminar.
5. **InyeccionSqlDemo**: con la entrada `' OR '1'='1`, la versión concatenada devuelve **toda la tabla** y la versión con `PreparedStatement` devuelve **0 filas**.

> ⚠️ El método `consultaVulnerable()` existe **solo para enseñar**. Nunca concatene datos del usuario en un SQL.
