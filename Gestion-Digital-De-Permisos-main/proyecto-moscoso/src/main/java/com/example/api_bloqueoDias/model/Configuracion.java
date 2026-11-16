package com.example.api_bloqueoDias.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity @Table(name="configuracion")
@Getter @Setter @NoArgsConstructor
public class Configuracion {
 @Id @NotBlank @Column(length=60) private String clave;
 @NotBlank @Column(nullable=false, length=100) private String valor;
}
