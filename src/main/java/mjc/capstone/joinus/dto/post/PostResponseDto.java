package mjc.capstone.joinus.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private String photo;

    private String author;

    private LocalDateTime meetingTime;

    private Address address;

    private Tag postTag;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private boolean liked;

    private int likesCount;

    private int viewCount;

    public static PostResponseDto from(Post post, boolean liked) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .photo(post.getPhoto())
                .author(post.getAuthor().getNickname())
                .meetingTime(post.getMeetingTime())
                .address(post.getAddress())
                .postTag(post.getTag())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .liked(liked)
                .likesCount(post.getPostLikes().size())
                .viewCount(post.getViewCount())
                .build();
    }
}
