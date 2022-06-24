package pe.todotic.mitiendaapi_s3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.mitiendaapi_s3.model.Grupo;
import pe.todotic.mitiendaapi_s3.repository.GrupoRepository;
import pe.todotic.mitiendaapi_s3.repository.PacienteRepository;
import pe.todotic.mitiendaapi_s3.model.Paciente;
import pe.todotic.mitiendaapi_s3.service.GrupoService;
import pe.todotic.mitiendaapi_s3.web.dto.ResultadoPagoPayPal;
import pe.todotic.mitiendaapi_s3.web.dto.SolicitudPagoPayPal;


import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;


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
    SolicitudPagoPayPal crearPagoPayPal(
            @RequestBody List<Integer> idPacientes,
            @RequestParam String urlRetorno
    ) throws IOException {
        //crear una venta y una orden de pago para paypal
        String url = grupoService.crearGrupoPayPal(idPacientes, urlRetorno);
        SolicitudPagoPayPal solicitudPagoPayPal = new SolicitudPagoPayPal();
        solicitudPagoPayPal.setUrl(url);

        return solicitudPagoPayPal;

    }

    @PostMapping("/pago-paypal/comprobar")
    ResultadoPagoPayPal  comprobarPagoPayPal(@RequestParam String token) throws  IOException{
        Grupo grupo = grupoService.comprobarPayPal(token);
        boolean success = false;

        ResultadoPagoPayPal resultadoPagoPayPal = new ResultadoPagoPayPal();

        if(grupo != null ){
            success = true;
            resultadoPagoPayPal.setIdGrupo(grupo.getId());
        }
        resultadoPagoPayPal.setSuccess(success);

        return resultadoPagoPayPal;

    }

    @GetMapping("/detalles-grupo/{idGrupo}")
    Grupo getGrupo(@PathVariable Integer idGrupo) {
        return grupoRepository
                .findByIdAndFase(idGrupo, Grupo.Fase.COMPLETADO)
                .orElseThrow(EntityNotFoundException::new);
    }


    //---------------------------------------------------------------------------------------------------------------------------------------




}



