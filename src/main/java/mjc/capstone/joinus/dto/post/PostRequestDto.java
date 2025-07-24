package mjc.capstone.joinus.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.entity.PostStatus;
import mjc.capstone.joinus.domain.tags.Tag;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostRequestDto {

    @Schema(description = "게시물 제목", example = "콘서트 동행 구해요")
    private String title;

    @Schema(description = "게시물 내용", example = "남자 여자 아무나 상관없습니다")
    private String content;

    @Schema(description = "게시물 사진", example = "photo.png")
    private String photo;

    @Schema(description = "동행 예정 시각", example = "2024.11.11")
    private LocalDateTime meetingTime;

    @Schema(description = "동행 위치", example = "서울시 은평구 응암동")
    private Address address;

    @Schema(description = "태그 ID", example = "3")
    private Long tagId;

    public Post toEntity(Member member, Tag tag) {
        Post post = Post.builder()
                .title(this.title)
                .content(this.content)
                .photo(this.photo)
                .meetingTime(this.meetingTime)
                .address(this.address)
                .tag(tag)
                .status(PostStatus.RECRUITING)
                .build();

        // 연관관계 설정
        post.setAuthor(member);
        return post;
    }

    public void updatePost(Post post, Tag tag) {
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setAddress(this.address);
        post.setMeetingTime(this.meetingTime);
        post.setPhoto(this.photo);
        post.setTag(tag);
    }
}
