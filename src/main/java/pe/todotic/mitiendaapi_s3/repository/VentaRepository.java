package pe.todotic.mitiendaapi_s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.mitiendaapi_s3.model.Venta;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    Optional<Venta> findByIdAndEstado(Integer id, Venta.Estado estado);
}