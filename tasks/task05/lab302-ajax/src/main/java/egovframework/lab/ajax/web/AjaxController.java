package egovframework.lab.ajax.web;

import egovframework.lab.ajax.service.DepartmentService;
import egovframework.lab.ajax.service.EmployeeService;
import egovframework.lab.com.vo.Department;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AjaxController {

  @Autowired
  @Qualifier("employeeService")
  private EmployeeService employeeService;

  @Resource(name = "departmentService")
  private DepartmentService departmentService;

  @RequestMapping(value = "/autoComplete.do")
  public ModelAndView simpleAjax(@RequestParam("keyword") String keyword) throws Exception {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("jsonView");

    String searchName = URLDecoder.decode(keyword, "utf-8");

    // TODO [Step 2-2-2] autoComplete의 리스트를 DB를 통하여 가져온다.
    List<String> nameList = employeeService.getNameListForSuggest(searchName);
    modelAndView.addObject("resultList", nameList);

    return modelAndView;
  }

  // linked selectbox 처리 기능
  @RequestMapping("/autoSelectDept.do")
  protected ModelAndView getDeptInfoforSelectTag(HttpServletRequest request) throws Exception {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("jsonView");

    String superdeptid = URLDecoder.decode(request.getParameter("superdeptid"), "utf-8");
    String depth = URLDecoder.decode(request.getParameter("depth"), "utf-8");

    Map<String, String> param = new HashMap<String, String>();
    param.put("superdeptid", superdeptid);
    param.put("depth", depth);
    List<Department> deptList = departmentService.getDepartmentList(param);

    modelAndView.addObject("depthList", deptList);

    return modelAndView;
  }
}
