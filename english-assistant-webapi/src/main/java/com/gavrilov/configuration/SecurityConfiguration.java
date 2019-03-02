package com.gavrilov.configuration;

import com.gavrilov.commons.CorsFilter;
import com.gavrilov.commons.MyAuthenticationSuccessHandler;
import com.gavrilov.commons.SessionEventListener;
import com.gavrilov.commons.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .rolePrefix("ROLE_")
                .usersByUsernameQuery("SELECT login, password, case enabled when 1 then 'true' else 'false' end FROM user WHERE login = ?")
                .authoritiesByUsernameQuery("SELECT u.login, r.rolename FROM user u join user_role r on u.user_role_id = r.id where u.login = ?")
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests() // позволяет ограничить доступ на основе использования
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER").anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(new SessionFilter(), SessionManagementFilter.class)
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true") // при неудачных попытках авторизации
                .successHandler(successHandler())
                //.defaultSuccessUrl("/user/home")
                .usernameParameter("login")
                .passwordParameter("password")
                .and()
                .logout().deleteCookies("JSESSIONID", "REMEMBER")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                .rememberMe()
                // Ключ для индетификации маркера
                .key("uniqueAndSecret")
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("REMEMBER")
                // Сколько будет  действовать маорке
                .tokenValiditySeconds(86400)
                .and()
                .sessionManagement()
                // при проверке подлинности создается новый сеанс, в который копируется текущей, старый закрывается. По умолчанию включена
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/invalidSession")
                .maximumSessions(2)
                // Без предупреждения закроет сессию. Можно создавать свои обработчики и передат его в метод failureHandler
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/sessionExpired");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new SessionEventListener();
    }

    private AuthenticationSuccessHandler successHandler() {
        return new MyAuthenticationSuccessHandler();
    }
}