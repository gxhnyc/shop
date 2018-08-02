package shop.config;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

@Configuration
@ComponentScan("shop")
@EnableWebMvc //开户WebMvc基础设施支持
@MapperScan("shop.mapper")
@PropertySource({"classpath:jdbc.properties","classpath:alipay.properties"})
@EnableTransactionManagement//开启spring事务支持（这是一个组件）
public class AppConfig extends WebMvcConfigurerAdapter{
	@Bean
	public DataSource dataSource(Environment env) {
		
		DriverManagerDataSource dmds=new DriverManagerDataSource(
				env.getProperty("jdbc.url"),
				env.getProperty("jdbc.username"),
				env.getProperty("jdbc.password")
				);
		dmds.setDriverClassName(env.getProperty("jdbc.driverClassName"));		
		
		return dmds;		
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
		
		SqlSessionFactoryBean sb=new SqlSessionFactoryBean();
		sb.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		sb.setDataSource(dataSource);
		return sb;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);		
		return passwordEncoder;		
	} 
	
	@Bean
	public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
		
		return new DataSourceTransactionManager(dataSource);
		
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// book-list -> /WEB-INF/jsp/book-list.jsp
		// 对于控制器方法返回的字符串，会用以下规则解析成jsp路径，然后forward
		// 前缀 + 返回字符串 + 后缀 = jsp路径
		//            		前缀       后缀
		registry.jsp("/WEB-INF/jsp/", ".jsp");
	}

	/**
	 * 当springMvc遇到没有控制器映射的路径（webapp下的静态资源-css样式文件）时，
	 * 交给默认的servlet处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
				
		configurer.enable();
	}
	
	/**
	 * 支付宝组件
	 * @return
	 * @throws IOException
	 */
	@Bean
    public AlipayClient alipayClient(Environment env) throws IOException {
        return new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",//支付宝网关，固定
                env.getProperty("alipay.appId"),//	APPID 即创建应用后生成
                FileUtils.readFileToString(new File(env.getProperty("alipay.appPrivateKeyFile")), "UTF-8"),//开发者私钥，由开发者自己生成
                "json",//参数返回格式，只支持json
                "UTF-8",//编码集，支持GBK/UTF-8
                FileUtils.readFileToString(new File(env.getProperty("alipay.alipayPublicKeyFile")), "UTF-8"),//支付宝公钥，由支付宝生成
                env.getProperty("alipay.signType")//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
                );
    }
	
}
