package guru.qa.country.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import quru.qa.xml.country.Country;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CountryJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("code")
        String code
) {

    public @Nonnull Country toJaxb() {
        Country jaxb = new Country();
        jaxb.setId(id != null ? id.toString() : null);
        jaxb.setName(name);
        jaxb.setCode(code);
        return jaxb;
    }

    public static @Nonnull CountryJson fromJaxb(@Nonnull Country jaxb) {
        return new CountryJson(
                jaxb.getId() != null ? UUID.fromString(jaxb.getId()) : null,
                jaxb.getName(),
                jaxb.getCode()
        );
    }
}
