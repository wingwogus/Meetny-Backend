package mjc.capstone.joinus.dto;

import jakarta.persistence.*;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.tags.PostTag;

import java.time.LocalDateTime;

public class PostRequestDto {
    private String title;

    private String content;

    private String photo;

    private LocalDateTime meetingTime;

    private Address address;

    private PostTag postTag;
}
