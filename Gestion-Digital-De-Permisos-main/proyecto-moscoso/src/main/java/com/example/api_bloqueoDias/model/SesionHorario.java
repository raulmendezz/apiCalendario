package com.example.api_bloqueoDias.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalTime;
@Entity @Table(name="sesiones_horario")
@Getter @Setter @NoArgsConstructor
public class SesionHorario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idSesion;
 @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="cod_grupo", nullable=false)
 @JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) private GrupoAlumnado grupo;
 @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="id_profesor", nullable=false)
 @JsonIgnoreProperties({"hibernateLazyInitializer","handler","dni","cp","localidad","telefono","direccion","cuerpo"}) private Profesor profesor;
 @NotNull @Column(name="hora_inicio", nullable=false) private LocalTime horaInicio;
 @NotNull @Column(name="hora_fin", nullable=false) private LocalTime horaFin;
 @NotNull @Enumerated(EnumType.STRING) @Column(name="dia_semana", nullable=false, length=10) private DiaSemana diaSemana;
}
