package guru.sfg.brewery.security.listners;


import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Component
@Slf4j
public class AuthenticationFailureListner {

    private final LoginFailureRepository loginFailureRepository;
    private final UserRepository userRepository;

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event){

        log.debug("Loged IN Failed");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken){

            LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof String){
                log.debug("Attempted Username: " + token.getPrincipal());
                builder.username((String) token.getPrincipal());
                userRepository.findByUsername((String) token.getPrincipal()).ifPresent(builder::user);
            }
            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("SourceIp :"+details.getRemoteAddress());
                builder.sourceIp(details.getRemoteAddress());
            }

            LoginFailure loginFailure = loginFailureRepository.save(builder.build());

            log.debug("Loged In failed Id" +loginFailure.getId());

            if(loginFailure.getUser() != null){
                lockUserAccout(loginFailure.getUser());
            }
        }
    }

    private void lockUserAccout(User user) {
        List<LoginFailure> failures = loginFailureRepository.findAllByUserAndCreatedDateIsAfter(user, Timestamp.valueOf(LocalDateTime
        .now().minusDays(1)));

        if (failures.size() > 3){
            log.debug("Locking User Account :");
            user.setAccountNonLocked(false);
            userRepository.save(user);

        }
    }
}
