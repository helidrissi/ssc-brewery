package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncodingTests {

    static final String PASSWORD= "password";

    @Test
    void bcryptExample(){
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        System.out.println(bcrypt.encode(PASSWORD));
    }

    @Test
    void ldapExample(){
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode("guru"));
        System.out.println(ldap.encode(PASSWORD));

        String encoded =ldap.encode(PASSWORD);

        assertTrue(ldap.matches(PASSWORD,encoded));
    }
    @Test
    void hashingExample(){
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salted = PASSWORD + "ThisIsMySALT";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes(StandardCharsets.UTF_8)));
    }

}
