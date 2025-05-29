package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("E")
@RequiredArgsConstructor
public class Culture extends Tag {

    public Culture(String tagName, String color) {
        super(tagName, color);
    }
}
