package mjc.capstone.joinus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.Tag;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostRequestDto {
    private String title;

    private String content;

    private String photo;

    private LocalDateTime meetingTime;

    private Address address;

    private Tag tag;

    public Post toEntity(Member member) {
        Post post = Post.builder()
                .title(this.title)
                .content(this.content)
                .photo(this.photo)
                .meetingTime(this.meetingTime)
                .address(this.address)
                .tag(this.tag)
                .build();

        member.addPost(post);
        return post;
    }

    public void updatePost(Post post) {
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setAddress(this.address);
        post.setMeetingTime(this.meetingTime);
        post.setPhoto(this.photo);
        post.setTag(this.tag);
    }
}
