package com.example.api_bloqueoDias.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity @Table(name="usuarios")
@Getter @Setter @NoArgsConstructor
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idUsuario;
 @NotBlank @Column(nullable=false, unique=true, length=50) private String username;
 @NotBlank @JsonIgnore @Column(nullable=false, length=100) private String password;
 @NotNull @Enumerated(EnumType.STRING) @Column(nullable=false, length=10) private Rol rol;
 @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_profesor", unique=true) @JsonIgnore private Profesor profesor;
}
