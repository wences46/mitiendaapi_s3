package pe.todotic.mitiendaapi_s3.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.mitiendaapi_s3.model.Paciente;
import pe.todotic.mitiendaapi_s3.repository.PacienteRepository;
import pe.todotic.mitiendaapi_s3.web.dto.PacienteDTO;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;


    @GetMapping("/listar")
    List<Paciente> listar() {
        return pacienteRepository.findAll();
    }


    @GetMapping("/{id}")
    Paciente obtener(@PathVariable Integer id) {
        return pacienteRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/crear")
    Paciente crear(@Validated @RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = new ModelMapper().map(pacienteDTO, Paciente.class);
        return pacienteRepository.save(paciente);
    }



    @PutMapping("/{id}")
    Paciente actualizar(
            @PathVariable Integer id,
            @Validated @RequestBody PacienteDTO pacienteDTO
    ) {
        Paciente paciente = pacienteRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        new ModelMapper().map(pacienteDTO, paciente);

        pacienteRepository.save(paciente);

        return paciente;
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void eliminar(@PathVariable Integer id) {
        Paciente paciente = pacienteRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        pacienteRepository.delete(paciente);
    }


    @GetMapping
    Page<Paciente> index(
            @PageableDefault(size = 5, sort = "numExp" , direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return pacienteRepository.findAll(pageable);
    }



}
