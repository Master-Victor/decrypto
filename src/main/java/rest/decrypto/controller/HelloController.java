package rest.decrypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.decrypto.dtos.MensajeDTO;

@RestController
public class HelloController {
    @GetMapping("/hi")
    public ResponseEntity<MensajeDTO> hi() {
        return ResponseEntity.ok(new MensajeDTO("Hi"));
    }

    @GetMapping("/hello")
    public ResponseEntity<MensajeDTO> hello() {
        return ResponseEntity.ok(new MensajeDTO("Hello, World!"));
    }
}
