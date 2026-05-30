package egovframework.lab.ioc.service.impl;

import egovframework.lab.ioc.service.EmpService;
import egovframework.lab.ioc.service.EmpVO;
import java.util.List;

public class XmlEmpServiceImpl implements EmpService {

  // TODO [Step 1-3] XmlEmpServiceImpl 작성
  private XmlEmpDAO empDAO;

  public void setEmpDAO(XmlEmpDAO empDAO) {
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
