package com.matheusmarques.neopdv.api.auth;

import com.matheusmarques.neopdv.api.auth.request.AuthenticationDTO;
import com.matheusmarques.neopdv.api.auth.request.RefreshTokenRequest;
import com.matheusmarques.neopdv.api.auth.request.RegisterDTO;
import com.matheusmarques.neopdv.api.auth.response.LoginResponseDTO;
import com.matheusmarques.neopdv.api.auth.response.RefreshTokenResponse;
import com.matheusmarques.neopdv.domain.shared.UserRole;
import com.matheusmarques.neopdv.domain.user.User;
import com.matheusmarques.neopdv.repository.UserRepository;
import com.matheusmarques.neopdv.service.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;

    AuthenticationController(AuthenticationManager manager, UserRepository repository, TokenService tokenService){
        this.authenticationManager = manager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var accessToken = tokenService.generateAccessToken((User)auth.getPrincipal());
        var refreshToken = tokenService.generateRefreshToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        String email = tokenService.validateRefreshToken(request.refreshToken());

        if (email.isEmpty()) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }

        User user = (User) repository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        String newAccessToken = tokenService.generateAccessToken(user);

        return ResponseEntity.ok(new RefreshTokenResponse(newAccessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO request){
        if (this.repository.findByEmail(request.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User();
        newUser.setEmail(request.login());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.valueOf(request.role().toUpperCase()));

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
