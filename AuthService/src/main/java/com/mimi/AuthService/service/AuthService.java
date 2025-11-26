package com.mimi.AuthService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mimi.AuthService.dto.LoginRequest;
import com.mimi.AuthService.dto.LoginResponse;
import com.mimi.AuthService.dto.UserAuthDTO;
import com.mimi.AuthService.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public LoginResponse login(String email, String password) {

        try {
            String url = "http://localhost:8084/api/users/validate";

            UserAuthDTO usuario = restTemplate.postForObject(
                    url,
                    new LoginRequest(email, password),
                    UserAuthDTO.class
            );

            if (usuario == null) {
                return new LoginResponse(
                        true,
                        "Credenciales inválidas",
                        null,
                        null
                );
            }

            String token = jwtUtil.generateToken(
                    usuario.getCorreo(),
                    usuario.getRolId()
            );

            return new LoginResponse(
                    true,
                    "Login exitoso",
                    usuario,
                    token
            );

        } catch (HttpClientErrorException.Unauthorized e) {
            return new LoginResponse(
                    true,
                    "Credenciales inválidas",
                    null,
                    null
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(
                    false,
                    "Error conectando a UserService",
                    null,
                    null
            );
        }
    }
}
