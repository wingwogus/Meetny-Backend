package mjc.capstone.joinus.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserTagDto {
    private String action;
    private String username;
    private List<TagDto> tags;
}
