package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("Concert")
@RequiredArgsConstructor
public class Concert extends Tag {
    public Concert(String tagName, String color) {
        super(tagName, color);
    }
}
