package pe.todotic.mitiendaapi_s3.web.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ErrorDetail {
    private long timeStamp;
    private int status;
    private String title;
    private String detail;

    /**
     * un mapa de errores de validación, si es que corresponde.
     * Por ejemplo:
     *
     * "titulo": [
     *  { "code": "NotNull", "message": "El título es obligatorio."  },
     *  { "code": "Size", "message": "El título debe estar entre 3 y 100 caracteres" }
     * ]
     *
     */
    private Map<String, List<ValidationError>> errors = new HashMap<>();
}
