package mjc.capstone.joinus.domain;

import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @CreatedBy
    private User createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private User lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
