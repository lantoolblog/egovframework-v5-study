package egovframework.lab.emp.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * EMP 테이블 매핑용 Value Object
 *
 * <pre>
 *  EMP_NO    NUMERIC(4)  -> empNo
 *  EMP_NAME  VARCHAR(10) -> empName
 *  JOB       VARCHAR(9)  -> job
 *  MGR       NUMERIC(4)  -> mgr
 *  HIRE_DATE DATE        -> hireDate
 *  SAL       NUMERIC(7)  -> sal
 *  COMM      NUMERIC(7)  -> comm
 *  DEPT_NO   NUMERIC(2)  -> deptNo
 * </pre>
 */
public class EmpVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal empNo;
	private String empName;
	private String job;
	private BigDecimal mgr;
	private Date hireDate;
	private BigDecimal sal;
	private BigDecimal comm;
	private BigDecimal deptNo;

	public BigDecimal getEmpNo() {
		return empNo;
	}

	public void setEmpNo(BigDecimal empNo) {
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

	public BigDecimal getMgr() {
		return mgr;
	}

	public void setMgr(BigDecimal mgr) {
		this.mgr = mgr;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public BigDecimal getSal() {
		return sal;
	}

	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}

	public BigDecimal getComm() {
		return comm;
	}

	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}

	public BigDecimal getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(BigDecimal deptNo) {
		this.deptNo = deptNo;
	}
}
