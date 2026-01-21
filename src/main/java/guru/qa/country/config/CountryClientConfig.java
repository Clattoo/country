package guru.qa.country.config;

import guru.qa.country.service.client.CountryClient;
import guru.qa.country.service.client.CountryRestClient;
import guru.qa.country.service.client.CountrySoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CountryClientConfig {

    @Value("${country.client}")
    private String clientType;

    @Value("${country.base-uri}")
    private String baseUri;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CountryClient countryClient(RestTemplate restTemplate, CountrySoapClient soapClient) {
        if ("soap".equalsIgnoreCase(clientType)) {
            return soapClient;
        } else {
            return new CountryRestClient(restTemplate, baseUri);
        }
    }
}