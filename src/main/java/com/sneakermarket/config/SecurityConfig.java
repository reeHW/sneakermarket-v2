package com.sneakermarket.config;

import com.sneakermarket.config.auth.CustomUserDetailService;
import com.sneakermarket.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) // 컨트롤러 접근 전에 낚아챔. 특정 주소 접근 시 권한 및 인증 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customMemberDetailService;
    private final CustomOAuth2UserService customOAuth2UserService;

    private final AuthenticationFailureHandler loginFailHandler;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customMemberDetailService).passwordEncoder(encoder());
    }

    /* 정적 리소스 관련 설정은 무시 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/images/**", "/js/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/post/view", "/api/posts/**", "/api/file/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/loginProc")
                    .defaultSuccessUrl("/", true)
                    .failureHandler(loginFailHandler)
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint() // OAuth2 로그인 성공 후 가져올 설정들
                            .userService(customOAuth2UserService); //서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시

    }

}
