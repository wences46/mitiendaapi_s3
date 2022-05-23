package pe.todotic.mitiendaapi_s3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.mitiendaapi_s3.repository.PacienteRepository;
import pe.todotic.mitiendaapi_s3.model.Paciente;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/ultimos-pacientes")
    List<Paciente> getUltimosPacientes(){
        return pacienteRepository.findFirst20ByOrderByNumExpAsc();
    }

    @GetMapping("/pacientes")
    Page<Paciente> getPaciente(@PageableDefault(size = 10, sort = "numExp" , direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return pacienteRepository.findAll(pageable);
    }

    @GetMapping("/pacientes/{numExp}")
    Paciente obtener(@PathVariable String numExp) {
        return pacienteRepository
                .findByNumExp(numExp)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/pago-paypal")
    void crearPagoPayPal(
            @RequestBody List<Integer> idLibro,
            @RequestParam String urlRetorno
    ){
        //crear una venta y una orden de pago para paypal

    }


}
