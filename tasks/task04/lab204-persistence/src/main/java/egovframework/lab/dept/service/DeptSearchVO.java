package egovframework.lab.dept.service;

import java.io.Serializable;
import java.util.List;

/**
 * 부서 검색 조건 VO (Dynamic SQL 테스트용)
 *
 * - 모든 필드는 nullable: 값이 있으면 WHERE 조건에 추가됨
 */
public class DeptSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String deptName;
	private String loc;
	private List<Integer> deptNoList;

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

	public List<Integer> getDeptNoList() {
		return deptNoList;
	}

	public void setDeptNoList(List<Integer> deptNoList) {
		this.deptNoList = deptNoList;
	}
}
