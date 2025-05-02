package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.domain.tags.MemberTag;
import mjc.capstone.joinus.dto.TagDto;
import mjc.capstone.joinus.dto.MyPageDto;
import mjc.capstone.joinus.repository.*;
import mjc.capstone.joinus.service.inf.MyPageService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyPageServiceIml implements MyPageService {
    private final MemberRepository memberRepository;
    private final UserTagRepository userTagRepository;
    private final FollowRepository followRepository;
    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @Override
    public String profileEdit(String url, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
        member.setProfileImg(url);
        memberRepository.save(member);
        return url;
    }

    @Override
    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }


    @Override
    public List<TagDto> findusertags(Member member) {
        return userTagRepository.findTagsByMember(member);
    }

    @Override
    public List<TagDto> findAlltags(Member member) {
        // 사용자가 가진 태그 ID들 가져오기
        List<Long> userTagIds = userTagRepository.findTagIdsByMember(member);

        // 전체 태그 중 사용자가 가진 태그는 checked=true, 아닌 건 false로 구성
        return tagRepository.findAll().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getTagName(), userTagIds.contains(tag.getId())))
                .toList();
    }

    @Override
    public void tagAdd(List<TagDto> tags, Member member) {
        List<Long> tagIds = tags.stream().map(TagDto::getTagId).toList();

        List<Long> existingTagIds = userTagRepository.findTagIdsByMember(member);

        List<Long> newTagIds = tagIds.stream()
                .filter(tagId -> !existingTagIds.contains(tagId))
                .toList();

        List<Tag> newTags = tagRepository.findAllById(newTagIds);

        List<MemberTag> newMemberTags = newTags.stream()
                .map(tag -> {
                    MemberTag mt = new MemberTag();
                    mt.setMember(member);
                    mt.setTags(tag);
                    member.addMemberTag(mt);
                    return mt;
                }).toList();

        userTagRepository.saveAll(newMemberTags);
    }

    @Override
    public void tagRemove(List<TagDto> tags, Member member) {
        List<Long> tagIdsToRemove = tags.stream().map(TagDto::getTagId).toList();
        List<MemberTag> memberTagsToDelete = userTagRepository.findByMemberAndTagIds(member, tagIdsToRemove);

        userTagRepository.deleteAll(memberTagsToDelete);

        if (member.getMemberTag() != null) {
            member.getMemberTag().removeIf(mt -> tagIdsToRemove.contains(mt.getTags().getId()));
        }
    }


    @Override
    public MyPageDto findMemberDto(Member m) {

        return memberRepository.findByUsername(m.getUsername())
                .map(member -> new MyPageDto(
                        member.getNickname(),
                        member.getMail(),
                        member.getProfileImg(),
                        member.getPassword(),
                        member.getPhone(),
                        this.findusertags(m).stream()
                                .map(TagDto::getTagName)
                                .toList(),
                        this.findFollowers(m),
                        this.findFollowing(m),
                        this.findPosts(m)
                ))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void editPassword(Member member, String oldPassword, String newPassword) {
        if(oldPassword.equals(member.getPassword())) {
            member.setPassword(newPassword);
            memberRepository.save(member);
        }

    }

    @Override
    public Long findFollowers(Member member) {
        return followRepository.countByToMember(member);
    }

    @Override
    public Long findFollowing(Member member) {
        return followRepository.countByFromMember(member);
    }

    @Override
    public List<Post> findPosts(Member member) {
        return postRepository.findByAuthor(member);
    }
}
