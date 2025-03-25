package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("S")
@AllArgsConstructor
public class Sports extends Tag {
}
