package guru.qa.country.service.client;

import guru.qa.country.controller.CountryDto;

import java.util.List;

public interface CountryClient {
    CountryDto getCountryByCode(String code);
    List<CountryDto> getAllCountries();
    CountryDto createCountry(CountryDto country);
    CountryDto updateCountryName(String code, String newName);
}
