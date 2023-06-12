package ru.job4j.accidents.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import ru.job4j.accidents.util.PassEncoderHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PassEncoderHandler passwordEncoder;
    private final DataSource ds;

    /*
     * Метод configure(auth) содержит описание, как искать пользователей.
     * В этом примере мы загружаем их в память.
     * У каждого пользователя есть роль. По роли мы определяем,
     * что пользователь может делать.
     * @param auth
     * @throws Exception
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(ds)
                .withUser(User.withUsername("user")
                        .password(passwordEncoder.passwordEncoder().encode("123456"))
                        .roles("USER"));
    }

    /*
     * Метод configure(http) содержит описание доступов
     * и конфигурирование страницы входа в приложение.
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /*
                 * - ссылки, которые доступны всем.
                 */
                .antMatchers("/login")
                .permitAll()
                /*
                 * - ссылки доступны только пользователем с ролями ADMIN, USER.
                 */
                .antMatchers("/**")
                .hasAnyRole("ADMIN", "USER")
                .and()
                /*
                 * Настройка формы авторизации.
                 */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

}