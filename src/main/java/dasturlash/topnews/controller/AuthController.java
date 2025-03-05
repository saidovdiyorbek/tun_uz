package dasturlash.topnews.controller;

import dasturlash.topnews.dto.profile.RegistrationDTO;
import dasturlash.topnews.enums.AppLanguage;
import dasturlash.topnews.service.AuthService;
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
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationDTO dto,
                                           @RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang) {
        return ResponseEntity.ok(authService.registration(dto, lang));
    }

    @GetMapping("/verification/{code}")
    public ResponseEntity<String> verification(@PathVariable("code") int code
                                               /*@RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang*/) {
        return ResponseEntity.ok(authService.verification(code/*, lang*/));
    }

}
