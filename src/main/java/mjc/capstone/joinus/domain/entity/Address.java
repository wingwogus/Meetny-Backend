package mjc.capstone.joinus.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String town;
    private String street;
}
