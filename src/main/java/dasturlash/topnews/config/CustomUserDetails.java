package dasturlash.topnews.config;

import dasturlash.topnews.entity.Profile;
import dasturlash.topnews.entity.ProfileRoleEntity;
import dasturlash.topnews.enums.ProfileStatus;
import dasturlash.topnews.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {
    Integer id;
    String name;
    String username;
    String password;
    ProfileStatus status;
    ProfileRoleEntity role;

    public CustomUserDetails(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.password = profile.getPassword();
        this.username = profile.getEmail();
        this.status = profile.getStatus();
        this.role = profile.getRoles().get(0);
    }

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
        return status == ProfileStatus.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
