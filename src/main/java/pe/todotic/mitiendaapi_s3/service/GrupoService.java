
package pe.todotic.mitiendaapi_s3.service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.todotic.mitiendaapi_s3.model.Grupo;
import pe.todotic.mitiendaapi_s3.model.ItemGrupo;
import pe.todotic.mitiendaapi_s3.model.Paciente;
import pe.todotic.mitiendaapi_s3.repository.GrupoRepository;
import pe.todotic.mitiendaapi_s3.repository.PacienteRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrupoService {


    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PayPalHttpClient payPalHttpClient;


    public String crearGrupoPayPal(List<Integer> idPacientes, String urlRetorno) throws IOException {

        //crear la venta
        Grupo grupo = new Grupo();
        List<ItemGrupo> items = new ArrayList<>();
        float total = 0;


        for (int idPaciente : idPacientes) {
            Paciente paciente = pacienteRepository
                    .findById(idPaciente)
                    .orElseThrow(EntityNotFoundException::new);

            ItemGrupo itemGrupo = new ItemGrupo();
            itemGrupo.setPaciente(paciente);
            itemGrupo.setPrecio(paciente.getPrecio());
            itemGrupo.setNumDescDisp(3);
            itemGrupo.setGrupo(grupo);

            items.add(itemGrupo);
            total += itemGrupo.getPrecio();

        }

        grupo.setFase(Grupo.Fase.CREADO);
        grupo.setFecha(LocalDateTime.now());
        grupo.setTotal(total);
        grupo.setItems(items);

        grupoRepository.save(grupo);


        //-------------------------------------------solicitud de pago a la api paypal-------------------------------------------

        //--------------------Datos Generales
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.brandName("Mi tienda Online");
        applicationContext.returnUrl(urlRetorno);
        applicationContext.cancelUrl(urlRetorno);

        //-----------------------crear solicitud del pedido---------------
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        orderRequest.applicationContext(applicationContext);

        //----------------------crera los detalles del pedido (opcional)------------

        List<Item> paypalItems = new ArrayList<>();

        grupo.getItems().forEach(itemGrupo -> {
            Money money = new Money()
                    .currencyCode("USD")
                    .value(itemGrupo.getPrecio().toString());

            Item paypalItem = new Item()
                    .name(itemGrupo.getPaciente().getNombre())
                    .quantity("1")
                    .unitAmount(money);

            paypalItems.add(paypalItem);

        });


        //--------------------crear el resumen de items
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();


        //--------------establecer el monto total de la venta-------------------
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
                .currencyCode("USD")
                .value(grupo.getTotal().toString());

        AmountBreakdown amountBreakdown = new AmountBreakdown()
                .itemTotal(
                        new Money()
                                .currencyCode("USD")
                                .value(grupo.getTotal().toString())
                );

        amountWithBreakdown.amountBreakdown(amountBreakdown);

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountWithBreakdown)
                .items(paypalItems)
                .description("Grupo #" + grupo.getIdGrupo())
                .referenceId(grupo.getIdGrupo().toString());

        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        //------------crear la solicitud http---------------------------
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

    public Grupo comprobarPayPal(String token) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(token);

        try {
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            Order order = response.result();

            // CREATE, APPROVED, COMPLETED, FAILED
            boolean success = order.status().equals("COMPLETED");

            if (success) {
                Integer idGrupo = Integer.parseInt(order.purchaseUnits().get(0).referenceId());

                Grupo grupo = grupoRepository
                        .findById(idGrupo)
                        .orElseThrow(EntityNotFoundException::new);

                grupo.setFase(Grupo.Fase.COMPLETADO);
                return grupoRepository.save(grupo);
            }
            return null;
        } catch (HttpException exception) {
            return null;
        }
    }


}
