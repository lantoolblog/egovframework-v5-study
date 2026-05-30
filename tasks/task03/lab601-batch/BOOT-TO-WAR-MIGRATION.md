# Spring Boot 제거 및 Tomcat WAR 배포 방식으로 전환

## 개요

Spring Boot 기반 JAR 패키징에서 Boot를 제거하고, eGovFrame Web Parent 기반의 전통적인 WAR 배포 방식으로 변경하였다.
Thymeleaf 템플릿은 그대로 유지하되 Boot 자동설정 대신 수동 설정으로 전환하였다.
web.xml 대신 WebApplicationInitializer를 사용하여 서블릿 컨텍스트를 Java 코드로 설정한다.

---

## 삭제된 파일

- EgovBootApplication.java : Spring Boot 진입점 (@SpringBootApplication, main())
- EgovBootInitialization.java : Boot용 MVC 설정 ("/" → "/batchList.do" 리다이렉트)
- application.yml : Boot 전용 설정 (server.port, thymeleaf, batch.job.enabled 등)

---

## 추가된 파일

- EgovWebApplicationInitializer.java : WebApplicationInitializer 구현. 필터, Root Context, DispatcherServlet을 Java 코드로 설정
- src/main/webapp/WEB-INF/web.xml : 최소 구성 (welcome-file, login-config, session, error-page만 선언. 실제 설정은 EgovWebApplicationInitializer에서 처리)
- src/main/webapp/index.html : batchList.do로 리다이렉트 (meta http-equiv="refresh")
- src/main/webapp/common/error.jsp : 범용 에러 JSP (현재 web.xml의 error-page와는 직접 연결되지 않음. web.xml은 /code404.jsp, /code500.jsp를 참조하지만 두 파일은 추후 추가 예정. Throwable 등 예외는 EgovWebMvcConfiguration의 SimpleMappingExceptionResolver에서 Thymeleaf 뷰로 처리)

---

## 수정된 파일

### pom.xml

- artifactId : egovframe-boot-batch-file-web → lab601-batch, version 5.0.0 → 1.0.0, name 동기화
- packaging : jar → war
- parent : egovframe-boot-starter-parent → egovframe-web-config-parent 5.0.0
- relativePath 추가
- Spring Boot 의존성 전부 제거
  - spring-boot-starter, spring-boot-starter-web, spring-boot-starter-batch
  - spring-boot-starter-thymeleaf, spring-boot-starter-log4j2
  - spring-boot-autoconfigure
- Template에서 명시되었던 Spring / Jakarta / JSP 의존성 제거 (web-config-parent에서 상속되어 불필요)
  - spring-context, spring-webmvc, spring-jdbc, spring-tx
  - spring-batch-core, spring-batch-infrastructure
  - jakarta.annotation-api, jakarta.persistence-api, jakarta.servlet-api
  - jakarta.servlet.jsp-api, jakarta.servlet.jsp.jstl-api, glassfish jakarta.servlet.jsp.jstl
- 추가된 의존성
  - egovframe-rte-ptl-mvc (HTMLTagFilter 등)
  - thymeleaf-spring6 3.1.3.RELEASE (Boot 없이 Thymeleaf 수동 설정용)
  - log4j-slf4j2-impl (SLF4J 2.x ↔ Log4j2 바인딩. Boot starter-log4j2가 빠진 자리를 채움)
- spring-boot-maven-plugin 제거 → maven-war-plugin 사용
- build에 defaultGoal(install), finalName 추가

### EgovConfigJobLauncher.java

- @EnableBatchProcessing 추가 : StepScope, JobScope 자동 등록 및 Job 자동 Registry 등록
- MapJobRegistry 수동 등록 유지 : @EnableBatchProcessing이 JobRegistrySmartInitializingSingleton을 통해 Job 자동 등록을 처리하지만, SimpleJobOperator 주입을 위해 기존 registry 빈은 그대로 둠

### BatchRunController.java

- 배치 출력 파일 경로 변경
  - Before : System.getProperty("user.dir") → Tomcat에서는 Eclipse 설치 디렉토리를 반환하여 경로 오류
  - After : servletContext.getRealPath("/")에서 .metadata 위치를 역추적하여 프로젝트 루트를 찾고 {프로젝트루트}/target/outputs 사용
  - 워크스페이스가 변경되어도 자동으로 프로젝트 루트 경로를 찾아 출력 디렉토리를 생성
- ServletContext 주입 추가 (@Autowired)
- Job 처리 로직(jobName 분기, 모델 구성, catch 블록)은 Template과 동일하게 유지

### EgovWebMvcConfiguration.java

- Before (Template, Boot 기반) : SimpleMappingExceptionResolver(예외 처리)와 FilterRegistrationBean(CharacterEncodingFilter)만 보유. Thymeleaf/View/정적 리소스는 Boot 자동설정에 위임
- After (lab601, WAR 기반) : Boot 자동설정 공백을 수동으로 채움
  - @EnableWebMvc, @ComponentScan(egovframework.example.bat.web) 추가
  - SpringResourceTemplateResolver (classpath:/templates/, suffix .html, UTF-8)
  - SpringTemplateEngine (SpEL 컴파일러 활성화)
  - ThymeleafViewResolver (UTF-8, order 1)
  - ResourceBundleMessageSource (egovframework/message/message-common)
  - addResourceHandlers (/css/**, /images/**) 추가
  - 예외 리졸버 매핑은 그대로 유지
  - encodingFilterBean은 제거되고 EgovWebApplicationInitializer의 servletContext.addFilter(...)로 이전

---

## WebApplicationInitializer 전환

EgovWebApplicationInitializer.java에서 서블릿 컨텍스트를 설정한다.

- CharacterEncodingFilter (UTF-8, *.do) → servletContext.addFilter("encodingFilter", ...)
- HTMLTagFilter (*.do) → servletContext.addFilter("HTMLTagFilter", ...)
- Root ApplicationContext → AnnotationConfigWebApplicationContext.scan(config, job, database) + ContextLoaderListener
- DispatcherServlet (*.do) → addServlet("action", new DispatcherServlet(...)) + addMapping("*.do")
- RequestContextListener → servletContext.addListener(new RequestContextListener())

web.xml에는 welcome-file, session-config, error-page만 남겨둔다 (Java 코드로 설정 불가한 항목).

---

## Thymeleaf 수동 설정 (EgovWebMvcConfiguration)

Boot 자동설정을 대체하여 수동으로 Thymeleaf를 구성한다.

- SpringResourceTemplateResolver : prefix classpath:/templates/, suffix .html, encoding UTF-8
- SpringTemplateEngine : SpEL 컴파일러 활성화
- ThymeleafViewResolver : charset UTF-8, order 1
- ResourceBundleMessageSource : egovframework/message/message-common
- SimpleMappingExceptionResolver : DataAccessException, TransactionException, EgovBizException 등 매핑
- 정적 리소스 핸들러 : /css/**, /images/**

---

