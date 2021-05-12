package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringSecurityBeans {

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
