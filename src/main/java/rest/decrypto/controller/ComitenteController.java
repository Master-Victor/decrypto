package rest.decrypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.decrypto.dtos.ComitenteDTO;
import rest.decrypto.models.Comitente;
import rest.decrypto.services.ComitenteService;

import java.util.List;

@RestController
@RequestMapping("/api/comitentes")
public class ComitenteController {

    @Autowired
    private ComitenteService comitenteService;

    @GetMapping
    public ResponseEntity<List<ComitenteDTO>> getAllComitentes() {
        return ResponseEntity.ok(comitenteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComitenteDTO> getComitenteById(@PathVariable Long id) {
        try {
            ComitenteDTO comitente = comitenteService.getComitenteById(id);
            return ResponseEntity.ok(comitente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ComitenteDTO());
        }
    }

    @PostMapping
    public ResponseEntity<?> createComitente(@RequestBody ComitenteDTO comitente) {
        try {
            ComitenteDTO newComitente = comitenteService.createComitente(comitente);
            return ResponseEntity.ok(newComitente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el comitente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComitente(@PathVariable Long id, @RequestBody ComitenteDTO comitente) {
        try {
            ComitenteDTO updatedComitente = comitenteService.updateComitente(id, comitente);
            return ResponseEntity.ok(updatedComitente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el comitente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComitente(@PathVariable Long id) {
        try {
            comitenteService.deleteComitente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el comitente: " + e.getMessage());
        }
    }
}
