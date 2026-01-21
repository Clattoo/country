package guru.qa.country.service.soap;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.service.CountryService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import quru.qa.xml.country.*;

import java.util.List;

import static guru.qa.country.config.AppConfig.SOAP_NAMESPACE;

@Endpoint
public class CountryEndpoint {

    private final CountryService countryService;

    public CountryEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "getCountryByCodeRequest")
    @ResponsePayload
    public CountryResponse getCountryByCodeRq(@RequestPayload GetCountryByCodeRequest request) {
        CountryDto country = countryService.findByCode(request.getCode());
        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.getId().toString());
        xmlCountry.setCode(country.getCode());
        xmlCountry.setName(country.getName());
        response.setCountry(xmlCountry);
        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "getAllCountriesRequest")
    @ResponsePayload
    public GetAllCountriesResponse getAllCountries(@RequestPayload GetAllCountriesRequest request) {
        List<CountryDto> countries = countryService.getAllCountries();

        GetAllCountriesResponse response = new GetAllCountriesResponse();
        for (CountryDto dto : countries) {
            Country xmlCountry = new Country();
            xmlCountry.setId(dto.getId().toString());
            xmlCountry.setCode(dto.getCode());
            xmlCountry.setName(dto.getName());
            response.getCountries().add(xmlCountry);
        }

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "createCountryRequest")
    @ResponsePayload
    public CountryResponse createCountry(@RequestPayload CreateCountryRequest request) {
        CountryDto dto = CountryDto.builder()
                .code(request.getCountry().getCode())
                .name(request.getCountry().getName())
                .build();

        CountryDto saved = countryService.addCountry(dto);

        Country xmlCountry = new Country();
        xmlCountry.setId(saved.getId().toString());
        xmlCountry.setCode(saved.getCode());
        xmlCountry.setName(saved.getName());

        CountryResponse response = new CountryResponse();
        response.setCountry(xmlCountry);
        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "updateCountryNameRequest")
    @ResponsePayload
    public CountryResponse updateCountryName(@RequestPayload UpdateCountryNameRequest request) {
        CountryInput input = request.getCountry();

        CountryDto updated = countryService.editCountry(
                input.getCode(),
                CountryDto.builder().name(input.getName()).build()
        );

        Country xmlCountry = new Country();
        xmlCountry.setId(updated.getId().toString());
        xmlCountry.setCode(updated.getCode());
        xmlCountry.setName(updated.getName());

        CountryResponse response = new CountryResponse();
        response.setCountry(xmlCountry);
        return response;
    }
}