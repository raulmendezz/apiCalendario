package com.example.api_bloqueoDias.exception;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
 private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 @ExceptionHandler(EntityNotFoundException.class)
 public ResponseEntity<ApiError> noEncontrado(EntityNotFoundException ex, HttpServletRequest req) { return respuesta(HttpStatus.NOT_FOUND,ex.getMessage(),req); }
 @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
 public ResponseEntity<ApiError> datosInvalidos(Exception ex, HttpServletRequest req) {
  String mensaje=ex instanceof MethodArgumentNotValidException m ? m.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).collect(Collectors.joining("; ")) : ex.getMessage();
  return respuesta(HttpStatus.BAD_REQUEST,mensaje,req);
 }
 @ExceptionHandler(HttpMessageNotReadableException.class)
 public ResponseEntity<ApiError> mensajeInvalido(HttpMessageNotReadableException ex, HttpServletRequest req) { return respuesta(HttpStatus.BAD_REQUEST,"El cuerpo de la petición no es válido o tiene formato incorrecto.",req); }
 @ExceptionHandler(ReglaNegocioException.class)
 public ResponseEntity<ApiError> reglaViolada(ReglaNegocioException ex, HttpServletRequest req) { return respuesta(HttpStatus.CONFLICT,ex.getMessage(),req); }
 @ExceptionHandler(DataIntegrityViolationException.class)
 public ResponseEntity<ApiError> conflicto(DataIntegrityViolationException ex, HttpServletRequest req) { return respuesta(HttpStatus.CONFLICT,"No se puede completar la operación porque hay datos relacionados o duplicados.",req); }
 @ExceptionHandler(Exception.class)
 public ResponseEntity<ApiError> interno(Exception ex, HttpServletRequest req) { log.error("Error interno en {}", req.getRequestURI(), ex); return respuesta(HttpStatus.INTERNAL_SERVER_ERROR,"Se ha producido un error interno.",req); }
 private ResponseEntity<ApiError> respuesta(HttpStatus status,String message,HttpServletRequest req) { return ResponseEntity.status(status).body(new ApiError(LocalDateTime.now(),status.value(),status.getReasonPhrase(),message,req.getRequestURI())); }
}
