package dasturlash.tun_uz.controller;

import dasturlash.tun_uz.dto.profile.RegistrationDTO;
import dasturlash.tun_uz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/verification/{code}")
    public ResponseEntity<String> verification(@PathVariable("code") int code) {
        return ResponseEntity.ok(authService.verification(code));
    }

}
