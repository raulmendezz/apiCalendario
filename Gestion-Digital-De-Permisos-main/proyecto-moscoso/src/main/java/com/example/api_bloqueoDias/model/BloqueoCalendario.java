package com.example.api_bloqueoDias.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
@Entity @Table(name="bloqueos_calendario")
@Getter @Setter @NoArgsConstructor
public class BloqueoCalendario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotNull @Column(name="fecha_inicio", nullable=false) private LocalDate fechaInicio;
 @NotNull @Column(name="fecha_fin", nullable=false) private LocalDate fechaFin;
 @NotBlank @Column(nullable=false, length=60) private String tipo;
}
