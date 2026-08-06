# UMG Programación II - Ejemplos de clase

Este repositorio reúne ejercicios y prácticas del curso de Programación II (UMG), organizados por clase para facilitar su consulta, ejecución y mantenimiento.

## Tabla de clases

| Clase | Proyecto | Resumen de ejemplos | Ruta |
| --- | --- | --- | --- |
| Clase 4 | Ejemplo1 | Operaciones básicas, estructuras de control (if/switch/for/while/do-while), uso de clase Persona y formulario Swing para tabla de multiplicar. | [Clase4/Ejemplo1](Clase4/Ejemplo1/) |
| Clase 4-1 | Ejemplos | Listas con ArrayList, manejo de objetos Persona, lectura/escritura de archivos de texto y formulario Swing con JTable para visualizar personas. | [Clase4-1/Ejemplos](Clase4-1/Ejemplos/) |

## Ejemplos por clase

### Clase 4

- [Ejemplo1.java](Clase4/Ejemplo1/src/main/java/edu/umg/ejemplo1/Ejemplo1.java): suma, comparación y ciclos.
- [Ejemplo2.java](Clase4/Ejemplo1/src/main/java/edu/umg/ejemplo1/Ejemplo2.java): creación y uso de objetos Persona.
- [jfrmPrincipal.java](Clase4/Ejemplo1/src/main/java/edu/umg/ejemplo1/jfrmPrincipal.java): formulario Swing para generar una tabla de multiplicar.

### Clase 4-1

- [Ejemplos.java](Clase4-1/Ejemplos/src/main/java/edu/umg/ejemplos/Ejemplos.java): operaciones con ArrayList de nombres.
- [Ejemplos2.java](Clase4-1/Ejemplos/src/main/java/edu/umg/ejemplos/Ejemplos2.java): lista de objetos Persona y recorrido de datos.
- [Ejemplos3.java](Clase4-1/Ejemplos/src/main/java/edu/umg/ejemplos/Ejemplos3.java): lectura y escritura en [datos.txt](Clase4-1/Ejemplos/datos.txt).
- [jFormPrincipal.java](Clase4-1/Ejemplos/src/main/java/edu/umg/ejemplos/jFormPrincipal.java): carga de personas en tabla Swing (JTable).

## Requisitos

- Java 17 o superior.
- Maven 3.8 o superior.
- IDE recomendado: NetBeans, IntelliJ IDEA o VS Code.

## Cómo ejecutar

1. Elegir la carpeta de trabajo (por ejemplo, [Clase4](Clase4/) o [Clase4-1/Ejemplos](Clase4-1/Ejemplos/)).
2. Abrir una terminal en esa carpeta.
3. Compilar el proyecto:

```bash
mvn clean compile
```

4. Ejecutar pruebas (si aplica):

```bash
mvn test
```

## Changelog

### 2026-08-05

- Se reorganizó el README raíz para presentar una tabla de clases.
- Se agregó el resumen de ejemplos por cada clase y archivo principal.
- Se mantuvieron y ajustaron las instrucciones de compilación/pruebas con Maven.
