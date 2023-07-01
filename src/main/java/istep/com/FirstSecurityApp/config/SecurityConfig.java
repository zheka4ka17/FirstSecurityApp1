package istep.com.FirstSecurityApp.config;

import istep.com.FirstSecurityApp.security.AuthProviderImpl;
import istep.com.FirstSecurityApp.service.PersonDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private final PersonDetailsService usersDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            //Set authentication
            http.getSharedObject(AuthenticationManagerBuilder.class)
                    .userDetailsService(usersDetailsService)
                    .passwordEncoder(getPasswordEncoder());

            // Set authorization
            http. authorizeHttpRequests(auth -> auth
                    //.requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/auth/**", "/error", "/styles/css/**").permitAll()
                    .anyRequest().hasAnyRole("USER", "ADMIN"));

            // Set custom login page
            http.formLogin(formLogin -> formLogin
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/hello", true)
                    .failureUrl("/auth/login?error"));

            // Set logout
            http.logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/auth/login"));

            return http.build();
        }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }



