package egovframework.lab.ioc.service.impl;

import egovframework.lab.ioc.service.EmpService;
import egovframework.lab.ioc.service.EmpVO;
import java.util.List;

public class JavaConfigEmpServiceImpl implements EmpService {

  // TODO [Step 3-3] JavaConfigEmpServiceImpl 작성
  private JavaConfigEmpDAO empDAO;

  public void setEmpDAO(JavaConfigEmpDAO empDAO) {
    this.empDAO = empDAO;
  }

  public void insertEmp(EmpVO empVO) throws Exception {
    empDAO.insertEmp(empVO);
  }

  public void updateEmp(EmpVO empVO) throws Exception {
    empDAO.updateEmp(empVO);
  }

  public void deleteEmp(EmpVO empVO) throws Exception {
    empDAO.deleteEmp(empVO);
  }

  public EmpVO selectEmp(EmpVO empVO) throws Exception {
    return empDAO.selectEmp(empVO);
  }

  public List<EmpVO> selectEmpList() throws Exception {
    return empDAO.selectEmpList();
  }
}
