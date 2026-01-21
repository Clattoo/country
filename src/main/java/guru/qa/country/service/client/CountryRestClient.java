package guru.qa.country.service.client;

import guru.qa.country.controller.CountryDto;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class CountryRestClient implements CountryClient {

    private final RestTemplate restTemplate;
    private final String baseUri;

    public CountryRestClient(RestTemplate restTemplate, String baseUri) {
        this.restTemplate = restTemplate;
        this.baseUri = baseUri;
    }

    @Override
    public CountryDto getCountryByCode(String code) {
        return restTemplate.getForObject(baseUri + "/api/countries/" + code, CountryDto.class);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return List.of(Objects.requireNonNull(restTemplate.getForObject(baseUri + "/api/countries", CountryDto[].class)));
    }

    @Override
    public CountryDto createCountry(CountryDto country) {
        return restTemplate.postForObject(baseUri + "/api/countries", country, CountryDto.class);
    }

    @Override
    public CountryDto updateCountryName(String code, String newName) {
        CountryDto updateDto = new CountryDto();
        updateDto.setName(newName);
        restTemplate.put(baseUri + "/api/countries/" + code, updateDto);
        return getCountryByCode(code);
    }
}
