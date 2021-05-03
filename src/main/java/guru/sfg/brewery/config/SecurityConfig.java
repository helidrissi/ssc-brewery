package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {

        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests(authorize -> {
                    authorize.antMatchers("/", "/login", "/resources/**", "/webjars/**").permitAll();
                    authorize.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll();
                    authorize.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/**").permitAll();
                }).authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                .password("{ldap}{SSHA}C7tqqcTd/7XtGPvC1ap6ki56l1MsUG0csJ675w==")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{bcrypt}$2a$10$WVqm677KKPKcViR/NB2YDOM73swONmSCy9YP74NwHpilTVPmwmYFW")
                .roles("USER");
    }

    /* @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("spring")
                .password("guru")
                .roles("ADMIN")
                .build();

        UserDetails user =  User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();


        return new InMemoryUserDetailsManager(admin,user);
    }*/
}
