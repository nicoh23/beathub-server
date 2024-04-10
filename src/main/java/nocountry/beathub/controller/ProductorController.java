package nocountry.beathub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nocountry.beathub.dto.request.UsuarioLoginDTO;
import nocountry.beathub.exception.*;
import nocountry.beathub.model.Artista;
import nocountry.beathub.model.Productor;
import nocountry.beathub.service.IArtistaService;
import nocountry.beathub.service.IProductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productor")
@Tag(name="Controlador de productor", description = "Controlador de para acceder a las operaciones del productor")
public class ProductorController {

    @Autowired
    private IProductorService productorService;

    @Operation(
            summary = "Registra un artista",
            description = ""
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid Productor productor) {
        try {
            boolean estadoRegistro = productorService.registerProductor(productor);
            if (estadoRegistro) {
                return new ResponseEntity<>("Usuario registrado exitosamente.", HttpStatus.CREATED);
            }
        } catch (ProductorExistException e) {
            return new ResponseEntity<>("El nombre de usuario ya fue registrado.", HttpStatus.BAD_REQUEST);
        } catch (HibernateOperationException e) {
            return new ResponseEntity<>("Error interno del servidor al intentar registrar el usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Si el estado de registro es falso (algo inesperado ocurrió)
        return new ResponseEntity<>("Error desconocido al intentar registrar el usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Autentica un productor",
            description = "Permite autenticar el usuario y los password de un productor"
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UsuarioLoginDTO usuarioLogin) {
        try {
            boolean authenticated = productorService.loginUser(usuarioLogin.getUsername(), usuarioLogin.getPassword());
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
            summary = "Obtiene todos los productores",
            description = "Obtiene un listado de todos los productores que hay en la base de datos"
    )
    @GetMapping("/productores")
    public ResponseEntity<List<Productor>> findAllProductores() {
        List<Productor> productores = productorService.findAllProductores();
        return new ResponseEntity<>(productores, HttpStatus.OK);
    }

}