package egovframework.lab.ajax.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.lab.com.vo.Department;

@Repository("departmentDao")
@SuppressWarnings("deprecation")
public class DepartmentDao extends EgovAbstractMapper {

	@SuppressWarnings("unchecked")
	public List<Department> getDepartmentList(Map<?, ?> param) throws DataAccessException {		
		return (List<Department>)list("Department.getDepartmentList",param);
	}

}
