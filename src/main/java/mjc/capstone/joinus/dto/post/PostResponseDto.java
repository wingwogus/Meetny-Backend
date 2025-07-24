package mjc.capstone.joinus.dto.post;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.dto.auth.SimpleMemberInfoDto;
import mjc.capstone.joinus.dto.tag.TagDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostResponseDto {
    @Schema(description = "게시물 ID", example = "1")
    private Long id;

    @Schema(description = "게시물 제목")
    private String title;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물에 첨부된 사진 URL")
    private String photo;

    @Schema(description = "작성자")
    private SimpleMemberInfoDto author;

    @Schema(description = "동행 시간")
    private LocalDateTime meetingTime;

    @Schema(description = "모임 주소 정보")
    private Address address;

    @Schema(description = "모집 상태")
    private String status;

    @Schema(description = "게시물 태그")
    private TagDto postTag;

    @Schema(description = "게시물 생성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "게시물 최종 수정 시간")
    private LocalDateTime lastModifiedAt;

    @Schema(description = "현재 로그인한 사용자가 이 게시물을 좋아요 눌렀는지 여부")
    private boolean liked;

    @Schema(description = "게시물의 좋아요 개수")
    private int likesCount;

    @Schema(description = "게시물 조회수")
    private int viewCount;

    public static PostResponseDto from(Post post, boolean liked) {
        SimpleMemberInfoDto author = SimpleMemberInfoDto.from(post.getAuthor());

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .photo(post.getPhoto())
                .author(author)
                .meetingTime(post.getMeetingTime())
                .address(post.getAddress())
                .status(post.getStatus().getDescription())
                .postTag(TagDto.builder()
                        .tagId(post.getTag().getId())
                        .tagName(post.getTag().getTagName())
                        .build())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .liked(liked)
                .likesCount(post.getPostLikes().size())
                .viewCount(post.getViewCount())
                .build();
    }
}
