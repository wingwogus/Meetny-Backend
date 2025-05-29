package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("Sports")
@RequiredArgsConstructor
public class Sports extends Tag {

    public Sports(String tagName, String color) {
        super(tagName, color);
    }
}
