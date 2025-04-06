package mjc.capstone.joinus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.PostTag;

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

    private PostTag postTag;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .photo(post.getPhoto())
                .author(post.getAuthor().getNickname())
                .meetingTime(post.getMeetingTime())
                .address(post.getAddress())
                .postTag(post.getPostTag())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }
}
