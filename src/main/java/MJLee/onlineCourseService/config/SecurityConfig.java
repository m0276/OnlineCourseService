package MJLee.onlineCourseService.config;

import MJLee.onlineCourseService.service.PrincipalDetailService;
import MJLee.onlineCourseService.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig{

  PrincipalDetailService detailService;

  @Autowired
  public SecurityConfig(PrincipalDetailService detailService) {
    this.detailService = detailService;
  }

  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers("/", "/onlineClass", "/onlineClass/login", "/onlineClass/join")
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    try {
      http
          .csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(requests -> requests
              .requestMatchers("/onlineClass/login", "/onlineClass/join").hasRole("VISITOR")
              .requestMatchers("/", "/onlineClass").hasAnyRole("USER", "VISITOR")
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
              .loginPage("/onlineClass/login")
              .loginProcessingUrl("/onlineClass/login")
              .defaultSuccessUrl("/onlineClass", true)
              .permitAll()
          )
          .logout(LogoutConfigurer::permitAll);

      http.authenticationManager(authenticationManager(http));

      return http.build();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(detailService);
    return authenticationManagerBuilder.build();
  }

}