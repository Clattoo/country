package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import quru.qa.country.*;

import java.util.List;

@Service
public class CountryGrpcService extends CountryServiceGrpc.CountryServiceImplBase {

    private final CountryService countryService;

    public CountryGrpcService(CountryService countryService) {
        this.countryService = countryService;
    }


    @Override
    public void countryByCode(CountryCodeRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryDto dto = countryService.findByCode(request.getCode());

        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(dto.getId().toString())
                        .setName(dto.getName())
                        .setCode(dto.getCode())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void createCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryDto newCountry = countryService.addCountry(
                CountryDto.builder()
                        .name(request.getName())
                        .code(request.getCode())
                        .build()
        );

        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(newCountry.getId().toString())
                        .setName(newCountry.getName())
                        .setCode(newCountry.getCode())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getAllCountries(GetAllCountriesRequest request, StreamObserver<GetAllCountriesResponse> responseObserver) {
        List<CountryDto> countries = countryService.getAllCountries();

        GetAllCountriesResponse.Builder builder = GetAllCountriesResponse.newBuilder();
        for (CountryDto dto : countries) {
            builder.addCountries(
                    CountryResponse.newBuilder()
                            .setId(dto.getId().toString())
                            .setName(dto.getName())
                            .setCode(dto.getCode())
                            .build()
            );
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
            CountryDto dto = CountryDto.builder()
                    .name(request.getName())
                    .build();

            CountryDto updated = countryService.editCountry(request.getCode(), dto);

            responseObserver.onNext(
                    CountryResponse.newBuilder()
                            .setId(updated.getId().toString())
                            .setName(updated.getName())
                            .setCode(updated.getCode())
                            .build()
            );
            responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> createCountryStream(StreamObserver<CountryAddCountResponse> responseObserver) {
        return new StreamObserver<>() {
            int count = 0;

            @Override
            public void onNext(CountryRequest country) {
                countryService.addCountry(
                        CountryDto.builder()
                                .name(country.getName())
                                .code(country.getCode())
                                .build()
                );
                count++;
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(
                        CountryAddCountResponse.newBuilder()
                                .setCount(count)
                                .build()
                );
                responseObserver.onCompleted();
            }
        };
    }
}
