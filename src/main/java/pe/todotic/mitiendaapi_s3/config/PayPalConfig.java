package pe.todotic.mitiendaapi_s3.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Bean
    PayPalHttpClient payPalHttpClient() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
                "Ad7-eAM-G6LKVRDNbVPQZ1UomDWm5g7ajCy5BlihoMPh_rBh_5qHkdNborSkgJjQXEYshx3CsiBPtql6",
                "EA9aPmoczGo4gRx9DN1v11ETWRyxWeVXbUMJp-vRTnIT4WrHUjRzXvLhOfsYQHQePoSjE6uF_1tx5wdR"
        );
        return new PayPalHttpClient(environment);
    }



}
