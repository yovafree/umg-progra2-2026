# Demo 01 — Conexión con JDBC

**Bloque 1.** Comprobar que Java se conecta a PostgreSQL.

## Qué muestra

- [ConexionBD.java](src/main/java/com/umg/registro/config/ConexionBD.java): constructor privado y `obtenerConexion()` con `DriverManager`. Lee `DB_URL`, `DB_USER` y `DB_PASS` y, si no existen, usa valores por defecto.
- [App.java](src/main/java/com/umg/registro/App.java): abre la conexión con **try-with-resources**, imprime el motor y su versión (`getMetaData()`) y, si falla, muestra `getMessage()`, `getSQLState()` y `getErrorCode()`.

## Ejecutar

```bash
docker compose up -d        # desde la carpeta Clase14
cd demo-01-conexion
mvn compile exec:java
```

Salida esperada:

```
Intentando conectar a PostgreSQL...
¡Conexión exitosa!
  Motor:    PostgreSQL
  Versión:  16.x
  Driver:   PostgreSQL JDBC Driver 42.7.14
```

## "Romper" la conexión a propósito

Las variables de entorno permiten provocar errores **sin tocar el código** (PowerShell):

| Prueba | Comando | SQLState esperado |
| --- | --- | --- |
| Contraseña incorrecta | `$env:DB_PASS = "mala"; mvn compile exec:java` | `28P01` (autenticación) |
| Puerto incorrecto | `$env:DB_URL = "jdbc:postgresql://localhost:5999/universidad"; mvn compile exec:java` | `08001` (no se puede conectar) |
| BD inexistente | `$env:DB_URL = "jdbc:postgresql://localhost:5432/noexiste"; mvn compile exec:java` | `3D000` |
| Servidor apagado | `docker compose stop` y ejecutar de nuevo | `08001` |

Para volver a la normalidad: `Remove-Item Env:DB_PASS, Env:DB_URL` (o cierre la terminal) y `docker compose start`.

## Qué observar en clase

- La conexión se cierra sola al terminar el `try`, aunque haya error.
- El **SQLState** es un código estándar: `08` significa problema de conexión y `28` problema de credenciales. En el demo 5 se usa para decidir qué mensaje mostrar.
- El `getErrorCode()` de PostgreSQL casi siempre es `0`. Por eso nos guiamos por el SQLState.
