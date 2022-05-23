package pe.todotic.mitiendaapi_s3.web.dto;

import lombok.Data;
import pe.todotic.mitiendaapi_s3.model.Usuario;


@Data
public class UsuarioDTO {
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private Usuario.Rol rol;
}
