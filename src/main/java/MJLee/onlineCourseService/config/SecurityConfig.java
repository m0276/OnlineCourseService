package MJLee.onlineCourseService.config;

import MJLee.onlineCourseService.service.PrincipalDetailService;
import MJLee.onlineCourseService.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
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
        .requestMatchers("/", "/onlineClass", "/onlineClass/loginPage", "/onlineClass/join")
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    try {
      http
          .csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(requests -> requests
              .requestMatchers("onlineClass/loginPage","/onlineClass/login", "/onlineClass/join").hasAnyRole("ANONYMOUS")
              .requestMatchers("/", "/onlineClass","/onlineClass/category/","onlineClass/courseName/","/onlineClass/fee/","onlineClass/week/","/favicon.ico").hasAnyRole("USER","ANONYMOUS")
              .anyRequest().authenticated()
          )
          .securityContext((context) -> context
              .securityContextRepository(new HttpSessionSecurityContextRepository())
          )
          .sessionManagement(session -> session
              .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션이 필요한 경우에만 생성
              .invalidSessionUrl("/login?error=sessionExpired") // 세션이 만료되었을 때 리다이렉트할 URL
              .maximumSessions(1) // 세션이 1개로 제한 (여러 기기에서 로그인 방지)
              .expiredUrl("/login?error=sessionExpired") // 세션 만료 후 리다이렉트될 URL
          )
          .formLogin(form -> form
              .loginPage("/onlineClass/loginPage")
              .loginProcessingUrl("/onlineClass/login")
              .defaultSuccessUrl("/onlineClass", true)
              .permitAll()
              .successHandler((request, response, authentication) -> {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                securityContextRepository().saveContext(SecurityContextHolder.getContext(),request,response);
                response.sendRedirect("/onlineClass");
              })
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

  @Bean
  public SecurityContextHolderStrategy securityContextHolderStrategy() {
    return SecurityContextHolder.getContextHolderStrategy();
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

}