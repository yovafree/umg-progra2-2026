# Clase 14 — Conexiones a bases de datos desde Java con MVC

Proyectos de ejemplo de la clase práctica de **Programación II** (UMG, Centro Universitario de Cobán).
Caso de trabajo: **Sistema de Registro de Estudiantes**.

Cada carpeta `demo-XX` es un proyecto Maven **independiente**: se abre, compila y ejecuta por sí solo.
Todos usan el paquete base `com.umg.registro`, Java 21, JDBC puro y Swing hecho a mano (sin archivos `.form`).

| Demo | Bloque de la clase | Qué muestra |
| --- | --- | --- |
| [demo-01-conexion](demo-01-conexion/) | 1. JDBC | `ConexionBD`, `DriverManager`, try-with-resources, metadatos y partes de una `SQLException`. |
| [demo-02-mvc-esqueleto](demo-02-mvc-esqueleto/) | 2. Patrón MVC | Paquetes `config`, `modelo`, `vista` y `controlador`; POJO `Estudiante`. Todavía sin BD. |
| [demo-03-dao-crud](demo-03-dao-crud/) | 3. CRUD con DAO | `EstudianteDAO` con `PreparedStatement`, menú de consola e `InyeccionSqlDemo`. |
| [demo-04-swing-mvc](demo-04-swing-mvc/) | 4. Formulario Swing | `FrmEstudiante` + `EstudianteControlador`: INSERT, SELECT, UPDATE y DELETE desde la ventana. |
| [demo-05-excepciones](demo-05-excepciones/) | 5. Manejo de errores | `DatosException`, `traducir(SQLException)` por SQLState, transacción en `eliminar` y catálogo de errores. |

## Requisitos

- **JDK 21**
- **Maven 3.9** o superior (NetBeans ya trae uno en `NetBeans/java/maven/bin`)
- **Docker Desktop** (con Docker Compose)

## 1. Levantar la base de datos

Desde esta carpeta (`Clase14`):

```bash
docker compose up -d
```

Se crea un PostgreSQL 16 con:

| Dato | Valor |
| --- | --- |
| Base de datos | `universidad` |
| Puerto | `5432` |
| Usuario / contraseña | `postgres` / `postgres` |
| Tablas | `estudiante` (5 registros), `curso` (4), `asignacion` (6) |

El script [db/init.sql](db/init.sql) solo corre la **primera vez**. Para volver a los datos originales después de la clase:

```bash
docker compose down -v
docker compose up -d
```

Para entrar a la consola de PostgreSQL:

```bash
docker exec -it umg-postgres-universidad psql -U postgres -d universidad
```

> **¿Ya tiene otro PostgreSQL en el puerto 5432?** Levante este en otro puerto y avísele a Java:
>
> ```powershell
> $env:DB_PORT = "5433"; docker compose up -d
> $env:DB_URL  = "jdbc:postgresql://localhost:5433/universidad"
> ```

## 2. Ejecutar un demo

```bash
cd demo-01-conexion
mvn compile exec:java
```

Lo mismo en cada carpeta `demo-XX`. Para el demo de inyección SQL (otra clase `main`):

```powershell
cd demo-03-dao-crud
mvn compile exec:java "-Dexec.mainClass=com.umg.registro.InyeccionSqlDemo"
```

> En PowerShell, los parámetros `-D...` van **entre comillas**.

### Variables de entorno (opcionales)

`ConexionBD` lee estas variables. Si no existen, usa los valores del `docker-compose.yml`.

| Variable | Valor por defecto |
| --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/universidad` |
| `DB_USER` | `postgres` |
| `DB_PASS` | `postgres` |

## Estructura

```
Clase14/
├── README.md
├── docker-compose.yml
├── db/init.sql
├── demo-01-conexion/
├── demo-02-mvc-esqueleto/
├── demo-03-dao-crud/
├── demo-04-swing-mvc/
└── demo-05-excepciones/
```

## Problemas comunes

- **Los acentos se ven como `�` en la consola:** ejecute `chcp 65001` antes de `mvn`, o use la consola del IDE.
- **`Connection refused` (SQLState 08001):** la base de datos no está levantada (`docker compose ps`) o el puerto no coincide.
- **Los datos ya no son los originales:** reinicie la base de datos con `docker compose down -v` y `docker compose up -d`.
