package guru.qa.country.controller.graphql;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryMutationController {

    private final CountryService countryService;

    // Mutation: createCountry
    @MutationMapping
    public CountryDto createCountry(@Argument("input") CountryDto dto) {
        return countryService.addCountry(dto);
    }

    // Mutation: updateCountryName
    @MutationMapping
    public CountryDto updateCountryName(@Argument String code,
                                        @Argument String name) {
        CountryDto dto = CountryDto.builder()
                .name(name)
                .build();

        countryService.editCountry(code, dto);

        // вернуть обновлённую страну
        return countryService.getAllCountries()
                .stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
