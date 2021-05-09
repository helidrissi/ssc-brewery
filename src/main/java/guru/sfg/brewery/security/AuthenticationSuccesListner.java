package guru.sfg.brewery.security;


import guru.sfg.brewery.domain.security.LoginSucces;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginSuccesRepository;
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
public class AuthenticationSuccesListner {

    private final LoginSuccesRepository loginSuccesRepository;


    @EventListener
    public void listenEvent(AuthenticationSuccessEvent event){

        log.debug("Succes loged IN");

        if(event.getSource() instanceof UsernamePasswordAuthenticationToken){
            LoginSucces.LoginSuccesBuilder builder = LoginSucces.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            if(token.getPrincipal() instanceof User){

                User user = (User) token.getPrincipal();
                builder.user(user);
                log.debug("UserName" +user.getUsername());
            }
            if (token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                builder.sourceIp(details.getRemoteAddress());
                log.debug("IP :"+details.getRemoteAddress());

            }
            LoginSucces loginSucces = loginSuccesRepository.save(builder.build());

            log.debug("Login Succes saved Id : " +loginSucces.getId());
        }
    }
}
