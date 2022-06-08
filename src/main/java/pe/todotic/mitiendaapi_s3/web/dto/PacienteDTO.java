package pe.todotic.mitiendaapi_s3.web.dto;

import lombok.Data;
import javax.validation.constraints.*;
import pe.todotic.mitiendaapi_s3.model.Paciente;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class PacienteDTO {

    @NotNull
    @Pattern(regexp = "[a-zA-Z- ]+")
    private String nombre;
    @Pattern(regexp = "[a-zA-Z- ]+")
    private String apePat;
    @Pattern(regexp = "[a-zA-Z- ]+")
    private String apeMat;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    private String numExp;
    @Pattern(regexp = "[0-9]+")
    private String telContacto;

    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Paciente.Estado estado;

    private String rutaPortada;

    private  String descripcion;


    private Float precio;

    private String tutor;



}
