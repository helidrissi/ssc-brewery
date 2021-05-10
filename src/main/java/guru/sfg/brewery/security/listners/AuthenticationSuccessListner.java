package guru.sfg.brewery.security.listners;


import guru.sfg.brewery.domain.security.LoginSuccess;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class AuthenticationSuccessListner {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent event){

        log.debug("Loged IN succed");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken){
            LoginSuccess.LoginSuccessBuilder builder = LoginSuccess.builder();

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof User){
                User user = (User) token.getPrincipal();
                builder.user(user);
                log.debug("UserName :"+user.getUsername());
            }
            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("SourceIp :"+details.getRemoteAddress());
                builder.sourceIp(details.getRemoteAddress());
            }
            LoginSuccess loginSuccess = loginSuccessRepository.save(builder.build());
            log.debug("Login Sucess saved .id" + loginSuccess.getId());
        }
    }
}
