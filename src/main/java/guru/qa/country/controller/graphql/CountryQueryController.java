package guru.qa.country.controller.graphql;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryQueryController {

    private final CountryService countryService;

    @QueryMapping
    public List<CountryDto> countries() {
        return countryService.getAllCountries();
    }

    @QueryMapping
    public CountryDto countryByCode(@Argument String code) {
        return countryService.findByCode(code);
    }
}
