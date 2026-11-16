package com.example.api_bloqueoDias.dto;

import com.example.api_bloqueoDias.model.EstadoSolicitud;
import jakarta.validation.constraints.NotNull;

public record CambioEstadoDto(@NotNull EstadoSolicitud estado) {}
