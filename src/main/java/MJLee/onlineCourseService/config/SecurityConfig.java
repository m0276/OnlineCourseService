package MJLee.onlineCourseService.config;

import MJLee.onlineCourseService.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
//@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

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
  public SecurityFilterChain securityFilterChain(HttpSecurity http){
  try{
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/", "/onlineClass", "/onlineClass/login", "/onlineClass/join")
            .permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/onlineClass/login")
            .loginProcessingUrl("/onlineClass/login")
            .defaultSuccessUrl("/onlineClass", true)
            .permitAll()
        )
        .logout(LogoutConfigurer::permitAll);

    return http.build();
  } catch (Exception e){
    e.printStackTrace();
  }


    return null;
  }

//  @Bean
//  public HttpFirewall defaultHttpFirewall(){
//    return new DefaultHttpFirewall();
//  }
}