package pe.todotic.mitiendaapi_s3.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.mitiendaapi_s3.exception.BadRequestException;
import pe.todotic.mitiendaapi_s3.model.ItemVenta;
import pe.todotic.mitiendaapi_s3.model.Libro;
import pe.todotic.mitiendaapi_s3.model.Venta;
import pe.todotic.mitiendaapi_s3.repository.ItemVentaRepository;
import pe.todotic.mitiendaapi_s3.repository.LibroRepository;
import pe.todotic.mitiendaapi_s3.repository.VentaRepository;
import pe.todotic.mitiendaapi_s3.service.FileSystemStorageService;
import pe.todotic.mitiendaapi_s3.service.VentaService;
//import pe.todotic.mitiendaapi_s3.web.dto.ResultadoPagoPayPal;
import pe.todotic.mitiendaapi_s3.web.dto.SolicitudPagoPayPal;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/apiventa")
public class HomeControllerVenta {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ItemVentaRepository itemVentaRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;


    @GetMapping("/ultimos-libros")
    List<Libro> getUltimosLibros() {
        return libroRepository.findFirst6ByOrderByFechaCreacionDesc();
    }

    @GetMapping("/libros")
    Page<Libro> getLibros(@PageableDefault(sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        return libroRepository.findAll(pageable);
    }

    @GetMapping("/libros/{slug}")
    Libro obtener(@PathVariable String slug) {
        return libroRepository
                .findBySlug(slug)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/pago-paypal")
    SolicitudPagoPayPal crearPagoPayPal(
            @RequestBody List<Integer> idLibros,
            @RequestParam String urlRetorno
    ) throws IOException {
        String url = ventaService.crearVentaPayPal(idLibros, urlRetorno);

        SolicitudPagoPayPal solicitudPagoPayPal = new SolicitudPagoPayPal();
        solicitudPagoPayPal.setUrl(url);

        return solicitudPagoPayPal;
    }



    /*

    @PostMapping("/pago-paypal/comprobar")
    ResultadoPagoPayPal comprobarPagoPayPal(@RequestParam String token) throws IOException {
        Venta venta = ventaService.comprobarPayPal(token);
        boolean success = false;

        ResultadoPagoPayPal resultadoPagoPayPal = new ResultadoPagoPayPal();

        if (venta != null) {
            success = true;
            resultadoPagoPayPal.setIdVenta(venta.getId());
        }
        resultadoPagoPayPal.setSuccess(success);

        return resultadoPagoPayPal;
    }

    @GetMapping("/detalles-venta/{idVenta}")
    Venta getVenta(@PathVariable Integer idVenta) {
        return ventaRepository
                .findByIdAndEstado(idVenta, Venta.Estado.COMPLETADO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/descargar-archivo/{idItemVenta}")
    Resource descargarArchivoItemVenta(@PathVariable Integer idItemVenta) {
        ItemVenta itemVenta = itemVentaRepository
                .findById(idItemVenta)
                .orElseThrow(EntityNotFoundException::new);

        if (itemVenta.getNumeroDescargasDisponibles() > 0) {
            itemVenta.consumirDescargasDisponibles();
            itemVentaRepository.save(itemVenta);

            return fileSystemStorageService.loadAsResource(itemVenta.getLibro().getRutaArchivo());
        } else {
            throw new BadRequestException("Ya no existen más descargas disponibles para este item.");
        }
    */
}
