package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.ex.CountryAlreadyExistsException;
import guru.qa.country.ex.NotFoundException;
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
            throw new CountryAlreadyExistsException(
                    "Country with code " + dto.getCode() + " already exists"
            );
        }

        CountryEntity entity = CountryEntity.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .build();

        return toDto(repository.save(entity));
    }

    @Override
    public CountryDto editCountry(String code, CountryDto dto) {
        CountryEntity entity = repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Country not found: " + code));

        entity.setName(dto.getName());

        return toDto(repository.save(entity));
    }

    @Override
    public CountryDto findByCode(String code) {
        return repository.findByCode(code)
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Country not found: " + code));
    }

    private CountryDto toDto(CountryEntity e) {
        return CountryDto.builder()
                .id(e.getId())
                .name(e.getName())
                .code(e.getCode())
                .build();
    }
}