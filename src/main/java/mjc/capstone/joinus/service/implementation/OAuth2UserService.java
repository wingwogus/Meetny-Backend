package mjc.capstone.joinus.service.implementation;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Role;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);
        String provider = request.getClientRegistration().getRegistrationId();
        String email = extractEmail(provider, user.getAttributes());

        Member member = memberRepository.findByUsername(email).orElse(null);

        if (member == null) {
            member = Member.builder()
                    .username(email)
                    .nickname(null) // 일단 null
                    .phone(null)
                    .gender(null)
                    .address(Address.builder()
                            .city("입력 필요")
                            .town("입력 필요")
                            .street("입력 필요")
                            .build())
                    .profileImg("https://picsum.photos/200/300")
                    .role(Role.USER)
                    .password("oauth2user")
                    .credibility(45.0)
                    .build();
            // 저장은 하지 않고 추가정보 입력 후 저장
            member = memberRepository.save(member);
        }

        // Copy attributes to a mutable map to avoid UnsupportedOperationException
        Map<String, Object> attributes = new HashMap<>(user.getAttributes());
        attributes.put("email", email);

        return new CustomUserDetails(member);
    }

    private String extractEmail(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "google" -> (String) attrs.get("email");
            case "kakao" -> {
                Object accountObj = attrs.get("kakao_account");
                if (accountObj instanceof Map<?, ?> kakaoMap) {
                    Object emailObj = kakaoMap.get("email");
                    if (emailObj instanceof String email) {
                        yield email;
                    } else {
                        // 이메일이 없으면 임시 이메일 생성
                        Object idObj = attrs.get("id");
                        if (idObj != null) {
                            String fakeEmail = "kakao_" + idObj + "@auth.com";
                            System.out.println("📛 [카카오] 이메일 없음 → 임시 이메일 사용: " + fakeEmail);
                            yield fakeEmail;
                        }
                    }
                }
                System.out.println("⚠️ [카카오] kakao_account 없음 또는 형식 오류: " + attrs);
                throw new OAuth2AuthenticationException("카카오 이메일 정보가 없습니다.");
            }
            case "naver" -> (String) ((Map<String, Object>) attrs.get("response")).get("email");
            default -> throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + provider);
        };
    }
}