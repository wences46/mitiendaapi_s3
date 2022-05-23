package pe.todotic.mitiendaapi_s3.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LibroDTO {

    @NotNull(message = "El título es obligatorio")
    // TODO 1: no debe ser nulo.
    //  "El título es obligatorio"
    @Size(min = 3, max = 100)
    // TODO 2: debe tener entre 3 a 100 caracteres de longitud.
    //  "El título debe tener {min} caracteres como mínimo y {max} caracteres como máximo"
    private String titulo;

    @Pattern(regexp = "[a-z0-9-]+")
    // TODO 3: debe cumplir la siguiente expresión regular "[a-z0-9-]+".
    //  "El slug debe tener un formato válido"
    @NotNull
    // TODO 4: no debe ser nulo: "El slug es obligatorio"
    private String slug;

    @NotBlank
    // TODO 5: no debe ser nulo y debe contener al menos un caracter que no sea un espacio.
    //  "La descripción es obligatoria"
    private String descripcion;

    @NotBlank
    // TODO 6: no debe ser nulo y debe contener al menos un caracter que no sea un espacio.
    //  "La portada es obligatoria"
    private String rutaPortada;

    @NotBlank
    // TODO 7: no debe ser nulo y debe contener al menos un caracter que no sea un espacio.
    //  "El archivo es obligatorio"
    private String rutaArchivo;

    @NotNull
    // TODO 8: no debe ser nulo.
    //  "El precio es obligatorio"
    @Min(0)
    // TODO 9: debe ser un valor positivo o igual a 0.
    //  "El precio debe ser mayor o igual a 0"
    private Float precio;
}
