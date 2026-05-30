package egovframework.lab.ioc.service;

import java.util.List;

public interface EmpService {

  // TODO [Step 1-1, 2-1, 3-1] EmpService 에 대한 CRUD 및 목록조회 비지니스 메서드 작성
  public void insertEmp(EmpVO empVO) throws Exception;

  public void updateEmp(EmpVO empVO) throws Exception;

  public void deleteEmp(EmpVO empVO) throws Exception;

  public EmpVO selectEmp(EmpVO empVO) throws Exception;

  public List<EmpVO> selectEmpList() throws Exception;
}
