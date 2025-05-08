package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("M")
@RequiredArgsConstructor
public class Movie extends Tag {
    
    public Movie(String tagName, String color) {
        super(tagName, color);
    }
}
