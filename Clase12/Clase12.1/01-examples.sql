CREATE TABLE Estudiante (id_estudiante INT PRIMARY KEY, nombre VARCHAR(80));
CREATE TABLE Curso (id_curso INT PRIMARY KEY, titulo VARCHAR(80));
CREATE TABLE Matricula (
  id_matricula INT PRIMARY KEY, id_estudiante INT, id_curso INT,
  FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante),
  FOREIGN KEY (id_curso) REFERENCES Curso(id_curso)
);
