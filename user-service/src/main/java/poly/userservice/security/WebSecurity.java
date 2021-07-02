package poly.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import poly.userservice.service.UserService;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private Environment env;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 권한에 대해서 검증한다
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
//        http.authorizeRequests().antMatchers("/**")
//                .hasIpAddress("127.0.0.1")// <- IP 변경
//                .and()
//                .addFilter(getAuthenticationFilter());
//                .hasIpAddress("172.22.128.1")// <- IP 변경
//                .access("hasIpAddress('192.168.183.1') or hasIpAddress('127.0.0.1')")
//                .antMatchers("/css/**").permitAll()
//                .and()
//                .formLogin() //formLogin() 은 기본값으로 제공되는 로그인 form 말고 자체적으로 제작한 login form 을 쓰겠다는 의미
//                .loginPage("로그인할 페이지 경로" //loginPage() 는 말그대로 로그인할 페이지 url
//                .loginProcessingUrl("로그인을 처리할 url  POST method) // loginProcessingUrl() 은 사용자로 부터 입력받은 로그인을 처리할 url 을 의미한다
//                .permitAll() //모든 유저가 로그인 화면은 볼 수 있게 한다
//                .and()
//                .logout().permitAll();
//                .hasIpAddress("192.168.183.1")
//                .hasIpAddress("127.0.0.1")

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, env);
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    /**
     * 인증에 대해서 검증한다
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

    }
}
