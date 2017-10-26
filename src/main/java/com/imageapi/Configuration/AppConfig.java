package com.imageapi.Configuration;

import java.util.Properties;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.imageapi.Util.ArrayUtil;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages ="com.imageapi")
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig  extends WebMvcConfigurerAdapter implements ApplicationContextAware{
	@Autowired
    private Environment environment;
	private ApplicationContext applicationContext;
 
    /*public ThymeleafTextTemplates(TemplateEngine textTemplateEngine) {
        this.textTemplateEngine = textTemplateEngine;
    }*/
	
	 public void setApplicationContext(ApplicationContext applicationContext) {
	        this.applicationContext = applicationContext;
	    }
	
	
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		    registry.addResourceHandler("swagger-ui.html")
		      .addResourceLocations("classpath:/META-INF/resources/");
		 
		    registry.addResourceHandler("/webjars/**")
		      .addResourceLocations("classpath:/META-INF/resources/webjars/");
		}
	 
	 @Bean(name = "multipartResolver")
	    public CommonsMultipartResolver multipartResolver() {
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	        multipartResolver.setMaxUploadSize(10485760);
	        return multipartResolver;
	    }
	
	  
	 @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan(new String[] { "com.imageapi.Objects" });
	        sessionFactory.setHibernateProperties(hibernateProperties());
	        return sessionFactory;
	     }
	    
	     
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
	        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
	        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
	        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
	        return dataSource;
	    }
 
	    private Properties hibernateProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
	        return properties;        
	    }
	     
	    @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(SessionFactory s) {
	       HibernateTransactionManager txManager = new HibernateTransactionManager();
	       txManager.setSessionFactory(s);
	       return txManager;
	    }
	    
	    
	    /*Email Configuration ..*/
	    @Bean
	    public JavaMailSenderImpl javaMailSenderImpl() {
	        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
	        mailSenderImpl.setHost(environment.getProperty("smtp.host"));
	        mailSenderImpl.setPort(environment.getProperty("smtp.port", Integer.class));
	        mailSenderImpl.setProtocol(environment.getProperty("smtp.protocol"));
	        mailSenderImpl.setUsername(environment.getProperty("smtp.username"));
	        mailSenderImpl.setPassword(environment.getProperty("smtp.password"));
	        final Properties javaMailProps = new Properties();
	        javaMailProps.put("mail.smtp.auth", true);
	        javaMailProps.put("mail.smtp.starttls.enable", true);
	        mailSenderImpl.setJavaMailProperties(javaMailProps);
	        return mailSenderImpl;
	    }
	    
/*	    thymeleaf-spring  Configuration */	
	    @Bean
	    public ViewResolver htmlViewResolver() {
	        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
	        resolver.setContentType("text/html");
	        resolver.setCharacterEncoding("UTF-8");
	        resolver.setViewNames(ArrayUtil.array("*.html"));
	        return resolver;
	    }
	     
	    @Bean
	    public ViewResolver javascriptViewResolver() {
	        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	        resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
	        resolver.setContentType("application/javascript");
	        resolver.setCharacterEncoding("UTF-8");
	        resolver.setViewNames(ArrayUtil.array("*.js"));
	        return resolver;
	    }
	     
	    @Bean
	    public ViewResolver plainViewResolver() {
	        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	        resolver.setTemplateEngine(templateEngine(plainTemplateResolver()));
	        resolver.setContentType("text/plain");
	        resolver.setCharacterEncoding("UTF-8");
	        resolver.setViewNames(ArrayUtil.array("*.txt"));
	        return resolver;
	    }
	    @Bean
	    @Description("Thymeleaf View Resolver")
	    public ThymeleafViewResolver viewResolver() {
	        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	        viewResolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
	        viewResolver.setOrder(1);
	        return viewResolver;
	    }
	    
	    private TemplateEngine templateEngine(ITemplateResolver templateResolver) {
	        SpringTemplateEngine engine = new SpringTemplateEngine();
	        engine.setTemplateResolver(templateResolver);
	        return engine;
	    }
	    private ITemplateResolver htmlTemplateResolver() {
	        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	        resolver.setApplicationContext(applicationContext);
	        resolver.setPrefix("/WEB-INF/views/");
	        resolver.setCacheable(false);
	        resolver.setTemplateMode(TemplateMode.HTML);
	        return resolver;
	    }
	         
	    private ITemplateResolver javascriptTemplateResolver() {
	        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	        resolver.setApplicationContext(applicationContext);
	        resolver.setPrefix("/WEB-INF/js/");
	        resolver.setCacheable(false);
	        resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
	        return resolver;
	    }
	         
	    private ITemplateResolver plainTemplateResolver() {
	        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	        resolver.setApplicationContext(applicationContext);
	        resolver.setPrefix("/WEB-INF/txt/");
	        resolver.setCacheable(false);
	        resolver.setTemplateMode(TemplateMode.TEXT);
	        return resolver;
	    }
	    
	    
}
