package mjc.capstone.joinus.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto {
    private Long id;
    private String tagName;
    private String color;
    private String category;
}