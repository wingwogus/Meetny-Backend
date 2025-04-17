package mjc.capstone.joinus.service;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Follow;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public void follow(Member fromUser, Member toUser) {
        if (fromUser.equals(toUser)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        followRepository.findByFromMemberAndToMember(fromUser, toUser)
                .ifPresent(f -> { throw new IllegalStateException("이미 팔로우한 유저입니다."); });

        Follow follow = new Follow();
        follow.setFromMember(fromUser);
        follow.setToMember(toUser);
        followRepository.save(follow);
    }

    @Override
    public void unfollow(Member fromUser, Member toUser) {
        Follow follow = followRepository.findByFromMemberAndToMember(fromUser, toUser)
                .orElseThrow(() -> new NoSuchElementException("팔로우 관계가 존재하지 않습니다."));
        followRepository.delete(follow);
    }

    @Override
    public List<Member> getFollowings(Member user) {
        return followRepository.findByFromMember(user).stream().map(Follow::getToMember).collect(Collectors.toList());
    }

    @Override
    public List<Member> getFollowers(Member user) {
        return followRepository.findByToMember(user).stream().map(Follow::getFromMember).collect(Collectors.toList());
    }

    @Override
    public long countFollowings(Member user) {
        return followRepository.countByFromMember(user);
    }

    @Override
    public long countFollowers(Member user) {
        return followRepository.countByToMember(user);
    }
}
