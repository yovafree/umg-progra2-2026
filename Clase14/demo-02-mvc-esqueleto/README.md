# Demo 02 — Esqueleto MVC

**Bloque 2.** Cómo se organiza un proyecto con el patrón Modelo–Vista–Controlador.
Este demo **todavía no** usa la base de datos: solo muestra la organización.

## Estructura de paquetes

```
com.umg.registro
├── App.java                      ← arranca y conecta las piezas
├── config/ConexionBD.java        ← cómo conectarse (demo 1)
├── modelo/Estudiante.java        ← datos (POJO). El DAO llega en el demo 3
├── vista/FrmEstudiante.java      ← ventana Swing: muestra y captura
└── controlador/EstudianteControlador.java  ← escucha eventos y coordina
```

## Cómo se comunican las capas

```
            Usuario
               │  clic en "Guardar"
               ▼
   ┌───────────────────────┐
   │        VISTA          │   FrmEstudiante
   │  muestra y captura    │   (sin SQL, sin reglas)
   └───────────────────────┘
       │ evento       ▲ mostrarMensaje()
       │ (clic)       │ cargarTabla(lista)
       ▼              │
   ┌───────────────────────┐
   │     CONTROLADOR       │   EstudianteControlador
   │  valida y coordina    │
   └───────────────────────┘
       │ dao.insertar()  ▲ Estudiante /
       │ dao.listar()    │ List<Estudiante>
       ▼                 │
   ┌───────────────────────┐
   │       MODELO          │   Estudiante (datos)
   │  datos y acceso a BD  │   EstudianteDAO (único con SQL)
   └───────────────────────┘
       │ JDBC            ▲ ResultSet
       ▼                 │
   ┌───────────────────────┐
   │  PostgreSQL           │   BD "universidad"
   └───────────────────────┘
```

La vista **nunca** habla directamente con el modelo ni con la base de datos. Todo pasa por el controlador.

## Ejecutar

```bash
cd demo-02-mvc-esqueleto
mvn compile exec:java
```

Se imprime un `Estudiante` en la consola (su `toString()`) y se abre una ventana mínima. Su botón demuestra que el controlador recibe el evento de la vista.

## Qué observar en clase

- [Estudiante.java](src/main/java/com/umg/registro/modelo/Estudiante.java): constructor vacío, constructor **sin id** (el id lo genera la BD), getters, setters y `toString()`.
- Lea los comentarios de [FrmEstudiante](src/main/java/com/umg/registro/vista/FrmEstudiante.java) y de [EstudianteControlador](src/main/java/com/umg/registro/controlador/EstudianteControlador.java): describen qué hace cada capa y qué **no** debe hacer.
- `App` solo crea la vista y se la entrega al controlador.
