package mjc.capstone.joinus.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.tags.PostTag;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostRequestDto {
    private String title;

    private String content;

    private String photo;

    private LocalDateTime meetingTime;

    private Address address;

    private PostTag postTag;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .photo(this.photo)
                .meetingTime(this.meetingTime)
                .address(this.address)
                .postTag(this.postTag)
                .author(member)
                .build();
    }
}
