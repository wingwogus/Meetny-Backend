package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.tag.TagDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query("SELECT new mjc.capstone.joinus.dto.tag.TagDto(t.id, t.tagName) FROM Tag t")
    List<TagDto> findAllTagDtos();


    @Query("SELECT t FROM Tag t WHERE TYPE(t) = mjc.capstone.joinus.domain.tags.Concert")
    List<Tag> findConcertTags();

    @Query("SELECT t FROM Tag t WHERE TYPE(t) = mjc.capstone.joinus.domain.tags.Culture")
    List<Tag> findCulturalTags();

    @Query("SELECT t FROM Tag t WHERE TYPE(t) = mjc.capstone.joinus.domain.tags.Movie")
    List<Tag> findMovieTags();

    @Query("SELECT t FROM Tag t WHERE TYPE(t) = mjc.capstone.joinus.domain.tags.Sports")
    List<Tag> findSportsTags();
}
