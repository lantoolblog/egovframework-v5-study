package egovframework.lab.aop.service.impl;

import java.util.List;

import egovframework.lab.aop.service.EmpService;
import egovframework.lab.aop.service.EmpVO;

public class XmlEmpServiceImpl implements EmpService {

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
        EmpVO resultVO;
        resultVO = empDAO.selectEmp(empVO);
        
        if(resultVO == null) {
            throw new Exception("no data found!");
        }
        
        return resultVO;
    }

    public List<EmpVO> selectEmpList() throws Exception {
        return empDAO.selectEmpList();
    }

}
