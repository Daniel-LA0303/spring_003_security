package com.la.springboot.webapp.configuracion;

import com.la.springboot.webapp.seguridad.CustomUserDetailsService;
import com.la.springboot.webapp.seguridad.JwtAuthenticationEntryPonit;
import com.la.springboot.webapp.seguridad.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//estas configuraciones son para habilitar la seguridad en la aplicacion
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; //inyectamos el servicio de usuario

    @Autowired
    private JwtAuthenticationEntryPonit jwtAuthenticationEntryPoint; //inyectamos el servicio de usuario

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**")
                .permitAll()
                .antMatchers("/api/auth/**").permitAll() //permitimos el acceso a /api/auth/** a cualquiera
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(),org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder()); //este metodo es para crear un usuario por defecto
    }

    /*
    //este metodo es para crear un usuario por defecto
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("12345")).roles("USER").build();

        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("12345")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin); //este metodo es para crear un usuario por defecto que esta en memoria
    }*/


}
