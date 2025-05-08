package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.TagDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query("SELECT new mjc.capstone.joinus.dto.TagDto(t.id, t.tagName) FROM Tag t")
    List<TagDto> findAllTagDtos();
}
