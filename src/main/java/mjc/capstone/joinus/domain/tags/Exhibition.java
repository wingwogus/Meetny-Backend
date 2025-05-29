package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("Exhibition")
@RequiredArgsConstructor
public class Exhibition extends Tag {

    public Exhibition(String tagName, String color) {
        super(tagName, color);
    }
}
