package mjc.capstone.joinus.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto {
    private Long id;
    private String tagName;
    private String color;
    private String category;

    private boolean checked;

    public TagResponseDto(Long id, String tagName, String color, Class category) {
        this.id = id;
        this.tagName = tagName;
        this.color = color;
        this.category = category.getSimpleName();
        this.checked = false;
    }
}