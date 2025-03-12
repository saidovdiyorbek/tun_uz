package dasturlash.topnews.config;

import dasturlash.topnews.entity.Profile;
import dasturlash.topnews.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile byEmailAndVisibleTrue = profileRepository.findByEmailAndVisibleTrue(username);

        if (byEmailAndVisibleTrue == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return new CustomUserDetails(byEmailAndVisibleTrue);
    }
}
