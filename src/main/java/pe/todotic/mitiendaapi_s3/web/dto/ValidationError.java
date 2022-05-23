package pe.todotic.mitiendaapi_s3.web.dto;

import lombok.Data;

@Data
public class ValidationError {
    private String code;
    private String message;
}
