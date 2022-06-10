package pe.todotic.mitiendaapi_s3.web.dto;

import lombok.Data;

@Data
public class ResultadoPagoPayPal {
    private boolean success;
    private Integer idGrupo;
}
