package companion.challeculum.security;

import companion.challeculum.domains.user.UserRepository;
import companion.challeculum.domains.user.dtos.User;
import companion.challeculum.security.oauth.provider.GoogleUserInfo;
import companion.challeculum.security.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by jonghyeon on 2023/02/13,
 * Package : companion.challeculum.security
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService extends DefaultOAuth2UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        System.out.println(byUsername);
        return new PrincipalDetails(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다.")));
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);

        OAuth2UserInfo info;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            log.info("Google login");
            info = new GoogleUserInfo(oauthUser.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("Not Supported Provider");
        }
        String oauthId = info.getProvider() + "-" + info.getProviderId();

        User user = userRepository.findByOAuthId(oauthId).orElseGet(() ->{
            User _user = User.builder()
                    .oauthId(oauthId)
                    .username(info.getEmail())
                    .password(passwordEncoder.encode(info.getProviderId()))
                    .build();
            userRepository.registerSocialLoginUser(_user);
            return _user;
        });
        return new PrincipalDetails(user, oauthUser.getAttributes());
    }
}
