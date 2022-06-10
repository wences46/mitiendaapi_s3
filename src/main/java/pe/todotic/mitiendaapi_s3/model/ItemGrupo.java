package pe.todotic.mitiendaapi_s3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ItemGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iditem_grupo")
    private Integer idItemGrupo;

    @ManyToOne
    @JoinColumn(name = "idPaciente", referencedColumnName = "id_paciente")
    private Paciente paciente;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idgrupo", referencedColumnName = "idgrupo")
    private Grupo grupo;

    @Column(name = "num_desc_disp")
    private  Integer numDescDisp;

    @Column(name = "precio")
    private Float precio;


}
