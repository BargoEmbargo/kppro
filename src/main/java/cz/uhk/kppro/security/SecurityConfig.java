package cz.uhk.kppro.security;

import cz.uhk.kppro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only paths
                        .requestMatchers("/css/**").permitAll() // Allow public access to CSS
                        .anyRequest().authenticated() // Authenticated users can access other paths
                )
                .formLogin((form) -> form
                        .loginPage("/login")// Custom login page
                        .successHandler((request, response, authentication) -> {
                            // Debug: Print user roles
                            authentication.getAuthorities().forEach(authority -> {
                                System.out.println("Authority: " + authority.getAuthority());
                            });

                            // Check user roles
                            String redirectUrl = authentication.getAuthorities().stream()
                                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")) ? "/admin/dashboard" : "/recipes";

                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .csrf((csrf) -> csrf.disable()) // For H2 Console
                .headers((headers) -> headers.frameOptions().disable()); // For H2 Console

        return http.build();
    }







    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/403");
        };
    }
}
