package com.example.api_bloqueoDias.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
@Entity @Table(name="moscosos_solicitudes")
@Getter @Setter @NoArgsConstructor
public class MoscosoSolicitud {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idSolicitud;
 @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="id_profesor", nullable=false)
 @JsonIgnoreProperties({"hibernateLazyInitializer","handler","dni","cp","localidad","telefono","direccion","cuerpo"}) private Profesor profesor;
 @NotNull @Column(nullable=false) private LocalDate fecha;
 @NotNull @Enumerated(EnumType.STRING) @Column(nullable=false, length=15) private EstadoSolicitud estado;
 @NotNull @Enumerated(EnumType.STRING) @Column(name="tipo_lectivo", nullable=false, length=15) private TipoLectivo tipoLectivo;
}
