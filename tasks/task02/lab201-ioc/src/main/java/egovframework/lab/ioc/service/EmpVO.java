package egovframework.lab.ioc.service;

import java.io.Serializable;

public class EmpVO implements Serializable, Comparable<EmpVO> {

  private static final long serialVersionUID = 8737104622830172190L;

  // TODO [Step 1-2, 2-2, 3-1] EmpVO 작성
  private int empNo;

  private String empName;

  private String job;

  public int compareTo(EmpVO o) {
    if (empNo > o.getEmpNo()) {
      return 1;
    } else if (empNo < o.getEmpNo()) {
      return -1;
    } else {
      return 0;
    }
  }

  public int getEmpNo() {
    return empNo;
  }

  public void setEmpNo(int empNo) {
    this.empNo = empNo;
  }

  public String getEmpName() {
    return empName;
  }

  public void setEmpName(String empName) {
    this.empName = empName;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }
}
