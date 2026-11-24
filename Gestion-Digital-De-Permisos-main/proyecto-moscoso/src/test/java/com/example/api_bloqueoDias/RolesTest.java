package com.example.api_bloqueoDias;

import com.example.api_bloqueoDias.dto.LoginRequestDto;
import com.example.api_bloqueoDias.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class RolesTest {
    @Autowired WebApplicationContext ctx;
    @Autowired FilterChainProxy securityFilter;
    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(securityFilter).build();
    }
    @Autowired AuthService auth;

    private String bearer(String user, String pass) {
        return "Bearer " + auth.login(new LoginRequestDto(user, pass)).token();
    }

    @Test
    void profesorNoPuedeEscribir() throws Exception {
        String t = bearer("user_test", "userpass_test");
        mvc.perform(delete("/api/bloqueos-calendario/999999").header("Authorization", t)).andExpect(status().isForbidden());
        mvc.perform(delete("/api/profesores/999999").header("Authorization", t)).andExpect(status().isForbidden());
        mvc.perform(patch("/api/moscosos-solicitudes/999999/estado").header("Authorization", t)
                .contentType(MediaType.APPLICATION_JSON).content("{\"estado\":\"APROBADA\"}")).andExpect(status().isForbidden());
        mvc.perform(get("/api/bloqueos-calendario").header("Authorization", t)).andExpect(status().isOk());
    }

    @Test
    void adminSiPuede() throws Exception {
        String t = bearer("admin_test", "admin_pass_test");
        mvc.perform(delete("/api/bloqueos-calendario/999999").header("Authorization", t)).andExpect(status().isNotFound());
    }

    @Test
    void meDevuelveProfesorOnull() throws Exception {
        mvc.perform(get("/api/auth/me").header("Authorization", bearer("admin_test", "admin_pass_test")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.rol").value("ADMIN")).andExpect(jsonPath("$.profesor").doesNotExist());
        mvc.perform(get("/api/auth/me").header("Authorization", bearer("user_test", "userpass_test")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.profesor.idProfesor").value(1))
                .andExpect(jsonPath("$.profesor.nombre").value("Raul"));
        mvc.perform(get("/api/auth/me")).andExpect(status().isUnauthorized());
    }

    @Test
    void profesorCreaSolicitudSoloASuNombre() throws Exception {
        String t = bearer("user_test", "userpass_test");
        mvc.perform(post("/api/moscosos-solicitudes").header("Authorization", t).contentType(MediaType.APPLICATION_JSON)
                .content("{\"profesor\":{\"idProfesor\":3},\"fecha\":\"2027-02-10\",\"estado\":\"PENDIENTE\",\"tipoLectivo\":\"LECTIVO\"}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.profesor.idProfesor").value(1));
        mvc.perform(get("/api/moscosos-solicitudes").header("Authorization", t))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].profesor.idProfesor").value(1));
    }

    @Test
    void rutaInexistenteEs404() throws Exception {
        mvc.perform(get("/api/no-existe").header("Authorization", bearer("user_test", "userpass_test")))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value(404));
    }
}
