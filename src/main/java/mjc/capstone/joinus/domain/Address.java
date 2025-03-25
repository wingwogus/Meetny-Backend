package mjc.capstone.joinus.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String town;
    private String street;
}
