package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.tag.TagResponseDto;
import mjc.capstone.joinus.repository.TagRepository;
import mjc.capstone.joinus.service.inf.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    // 전체 태그 조회
    @Override
    public List<TagResponseDto> findAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponseDto> dtos = tags.stream()
                .map(tag -> new TagResponseDto(
                        tag.getId(),
                        tag.getTagName(),
                        tag.getColor(),
                        tag.getClass().getSimpleName()
                ))
                .collect(Collectors.toList());
        return dtos;
    }

    // 카테고리별 태그 조회
    @Override
    public List<TagResponseDto> findTagsByCategory(String category) {
        List <Tag> tags = switch (category) {
            case "Concert" -> tagRepository.findConcertTags();
            case "Sports" -> tagRepository.findSportsTags();
            case "Culture" -> tagRepository.findCulturalTags();
            case "Movie" -> tagRepository.findMovieTags();
            default -> tagRepository.findAll();
        };
        return tags.stream()
                .map(tag -> new TagResponseDto(
                        tag.getId(),
                        tag.getTagName(),
                        tag.getColor(),
                        tag.getClass().getSimpleName()
                ))
                .collect(Collectors.toList());
    }
}
