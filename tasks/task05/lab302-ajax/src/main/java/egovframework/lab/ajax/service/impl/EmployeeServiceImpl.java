package egovframework.lab.ajax.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.lab.ajax.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;	

	// TODO [Step 2-2-4] getNameListForSuggest 메소드를 구현한다.

		//TODO [Step 2-2-5] employeeDao 의 getNameListForSuggest 메소드를 이용하여 검색한 후 결과를 리턴한다.

}
