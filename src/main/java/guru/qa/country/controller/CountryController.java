package guru.qa.country.controller;

import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    // GET — получить все страны
    @GetMapping
    public List<CountryDto> getAll() {
        return countryService.getAllCountries();
    }

    // POST — добавить страну
    @PostMapping
    public ResponseEntity<CountryDto> add(@RequestBody CountryDto dto) {
        try {
            CountryDto result = countryService.addCountry(dto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Void> edit(@PathVariable String code,
                                     @RequestBody CountryDto dto) {
        try {
            countryService.editCountry(code, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
