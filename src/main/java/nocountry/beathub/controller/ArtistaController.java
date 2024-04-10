package nocountry.beathub.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import nocountry.beathub.dto.request.UsuarioLoginDTO;
import nocountry.beathub.exception.ArtistaExistException;
import nocountry.beathub.exception.HibernateOperationException;
import nocountry.beathub.exception.IncorrectPasswordException;
import nocountry.beathub.exception.UsernameNotFoundException;
import nocountry.beathub.model.Artista;
import nocountry.beathub.service.IArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artista")
public class ArtistaController {


    @Autowired
    private IArtistaService artistaService;

    @Operation(
            summary = "Registra un artista",
            description = ""
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid Artista artista) {
        try {
            boolean estadoRegistro = artistaService.registerArtista(artista);
            if (estadoRegistro) {
                return new ResponseEntity<>("Usuario registrado exitosamente.", HttpStatus.CREATED);
            }
        } catch (ArtistaExistException e) {
            return new ResponseEntity<>("El nombre de usuario ya fue registrado.", HttpStatus.BAD_REQUEST);
        } catch (HibernateOperationException e) {
            return new ResponseEntity<>("Error interno del servidor al intentar registrar el usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Si el estado de registro es falso (algo inesperado ocurrió)
        return new ResponseEntity<>("Error desconocido al intentar registrar el usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Autentica un artista",
            description = ""
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UsuarioLoginDTO usuarioLogin)  {
        try {
            boolean authenticated = artistaService.loginUser(usuarioLogin.getUsername(), usuarioLogin.getPassword());
            if (authenticated) {
                return ResponseEntity.status(HttpStatus.OK).body("Usuario autenticado");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nombre de usuario no encontrado");
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        } catch (HibernateOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }


    }

    @Operation(
            summary = "Obtiene todos los artistas",
            description = ""
    )
    @GetMapping("/artistas")
    public ResponseEntity<List<Artista>> findAllArtistas() {
        List<Artista> artistas = artistaService.findAllArtistas();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }


//    @Operation(
//            summary = "Trae un artista",
//            description = "Busca un artista por id"
//    )
//    @GetMapping("/{userId}")
//    public String getUser(@PathVariable Long artista){
//        return "Prueba";
//    }
}
