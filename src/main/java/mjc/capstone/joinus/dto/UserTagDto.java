package mjc.capstone.joinus.dto;

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
    private String nickname;
    private List<String> tags;
}
