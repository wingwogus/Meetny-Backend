package mjc.capstone.joinus.dto;

import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.tags.PostTag;

import java.time.LocalDateTime;

public class PostResponseDto {
    private String title;

    private String content;

    private String photo;

    private LocalDateTime meetingTime;

    private Address address;

    private PostTag postTag;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
