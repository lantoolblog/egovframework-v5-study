package egovframework.lab.ajax.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("employeeDao")
@SuppressWarnings("deprecation")
public class EmployeeDao extends EgovAbstractMapper {

	@SuppressWarnings("unchecked")
	public List<String> getNameListForSuggest(String namePrefix){
		return (List<String>)list("Employee.getNameListForSuggest",namePrefix);
	}

}
