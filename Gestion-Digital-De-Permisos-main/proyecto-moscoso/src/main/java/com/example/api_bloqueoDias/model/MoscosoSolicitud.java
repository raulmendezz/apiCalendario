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
 @JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) private Profesor profesor;
 @NotNull @Column(nullable=false) private LocalDate fecha;
 @NotBlank @Column(nullable=false, length=30) private String estado;
 @NotBlank @Column(name="tipo_lectivo", nullable=false, length=20) private String tipoLectivo;
}
