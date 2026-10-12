ALTER TABLE Matricula
  ADD CONSTRAINT pk_matricula
  PRIMARY KEY (id_estudiante, id_curso);

ALTER TABLE Matricula
  ADD CONSTRAINT fk_mat_estudiante
  FOREIGN KEY (id_estudiante)
  REFERENCES Estudiante(id_estudiante);
