package mjc.capstone.joinus.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "email", member.getEmail(),
                "username", member.getUsername(),
                "nickname", member.getNickname(),
                "profileImg", member.getProfileImg() // ✅ 여기가 핵심!
        );
    }

    @Override
    public String getName() {
        return member.getUsername(); // 또는 고유 식별자
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public String getNickname() {return member.getNickname();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}