package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.tag.TagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    // 전체 태그 조회
    List<TagResponseDto> findAllTags();
    // 카테고리별 태그 조회
    List<TagResponseDto> findTagsByCategory(String category);
}
