package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.repository.CountryEntity;
import guru.qa.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    @Override
    public List<CountryDto> getAllCountries() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CountryDto addCountry(CountryDto dto) {
        if (repository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Country with code " + dto.getCode() + " already exists");
        }

        CountryEntity entity = CountryEntity.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .build();

        return toDto(repository.save(entity));
    }

    @Override
    public void editCountry(String code, CountryDto dto) {
        CountryEntity entity = repository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Country not found: " + code));

        entity.setName(dto.getName());
        repository.save(entity);
    }

    private CountryDto toDto(CountryEntity e) {
        return CountryDto.builder()
                .id(e.getId())
                .name(e.getName())
                .code(e.getCode())
                .build();
    }
}
