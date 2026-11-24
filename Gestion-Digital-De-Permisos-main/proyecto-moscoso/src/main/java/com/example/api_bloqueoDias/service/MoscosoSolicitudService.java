package com.example.api_bloqueoDias.service;

import com.example.api_bloqueoDias.exception.ReglaNegocioException;
import com.example.api_bloqueoDias.model.EstadoSolicitud;
import com.example.api_bloqueoDias.model.MoscosoSolicitud;
import com.example.api_bloqueoDias.model.Profesor;
import com.example.api_bloqueoDias.model.Usuario;
import com.example.api_bloqueoDias.repository.BloqueoCalendarioRepository;
import com.example.api_bloqueoDias.repository.MoscosoSolicitudRepository;
import com.example.api_bloqueoDias.repository.ProfesorRepository;
import com.example.api_bloqueoDias.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MoscosoSolicitudService {
    private final MoscosoSolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ProfesorRepository profesorRepository;
    private final BloqueoCalendarioRepository bloqueoRepository;
    private final ConfiguracionService configuracionService;

    public MoscosoSolicitudService(MoscosoSolicitudRepository repository,
                                   UsuarioRepository usuarioRepository,
                                   ProfesorRepository profesorRepository,
                                   BloqueoCalendarioRepository bloqueoRepository,
                                   ConfiguracionService configuracionService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
        this.bloqueoRepository = bloqueoRepository;
        this.configuracionService = configuracionService;
    }

    public List<MoscosoSolicitud> listar(Jwt jwt) {
        if (esAdmin(jwt)) return repository.findAll();
        return repository.findByProfesorIdProfesor(profesorDe(jwt).getIdProfesor());
    }

    public MoscosoSolicitud obtener(Long id, Jwt jwt) {
        MoscosoSolicitud solicitud = obtener(id);
        comprobarPropietario(solicitud, jwt);
        return solicitud;
    }

    private MoscosoSolicitud obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("MoscosoSolicitud no encontrado: " + id));
    }

    public MoscosoSolicitud crear(MoscosoSolicitud entidad, Jwt jwt) {
        // Un profesor solo puede pedir días a su propio nombre
        if (!esAdmin(jwt)) entidad.setProfesor(profesorDe(jwt));
        entidad.setEstado(EstadoSolicitud.PENDIENTE);
        validarSolicitud(entidad, null);
        return repository.save(entidad);
    }

    public MoscosoSolicitud actualizar(Long id, MoscosoSolicitud datos, Jwt jwt) {
        MoscosoSolicitud actual = obtener(id);
        comprobarPropietario(actual, jwt);
        comprobarPendiente(actual, jwt);
        if (!esAdmin(jwt)) datos.setProfesor(null); // un profesor no puede cambiar el profesor
        copiar(actual, datos);
        validarSolicitud(actual, id);
        return repository.save(actual);
    }

    public MoscosoSolicitud cambiarEstado(Long id, EstadoSolicitud nuevoEstado) {
        MoscosoSolicitud solicitud = obtener(id);
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE)
            throw new ReglaNegocioException("Solo se puede resolver una solicitud en estado PENDIENTE.");
        if (nuevoEstado == EstadoSolicitud.PENDIENTE)
            throw new ReglaNegocioException("No se puede volver a PENDIENTE una vez iniciada la resolución.");
        if (nuevoEstado == EstadoSolicitud.APROBADA) {
            int aprobadas = repository.countByFechaAndEstado(solicitud.getFecha(), EstadoSolicitud.APROBADA);
            if (aprobadas >= configuracionService.getCapacidadDiaria())
                throw new ReglaNegocioException("Se ha alcanzado la capacidad diaria máxima para el día " + solicitud.getFecha() + ".");
        }
        solicitud.setEstado(nuevoEstado);
        return repository.save(solicitud);
    }

    public void eliminar(Long id, Jwt jwt) {
        MoscosoSolicitud solicitud = obtener(id);
        comprobarPropietario(solicitud, jwt);
        comprobarPendiente(solicitud, jwt);
        repository.deleteById(id);
    }

    // Un profesor solo puede modificar o borrar solicitudes aún pendientes
    private void comprobarPendiente(MoscosoSolicitud solicitud, Jwt jwt) {
        if (!esAdmin(jwt) && solicitud.getEstado() != EstadoSolicitud.PENDIENTE)
            throw new ReglaNegocioException("Solo se pueden modificar o eliminar solicitudes en estado PENDIENTE.");
    }

    private boolean esAdmin(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");
        return roles != null && roles.contains("ADMIN");
    }

    // Profesor enlazado al usuario del token (409 si no tiene ninguno)
    private Profesor profesorDe(Jwt jwt) {
        Usuario u = usuarioRepository.findByUsername(jwt.getSubject())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + jwt.getSubject()));
        if (u.getProfesor() == null)
            throw new ReglaNegocioException("Tu usuario no tiene ningún profesor enlazado.");
        // findById devuelve la entidad real (no el proxy lazy del usuario)
        return profesorRepository.findById(u.getProfesor().getIdProfesor())
                .orElseThrow(() -> new EntityNotFoundException("Profesor no encontrado: " + u.getProfesor().getIdProfesor()));
    }

    private void comprobarPropietario(MoscosoSolicitud solicitud, Jwt jwt) {
        if (esAdmin(jwt)) return;
        if (!solicitud.getProfesor().getIdProfesor().equals(profesorDe(jwt).getIdProfesor()))
            throw new AccessDeniedException("No puedes acceder a solicitudes de otro profesor.");
    }

    private void validarSolicitud(MoscosoSolicitud e, Long idExcluir) {
        // 3.1: fecha bloqueada en el calendario
        if (e.getFecha() != null && bloqueoRepository.existsByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(e.getFecha(), e.getFecha()))
            throw new ReglaNegocioException("La fecha " + e.getFecha() + " está bloqueada en el calendario.");
        // 3.2: mismo profesor, misma fecha
        if (e.getProfesor() != null && e.getFecha() != null) {
            boolean duplicado = idExcluir == null
                    ? repository.existsByProfesorIdProfesorAndFecha(e.getProfesor().getIdProfesor(), e.getFecha())
                    : repository.existsByProfesorIdProfesorAndFechaAndIdSolicitudNot(e.getProfesor().getIdProfesor(), e.getFecha(), idExcluir);
            if (duplicado)
                throw new ReglaNegocioException("El profesor ya tiene una solicitud para la fecha " + e.getFecha() + ".");
        }
    }

    private void copiar(MoscosoSolicitud a, MoscosoSolicitud d) {
        if (d.getProfesor() != null) a.setProfesor(d.getProfesor());
        if (d.getFecha() != null) a.setFecha(d.getFecha());
        if (d.getTipoLectivo() != null) a.setTipoLectivo(d.getTipoLectivo());
    }
}
