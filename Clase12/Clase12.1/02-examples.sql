CREATE TABLE Matricula (
  id_estudiante INT,
  id_curso INT,
  PRIMARY KEY (id_estudiante, id_curso),
  FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante) ON DELETE CASCADE,
  FOREIGN KEY (id_curso) REFERENCES Curso(id_curso) ON DELETE RESTRICT
);
