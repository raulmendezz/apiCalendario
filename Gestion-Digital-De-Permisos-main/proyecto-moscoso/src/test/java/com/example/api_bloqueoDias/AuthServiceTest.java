package com.example.api_bloqueoDias;

import com.example.api_bloqueoDias.dto.LoginRequestDto;
import com.example.api_bloqueoDias.dto.LoginResponseDto;
import com.example.api_bloqueoDias.exception.CredencialesInvalidasException;
import com.example.api_bloqueoDias.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthServiceTest {

    @Autowired private AuthService authService;
    @Autowired private JwtDecoder jwtDecoder;

    @Test
    void loginCorrecto_devuelveTokenConRolAdmin() {
        LoginResponseDto resp = authService.login(new LoginRequestDto("admin_test", "admin_pass_test"));
        assertThat(resp.token()).isNotBlank();
        assertThat(resp.tipo()).isEqualTo("Bearer");
        assertThat(resp.rol()).isEqualTo("ADMIN");
        Jwt jwt = jwtDecoder.decode(resp.token());
        assertThat(jwt.getClaimAsStringList("roles")).contains("ADMIN");
    }

    @Test
    void rolProfesorParaUsuarioNormal() {
        LoginResponseDto resp = authService.login(new LoginRequestDto("user_test", "userpass_test"));
        assertThat(resp.rol()).isEqualTo("PROFESOR");
        Jwt jwt = jwtDecoder.decode(resp.token());
        assertThat(jwt.getClaimAsStringList("roles")).contains("PROFESOR");
    }

    @Test
    void passwordIncorrecta_lanzaCredencialesInvalidasException() {
        assertThrows(CredencialesInvalidasException.class,
            () -> authService.login(new LoginRequestDto("admin_test", "wrong_password")));
    }

    @Test
    void usuarioInexistente_lanzaCredencialesInvalidasException() {
        assertThrows(CredencialesInvalidasException.class,
            () -> authService.login(new LoginRequestDto("usuario_que_no_existe", "cualquier")));
    }
}
