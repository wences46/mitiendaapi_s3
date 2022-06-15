package pe.todotic.mitiendaapi_s3.model.vm;

import lombok.Data;

@Data
public class AuthCredentials {
    private String username;
    private String password;
}
