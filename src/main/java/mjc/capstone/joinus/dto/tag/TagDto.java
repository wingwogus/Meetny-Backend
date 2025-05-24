package mjc.capstone.joinus.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDto {
    private Long tagId;
    private String tagName;
    private boolean checked;

    public TagDto() {
    }

    public TagDto(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.checked = false;
    }
}
