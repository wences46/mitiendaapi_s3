package pe.todotic.mitiendaapi_s3.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgrupo")
    private Integer id;

    private LocalDateTime fecha;

    private Float total;

    @Enumerated(EnumType.STRING)
    private Fase fase;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<ItemGrupo> items;

    public enum Fase{
        CREADO,
        COMPLETADO
    }

}
