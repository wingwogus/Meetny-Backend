package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("E")
@AllArgsConstructor
public class Exhibition extends Tag {
}
