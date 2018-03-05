package sjsu.cmpe275.project.initialize;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import sjsu.cmpe275.project.entity.Role;

/**
 * Created by shiva on 12/19/17.
 */

    //@Configuration
    //@EnableOAuth2Sso
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                        .disable()
                    .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/","/ticket/*")
                        .permitAll()
                    .anyRequest()
                        .authenticated();
        }
        /*
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("user").password("password").roles(Role.USER.name())
                    .and()
                    .withUser("admin").password("admin").roles(Role.USER.name(),Role.ADMIN.name());
        }*/
    }
