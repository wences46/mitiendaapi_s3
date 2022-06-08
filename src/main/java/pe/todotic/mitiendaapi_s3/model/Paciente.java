package pe.todotic.mitiendaapi_s3.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Paciente {
    @Id
    @Column(name = "id_paciente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    private String nombre;
    private String apePat;
    private String apeMat;
    private String numExp;
    private String telContacto;
    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Estado estado;


    public enum Estado{
        primervez,
        subsecuente,
        baja,
        egreso
    }

    private String rutaPortada;

    private String descripcion;

    private Float precio;

    private  String tutor;




}
