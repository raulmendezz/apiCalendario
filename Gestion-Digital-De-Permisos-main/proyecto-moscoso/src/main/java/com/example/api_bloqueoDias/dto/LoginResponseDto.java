package com.example.api_bloqueoDias.dto;
public record LoginResponseDto(String token, String tipo, long expiraEnSegundos, String username, String rol) {}
