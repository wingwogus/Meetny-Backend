package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.domain.tags.MemberTag;
import mjc.capstone.joinus.dto.tag.TagResponseBasicDto;
import mjc.capstone.joinus.dto.tag.TagResponseDto;
import mjc.capstone.joinus.dto.mypage.MyPageDto;
import mjc.capstone.joinus.exception.ErrorCode;
import mjc.capstone.joinus.exception.ImageSaveFailedException;
import mjc.capstone.joinus.exception.InvalidImageException;
import mjc.capstone.joinus.exception.NotFoundMemberException;
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

    @Override
    public String profileEdit(String url, String username) {
        if (!(url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg"))) {
            throw new InvalidImageException();
        }
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(NotFoundMemberException::new);
        member.setProfileImg(url);
        Member saved = memberRepository.save(member);

        if (saved.getProfileImg() == null || saved.getProfileImg().isBlank()) {
            throw new ImageSaveFailedException();
        }
        return url;
    }

    @Override
    public List<TagResponseDto> findusertags(Member member) {
        return userTagRepository.findTagsByMember(member);
    }

    @Override
    public List<TagResponseBasicDto> findBasicUserTags(Member member) {
        return userTagRepository.findBasicTagsByMember(member);
    }

    @Override
    public List<TagResponseDto> findAlltags(Member member) {
        // 사용자가 가진 태그 ID들 가져오기
        List<Long> userTagIds = userTagRepository.findTagIdsByMember(member);

        // 전체 태그 중 사용자가 가진 태그는 checked=true, 아닌 건 false로 구성
        return tagRepository.findAll().stream()
                .map(tag -> new TagResponseDto(tag.getId(), tag.getTagName(), tag.getColor(),tag.getClass().getSimpleName(),userTagIds.contains(tag.getId())))
                .toList();
    }

    @Override
    public void tagAdd(List<TagResponseDto> tags, Member member) {
        List<Long> tagIds = tags.stream().map(TagResponseDto::getId).toList();

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
    public void tagRemove(List<TagResponseDto> tags, Member member) {
        List<Long> tagIdsToRemove = tags.stream().map(TagResponseDto::getId).toList();
        List<MemberTag> memberTagsToDelete = userTagRepository.findByMemberAndTagIds(member, tagIdsToRemove);

        userTagRepository.deleteAll(memberTagsToDelete);

        if (member.getMemberTag() != null) {
            member.getMemberTag().removeIf(mt -> tagIdsToRemove.contains(mt.getTags().getId()));
        }
    }


    @Override
    public MyPageDto findMemberDto(Member m) {

        return memberRepository.findByUsername(m.getUsername())
                .map(member -> MyPageDto.builder()
                        .nickname(member.getNickname())
                        .profilePic(member.getProfileImg())
                        .password(member.getPassword())
                        .phone(member.getPhone())
                        .tags(findBasicUserTags(m))
                        .followerCount(findFollowers(m))
                        .followingCount(findFollowing(m))
                        .build()
                )
                .orElseThrow(NotFoundMemberException::new);
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
}
