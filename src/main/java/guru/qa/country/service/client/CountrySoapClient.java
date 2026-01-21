package guru.qa.country.service.client;

import guru.qa.country.controller.CountryDto;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.stereotype.Component;
import quru.qa.xml.country.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountrySoapClient extends WebServiceGatewaySupport implements CountryClient {

    public CountrySoapClient(Jaxb2Marshaller marshaller) {
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setDefaultUri("http://localhost:8080/ws/countries");
    }

    @Override
    public CountryDto getCountryByCode(String code) {
        GetCountryByCodeRequest req = new GetCountryByCodeRequest();
        req.setCode(code);
        CountryResponse resp = (CountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);

        return mapToDto(resp.getCountry());
    }

    @Override
    public List<CountryDto> getAllCountries() {
        GetAllCountriesRequest req = new GetAllCountriesRequest();
        GetAllCountriesResponse resp = (GetAllCountriesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);

        return resp.getCountries().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDto createCountry(CountryDto dto) {
        CreateCountryRequest req = new CreateCountryRequest();
        CountryInput input = new CountryInput();
        input.setName(dto.getName());
        input.setCode(dto.getCode());
        req.setCountry(input);

        CountryResponse resp = (CountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);

        return mapToDto(resp.getCountry());
    }

    @Override
    public CountryDto updateCountryName(String code, String newName) {
        UpdateCountryNameRequest req = new UpdateCountryNameRequest();
        CountryInput ci = new CountryInput();
        ci.setCode(code);
        ci.setName(newName);
        req.setCountry(ci);

        CountryResponse resp = (CountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);

        return mapToDto(resp.getCountry());
    }

    private CountryDto mapToDto(Country c) {
        return CountryDto.builder()
                .id(c.getId() != null ? java.util.UUID.fromString(c.getId()) : null)
                .name(c.getName())
                .code(c.getCode())
                .build();
    }
}