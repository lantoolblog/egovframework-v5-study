package egovframework.lab.ioc.config;

import egovframework.lab.ioc.service.EmpService;
import egovframework.lab.ioc.service.impl.JavaConfigEmpDAO;
import egovframework.lab.ioc.service.impl.JavaConfigEmpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpConfig {

  // TODO [Step 3-5] EmpConfig 작성
  @Bean
  public JavaConfigEmpDAO javaConfigEmpDAO() {
    return new JavaConfigEmpDAO();
  }

  @Bean(name = "javaConfigEmpService")
  public EmpService javaConfigEmpService() {
    JavaConfigEmpServiceImpl empService = new JavaConfigEmpServiceImpl();
    empService.setEmpDAO(javaConfigEmpDAO());
    return empService;
  }
}
