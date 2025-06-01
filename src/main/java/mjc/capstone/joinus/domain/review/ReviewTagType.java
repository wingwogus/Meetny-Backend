package mjc.capstone.joinus.domain.review;

import lombok.Getter;

@Getter
public enum ReviewTagType {
    POSITIVE(0.1),
    NEGATIVE(-0.3);

    private final double value;

    ReviewTagType(double value) {
        this.value = value;
    }
}
