package guru.qa.country.controller.graphql;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.controller.CountryInput;
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

    @MutationMapping
    public CountryDto createCountry(@Argument("input") CountryInput input) {
        CountryDto dto = CountryDto.builder()
                .name(input.getName())
                .code(input.getCode())
                .build();

        return countryService.addCountry(dto);
    }

    @MutationMapping
    public CountryDto updateCountryName(@Argument String code,
                                        @Argument String name) {
        CountryDto dto = CountryDto.builder()
                .name(name)
                .build();

        return countryService.editCountry(code, dto);
    }
}
