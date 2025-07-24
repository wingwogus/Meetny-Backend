package mjc.capstone.joinus.domain.entity;

import lombok.Getter;

@Getter
public enum PostStatus {
    RECRUITING("모집 중"),
    COMPLETED("동행 완료");

    private final String description;

    PostStatus(String description) {
        this.description = description;
    }

}