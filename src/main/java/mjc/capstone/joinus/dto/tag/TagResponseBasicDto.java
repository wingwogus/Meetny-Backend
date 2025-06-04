package mjc.capstone.joinus.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseBasicDto {
    private Long id;
    private String tagName;
    private String color;
    private String category;

    public TagResponseBasicDto(Long id, String tagName, String color, Class<?> categoryClass) {
        this.id = id;
        this.tagName = tagName;
        this.color = color;
        this.category = categoryClass.getSimpleName(); // "Concert", "Festival" 등
    }
}