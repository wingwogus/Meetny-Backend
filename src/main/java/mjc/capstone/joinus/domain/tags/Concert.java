package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("C")
@AllArgsConstructor
public class Concert extends Tag {
}
