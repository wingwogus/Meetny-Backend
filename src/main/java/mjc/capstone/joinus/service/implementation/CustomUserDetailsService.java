package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.exception.WithdrawnMemberException;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsernameWithMemberTag(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        // 탈퇴한 멤버라면
        if (member.isDeleted()) {
            throw new WithdrawnMemberException();
        }

        return new CustomUserDetails(member);
    }
}