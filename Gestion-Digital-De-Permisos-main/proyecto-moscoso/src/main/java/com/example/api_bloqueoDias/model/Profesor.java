package com.example.api_bloqueoDias.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity @Table(name="profesores")
@Getter @Setter @NoArgsConstructor
public class Profesor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idProfesor;
 @NotBlank @Column(nullable=false, length=80) private String nombre;
 @NotBlank @Column(nullable=false, length=100) private String apellidos;
 @NotBlank @Column(nullable=false, unique=true, length=20) private String dni;
 @NotBlank @Column(nullable=false, length=10) private String cp;
 @NotBlank @Column(nullable=false, length=100) private String localidad;
 @NotBlank @Column(nullable=false, length=20) private String telefono;
 @NotBlank @Column(nullable=false, length=160) private String direccion;
 @NotBlank @Column(nullable=false, length=80) private String cuerpo;
 @NotBlank @Column(nullable=false, length=100) private String especialidad;
 @NotBlank @Column(nullable=false, length=100) private String departamento;
}
