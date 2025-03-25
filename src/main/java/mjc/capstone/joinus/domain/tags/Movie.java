package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("M")
@AllArgsConstructor
public class Movie extends Tag {
}
