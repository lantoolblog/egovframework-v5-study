package egovframework.example;

import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * EgovWebMvcConfiguration 클래스
 * <Notice>
 *     Spring MVC 설정을 Java Config 방식으로 처리한다.
 *     Thymeleaf 뷰 리졸버, 예외 처리, 메시지 소스, 정적 리소스 핸들러를 설정한다.
 *
 * <pre>
 *  == 개정이력(Modification Information) ==
 *
 *  수정일        수정자           수정내용
 *  ----------  -----------    ---------------------------
 *  2025.11.05  신용호           최초 생성 (Boot 기반)
 *  2026.04.18  신용호           Boot 제거, Thymeleaf 수동 설정으로 전환
 *
 * </pre>
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "egovframework.example.bat.web")
public class EgovWebMvcConfiguration implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public EgovWebMvcConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //-------------------------------------------------------------
    // 예외 처리 설정
    //-------------------------------------------------------------
    @Override
    public void configureHandlerExceptionResolvers(@NonNull List<HandlerExceptionResolver> exceptionResolvers) {
        Properties prop = new Properties();
        prop.setProperty("org.springframework.dao.DataAccessException", "cmmn/dataAccessFailure");
        prop.setProperty("org.springframework.transaction.TransactionException", "cmmn/transactionFailure");
        prop.setProperty("org.egovframe.rte.fdl.cmmn.exception.EgovBizException", "cmmn/egovError");
        prop.setProperty("org.springframework.security.AccessDeniedException", "cmmn/egovError");
        prop.setProperty("java.lang.Throwable", "cmmn/bizError");

        SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
        smer.setDefaultErrorView("cmmn/bizError");
        smer.setExceptionMappings(prop);
        exceptionResolvers.add(smer);
    }

    //-------------------------------------------------------------
    // Thymeleaf TemplateResolver 설정
    //-------------------------------------------------------------
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }

    //-------------------------------------------------------------
    // Thymeleaf TemplateEngine 설정
    //-------------------------------------------------------------
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    //-------------------------------------------------------------
    // Thymeleaf ViewResolver 설정
    //-------------------------------------------------------------
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(1);
        return resolver;
    }

    //-------------------------------------------------------------
    // MessageSource 설정
    //-------------------------------------------------------------
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("egovframework/message/message-common");
        return messageSource;
    }

    //-------------------------------------------------------------
    // 정적 리소스 핸들러 설정
    //-------------------------------------------------------------
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    }
}
