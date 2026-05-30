package egovframework.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

/**
 * EgovWebApplicationInitializer 클래스
 * <Notice>
 *     Spring MVC 설정을 Java Config 방식으로 처리한다.
 *     WebApplicationInitializer 기능으로 처리
 *
 * <pre>
 *  == 개정이력(Modification Information) ==
 *
 *  수정일        수정자           수정내용
 *  ----------  -----------    ---------------------------
 *  2026.04.19  신용호           최초 생성
 *
 * </pre>
 */

public class EgovWebApplicationInitializer implements WebApplicationInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovWebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		LOGGER.debug("EgovWebApplicationInitializer START ============================================");

		//-------------------------------------------------------------
		// Spring CharacterEncodingFilter 설정
		//-------------------------------------------------------------
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",
				new org.springframework.web.filter.CharacterEncodingFilter());
		encodingFilter.setInitParameter("encoding", "UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, false, "*.do");

		//-------------------------------------------------------------
		// HTMLTagFilter 설정
		//-------------------------------------------------------------
		FilterRegistration.Dynamic htmlTagFilter = servletContext.addFilter("HTMLTagFilter",
				new org.egovframe.rte.ptl.mvc.filter.HTMLTagFilter());
		htmlTagFilter.addMappingForUrlPatterns(null, false, "*.do");

		//-------------------------------------------------------------
		// Root Application Context 설정 (Java Config)
		//-------------------------------------------------------------
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.scan(
				"egovframework.example.bat.config",
				"egovframework.example.bat.job",
				"egovframework.example.bat.database"
		);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		//-------------------------------------------------------------
		// Dispatcher Servlet 설정 (Java Config)
		//-------------------------------------------------------------
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(EgovWebMvcConfiguration.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("action",
				new DispatcherServlet(dispatcherContext));
		dispatcher.addMapping("*.do");
		dispatcher.setLoadOnStartup(1);

		//-------------------------------------------------------------
		// Spring RequestContextListener 설정
		//-------------------------------------------------------------
		servletContext.addListener(new org.springframework.web.context.request.RequestContextListener());

		LOGGER.debug("EgovWebApplicationInitializer END ============================================");
	}

}
