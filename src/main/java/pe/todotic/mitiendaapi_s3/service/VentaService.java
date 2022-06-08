package pe.todotic.mitiendaapi_s3.service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pe.todotic.mitiendaapi_s3.model.ItemVenta;
import pe.todotic.mitiendaapi_s3.model.Libro;
import pe.todotic.mitiendaapi_s3.model.Venta;
import pe.todotic.mitiendaapi_s3.repository.LibroRepository;
import pe.todotic.mitiendaapi_s3.repository.VentaRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private PayPalHttpClient payPalHttpClient;

    public String crearVentaPayPal(List<Integer> idLibros, String urlRetorno) throws IOException {
        // crear la venta
        Venta venta = new Venta();
        List<ItemVenta> items = new ArrayList<>();
        float total = 0;

        for (int idLibro : idLibros) {
            Libro libro = libroRepository
                    .findById(idLibro)
                    .orElseThrow(EntityNotFoundException::new);

            ItemVenta itemVenta = new ItemVenta();
            itemVenta.setLibro(libro);
            itemVenta.setPrecio(libro.getPrecio());
            itemVenta.setNumeroDescargasDisponibles(3);
            itemVenta.setVenta(venta);

            items.add(itemVenta);
            total += itemVenta.getPrecio();
        }
        venta.setEstado(Venta.Estado.CREADO);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setItems(items);

        ventaRepository.save(venta);

        //=========================> crear una solicitud de pago a la api de paypal

        // establecemos los datos generales
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.brandName("Mi Tienda Online");
        applicationContext.returnUrl(urlRetorno);
        applicationContext.cancelUrl(urlRetorno);

        // crear la solicitud del pedido
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        orderRequest.applicationContext(applicationContext);

        // crear los detalles del pedido (opcional)
        List<Item> paypalItems = new ArrayList<>();

        venta.getItems().forEach(itemVenta -> {
            Money money = new Money()
                    .currencyCode("USD")
                    .value(itemVenta.getPrecio().toString());

            Item paypalItem = new Item()
                    .name(itemVenta.getLibro().getTitulo())
                    .quantity("1")
                    .unitAmount(money);

            paypalItems.add(paypalItem);
        });

        // crear el resumen de items
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();

        // establecer el monto total de la venta
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
                .currencyCode("USD")
                .value(venta.getTotal().toString());

        AmountBreakdown amountBreakdown = new AmountBreakdown()
                .itemTotal(
                        new Money()
                                .currencyCode("USD")
                                .value(venta.getTotal().toString())
                );

        amountWithBreakdown.amountBreakdown(amountBreakdown);

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountWithBreakdown)
                .items(paypalItems)
                .description("Venta #" + venta.getId())
                .referenceId(venta.getId().toString());

        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        // crear la solicitud http
        OrdersCreateRequest request = new OrdersCreateRequest()
                .requestBody(orderRequest);

        HttpResponse<Order> response = payPalHttpClient.execute(request);
        Order order = response.result();

        return order
                .links()
                .stream()
                .filter(link -> link.rel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .href();
    }

    public Venta comprobarPayPal(String token) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(token);

        try {
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            Order order = response.result();

            // CREATE, APPROVED, COMPLETED, FAILED
            boolean success = order.status().equals("COMPLETED");

            if (success) {
                Integer idVenta = Integer.parseInt(order.purchaseUnits().get(0).referenceId());

                Venta venta = ventaRepository
                        .findById(idVenta)
                        .orElseThrow(EntityNotFoundException::new);

                venta.setEstado(Venta.Estado.COMPLETADO);
                return ventaRepository.save(venta);
            }
            return null;
        } catch (HttpException exception) {
            return null;
        }
    }

}
