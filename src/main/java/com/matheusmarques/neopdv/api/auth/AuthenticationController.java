package com.matheusmarques.neopdv.api.auth;

import com.matheusmarques.neopdv.api.auth.request.AuthenticationDTO;
import com.matheusmarques.neopdv.api.auth.request.RegisterDTO;
import com.matheusmarques.neopdv.api.auth.response.LoginResponseDTO;
import com.matheusmarques.neopdv.domain.shared.UserRole;
import com.matheusmarques.neopdv.domain.user.User;
import com.matheusmarques.neopdv.repository.UserRepository;
import com.matheusmarques.neopdv.service.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TokenService tokenService;

    AuthenticationController(AuthenticationManager manager, UserRepository repository){
        this.authenticationManager = manager;
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());


        return ResponseEntity
                .ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO request){
        if (this.repository.findByEmail(request.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User();
        newUser.setEmail(request.login());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.valueOf(request.role().toUpperCase()));

        this.repository.save(newUser);

        return ResponseEntity
                .ok()
                .build();

    }
}
