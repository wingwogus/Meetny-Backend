package mjc.capstone.joinus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.domain.tags.UserTag;
import mjc.capstone.joinus.dto.UserDetailDto;
import mjc.capstone.joinus.repository.FollowRepository;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.repository.TagRepository;
import mjc.capstone.joinus.repository.UserTagRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyPageServiceIml implements MyPageService{
    private final MemberRepository memberRepository;
    private final UserTagRepository userTagRepository;
    private final FollowRepository followRepository;

    @Override
    public String profileEdit(String url, Member member) {
        member.setProfileImg(url);
        memberRepository.save(member);
        return url;
    }

    @Override
    public void tagAdd(Tag tag, Member member) {
        UserTag userTag = userTagRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("조회된 태그가 없습니다."));
        List<Tag> findTag = userTag.getTags();
        findTag.add(tag);
        userTag.setTags(findTag);
        userTagRepository.save(userTag);
    }

    @Override
    public void tagRemove(Tag tag, Member member) {
        UserTag userTag = userTagRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("조회된 태그가 없습니다."));
        List<Tag> findTag = userTag.getTags();
        findTag.remove(tag);
        userTag.setTags(findTag);
        userTagRepository.save(userTag);
    }

    @Override
    public UserDetailDto findMember(String username) {
        return memberRepository.findByUsername(username)
                .map(member -> new UserDetailDto(
                        member.getNickname(),
                        member.getMail(),
                        member.getProfileImg(),
                        member.getPassword(),
                        member.getPhone()
                ))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void findFollowers(User user) {

    }

    @Override
    public void findFollowing(User user) {

    }
}
