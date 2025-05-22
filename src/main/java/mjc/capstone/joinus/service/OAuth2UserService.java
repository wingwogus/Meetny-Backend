package mjc.capstone.joinus.service;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Role;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);
        String provider = request.getClientRegistration().getRegistrationId();
        String email = extractEmail(provider, user.getAttributes());

        Member member = memberRepository.findByMail(email)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .mail(email)
                                .username(provider + "_" + UUID.randomUUID())
                                .role(Role.USER)
                                .build()
                ));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                user.getAttributes(),
                "mail"
        );
    }

    private String extractEmail(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "google" -> (String) attrs.get("email");
            case "kakao" -> (String) ((Map<String, Object>) attrs.get("kakao_account")).get("email");
            case "naver" -> (String) ((Map<String, Object>) attrs.get("response")).get("email");
            default -> throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + provider);
        };
    }
}