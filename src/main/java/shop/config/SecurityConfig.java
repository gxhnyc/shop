package shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity // 开启WebSecurity组件功能，获得了SpringSecurityFilterChain组件
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级别权限检查支持（@preAuthorize）

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		http.authorizeRequests()// 请求页面配置授权
				.antMatchers("/**").permitAll()// / 页面允许所有用户访问，包括匿名用户
				.antMatchers("/uc/**").authenticated()// 其他页面仅限于用户登录后访问
				.and()
					.sessionManagement()// 配置会话管理
					.maximumSessions(1)// 指定每个账号最多同时活跃会话数，1表示同一个账号第二次登录会使得第一次登录失效
					.sessionRegistry(sessionRegistry())// 注入会话注册表
				.and()
				.and()
					.formLogin()// 配置表单登录
					.loginPage("/login")// 指定登录页面的路径：显示表单（自己写） GET /login；提交表单（springsecurity自带） POST /login
					.defaultSuccessUrl("/")// 指定默认登录成功页面（比如直接访问登录页面，而不是其他需要登录的页面触发的）
				.and()
					.logout()
					.logoutSuccessUrl("/")
				.and()
					.rememberMe()
					.tokenValiditySeconds(7 * 24 * 3600)
					.key("test123456") // 避免开发中需要反复登录，上线时注释掉，默认为随机数会导致服务器重启之后重启前的记住我cookie失效
					.userDetailsService(userDetailsService);
	}

	@Bean // 维护Map<UserDetailsImpl,SessionIdSet>,可以从中获取当前有哪些登录用户及会话
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

}
