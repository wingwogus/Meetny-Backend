package mjc.capstone.joinus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMetaResponseDto {
    private String companionName;
    private String companionImageUrl;

    private String title;
    private String description;
    private String location;
    private String date;
    private String imageUrl;

    public static ReviewMetaResponseDto from(Post post) {
        return ReviewMetaResponseDto.builder()
                .companionName(post.getAuthor().getNickname())
                .companionImageUrl(post.getAuthor().getProfileImg())
                .title(post.getTitle())
                .description(post.getContent())
                .location(post.getAddress().getTown())
                .date(post.getMeetingTime().toLocalDate().toString())
                .imageUrl(post.getPhoto())
                .build();
    }
}

