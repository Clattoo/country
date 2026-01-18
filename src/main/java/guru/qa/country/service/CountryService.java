package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountries();
    CountryDto addCountry(CountryDto country);
    CountryDto editCountry(String code, CountryDto country);
    CountryDto findByCode(String code);
}
