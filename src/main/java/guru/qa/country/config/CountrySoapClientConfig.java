package guru.qa.country.config;

import guru.qa.country.service.client.CountrySoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountrySoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("quru.qa.xml.country");
        return marshaller;
    }

    @Bean
    public CountrySoapClient countrySoapClient(Jaxb2Marshaller marshaller) {
        CountrySoapClient client = new CountrySoapClient(marshaller);
        client.setDefaultUri("http://localhost:8080/ws/countries");
        return client;
    }
}
