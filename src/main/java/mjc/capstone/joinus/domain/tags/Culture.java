package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("Exhibition")
@RequiredArgsConstructor
public class Culture extends Tag {

    public Culture(String tagName, String color) {
        super(tagName, color);
    }
}
