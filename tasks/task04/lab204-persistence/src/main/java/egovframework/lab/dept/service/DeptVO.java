package egovframework.lab.dept.service;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DEPT 테이블 매핑용 Value Object
 *
 * <pre>
 *  DEPT_NO   NUMERIC(2)  -> deptNo
 *  DEPT_NAME VARCHAR(14) -> deptName
 *  LOC       VARCHAR(13) -> loc
 * </pre>
 */
public class DeptVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal deptNo;
	private String deptName;
	private String loc;

	public DeptVO() {
	}

	public DeptVO(BigDecimal deptNo, String deptName, String loc) {
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.loc = loc;
	}

	public BigDecimal getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(BigDecimal deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@Override
	public String toString() {
		return "DeptVO[deptNo=" + deptNo + ", deptName=" + deptName + ", loc=" + loc + "]";
	}
}
