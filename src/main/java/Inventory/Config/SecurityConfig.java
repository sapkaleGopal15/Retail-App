package Inventory.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import Inventory.Services.Implemantation.SecurityCustomUserDetailService;


@Configuration
public class SecurityConfig {
    
    @Autowired
    private SecurityCustomUserDetailService securityCustomUserDetailService;


    // configaration 
    @Bean
    public DaoAuthenticationProvider authenticationProvider() { 

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // configuration

        // urls configure public and private
        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home","/register","/about","/service","/contact").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login
        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.defaultSuccessUrl("/user/dashboard", true);
            formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
