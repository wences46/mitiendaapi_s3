package pe.todotic.mitiendaapi_s3.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.mitiendaapi_s3.exception.BadRequestException;
import pe.todotic.mitiendaapi_s3.model.Usuario;
import pe.todotic.mitiendaapi_s3.repository.UsuarioRepository;
import pe.todotic.mitiendaapi_s3.web.dto.UsuarioDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registrar")
    void registrarUsuario(@RequestBody @Validated UsuarioDTO usuarioDTO) {
        boolean emailYaExiste = usuarioRepository.existsByEmail(usuarioDTO.getEmail());

        if (emailYaExiste) {
            throw new BadRequestException("El email ya fue registrado para otro usuario");
        }
        Usuario usuario = new ModelMapper().map(usuarioDTO, Usuario.class);
        usuario.setRol(Usuario.Rol.LECTOR);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    @GetMapping("/verificar-email")
    Map<String, Boolean> verificarEmail(@RequestParam String email) {
        boolean emailYaExiste = usuarioRepository
                .existsByEmail(email);

        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", emailYaExiste);
        return result;
    }

}
