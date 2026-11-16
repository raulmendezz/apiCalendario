package com.example.api_bloqueoDias.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity @Table(name="grupos_alumnado")
@Getter @Setter @NoArgsConstructor
public class GrupoAlumnado {
 @Id @NotBlank @Column(name="cod_grupo", length=30) private String codGrupo;
 @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="id_profesor", nullable=false)
 @JsonIgnoreProperties({"hibernateLazyInitializer","handler","dni","cp","localidad","telefono","direccion","cuerpo"}) private Profesor profesor;
}
