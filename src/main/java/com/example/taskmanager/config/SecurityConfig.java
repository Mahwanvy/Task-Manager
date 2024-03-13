package com.example.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.taskmanager.repository.TmUserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
//	@Autowired
//	private CustomAuthenticationManager authenticationProvider;
//	
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider);
//	}
//	
//	@Bean
//	public AuthenticationManager authencationManagerBean() throws Exception{
//		return super.authencationManagerBean();
//	}
	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder())
	 * ; }
	 * 
	 * @Bean public AuthenticationSuccessHandler successHandler() { return new
	 * SimpleUrlAuthenticationSuccessHandler("/dashboard"); }
	 */
	@Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
		/*
		 * @Bean public UserDetailsService userDetailsService() { return new
		 * CustomUserService(); }
		 * 
		 * @Bean public DaoAuthenticationProvider authenticationProvider() {
		 * DaoAuthenticationProvider daoAuthenticationProvider = new
		 * DaoAuthenticationProvider();
		 * daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		 * daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); return
		 * daoAuthenticationProvider ; }
		 */
	 
	 
	 @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			  http 
			  		.csrf((csrf) -> csrf .disable())
			  		.cors(AbstractHttpConfigurer::disable) .headers(httpSecurityHeadersConfigurer
			  				 -> { httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.
			  				  FrameOptionsConfig::disable); })
					
					//.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll()) 
					//.authorizeHttpRequests(auth-> auth .requestMatchers("/user/**").permitAll())
				      .authorizeHttpRequests(auth -> auth
				 			//.requestMatchers("/").permitAll()
				            .anyRequest().permitAll()
				    )
			  		.formLogin((formLogin) -> formLogin
			            .loginPage("/login")
			            .loginProcessingUrl("/process_login")
			            .defaultSuccessUrl("/dashboard")
			            .permitAll()
			        )
			  		.sessionManagement((sessionManagement) -> sessionManagement
				            .maximumSessions(1) 
				            .expiredUrl("/login?expired=true"));
			  		//.authenticationManager(new CustomAuthenticationManager());
			  return http.build(); 
	 }
	 
	 
	/* 
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
			http
			    .csrf((csrf) -> csrf .disable())
			    .authorizeHttpRequests(auth -> auth
			    		.requestMatchers("/").permitAll()
			            .anyRequest().authenticated()
			    )
			    .formLogin((formLogin) -> formLogin
			            .loginPage("/login").permitAll() 
			            .loginProcessingUrl("/loginUser")
			            .defaultSuccessUrl("/dashboard", true) 
			            .failureUrl("/login?error=true")
			    )
			    .logout((logout) -> logout
			    	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
			    	    .logoutSuccessUrl("/login")
			    	    .permitAll()
			    	)
			    
			    .sessionManagement((sessionManagement) -> sessionManagement
			            .maximumSessions(1) // Allow only one session per user
			            .expiredUrl("/login?expired=true") // Enforce session control
			        );
			return http.build();
	    }
 */
	
	/*
	 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 * 
	 * @Bean public UserDetailsService getDetailsService() { return new
	 * CustomUserDetailsService(); }
	 * 
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider daoAuthenticationProvider= new
	 * DaoAuthenticationProvider();
	 * daoAuthenticationProvider.setUserDetailsService(getDetailsService());
	 * daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); return
	 * daoAuthenticationProvider; }
	 */
	
	/*
	  @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	  throws Exception {
	 return http 
			 	.formLogin((formLogin) -> formLogin
	            .loginPage("/login").permitAll() )
			 	.authorizeHttpRequests(auth -> auth
			            .anyRequest().authenticated()
			    )
			 .build(); }
	 
	 */
			 

}
	

