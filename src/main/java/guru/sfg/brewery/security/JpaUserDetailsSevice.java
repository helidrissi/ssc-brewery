package guru.sfg.brewery.security;

import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsSevice implements UserDetailsService {

    private final UserRepository userRepository;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw  new UsernameNotFoundException("User name: " + username + " not found");
        });


    }


}
