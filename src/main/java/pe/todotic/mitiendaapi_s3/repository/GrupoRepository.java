package pe.todotic.mitiendaapi_s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.mitiendaapi_s3.model.Grupo;

import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    Optional<Grupo> findByIdGrupoAndFase(Integer idGrupo, Grupo.Fase fase);


}
