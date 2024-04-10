package nocountry.beathub.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import nocountry.beathub.exception.LicenciaDuplicadaException;
import nocountry.beathub.exception.LicenciaNoExisteException;
import nocountry.beathub.model.Beat;
import nocountry.beathub.service.IBeatService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/beat")
public class BeatController {

    private final IBeatService iBeatService;
    @Operation(
        summary = "Obtiene todos los beats registrados en el sistema."
    )
    @GetMapping("/beats")
    public ResponseEntity<List<Beat>> getAllBeats() {
        return ResponseEntity.ok(iBeatService.getAllBeats());
    }
    @Operation(
            summary = "Guarda un beat en el sistema."
    )
    @PostMapping("/save-beat")
    public ResponseEntity<Beat> saveBeat(@RequestBody Beat newBeat){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(iBeatService.saveBeat(newBeat));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new LicenciaDuplicadaException("No se puede duplicar la licencia.");
            } else if(e.getMessage().contains("foreign key constraint fails")){
                throw new LicenciaNoExisteException("Licencia no existe");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }
}
