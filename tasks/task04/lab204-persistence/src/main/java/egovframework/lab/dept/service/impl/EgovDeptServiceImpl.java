package egovframework.lab.dept.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import egovframework.lab.dept.service.DeptVO;
import egovframework.lab.dept.service.EgovDeptService;
import jakarta.annotation.Resource;

/**
 * EgovDeptService 구현체
 *
 * <p>
 * Bean 이름이 {@code "Impl"}로 끝나는 클래스에 대해
 * {@code context-transaction.xml} 의 AOP advice가 적용된다.
 * </p>
 */
@Service("egovDeptService")
public class EgovDeptServiceImpl implements EgovDeptService {

	@Resource(name = "deptMapper")
	private DeptMapper deptMapper;

	@Override
	public void createDept(DeptVO vo) throws Exception {
		deptMapper.insertDept(vo);
	}

	@Override
	public void createRBRole(DeptVO vo) throws Exception {
		deptMapper.insertDept(vo);
	}

	@Override
	public void createNoRBRole(DeptVO vo) throws Exception {
		deptMapper.insertDept(vo);
	}

	@Override
	public void createTwoDeptsThenFail(DeptVO first, DeptVO second) {
		deptMapper.insertDept(first);
		deptMapper.insertDept(second);
		throw new IllegalStateException("의도된 RuntimeException - 트랜잭션 롤백 검증용");
	}

	@Override
	public void createTwoDeptsThenCheckedFail(DeptVO first, DeptVO second) throws Exception {
		deptMapper.insertDept(first);
		deptMapper.insertDept(second);
		throw new Exception("의도된 체크드 Exception - 기본 advice는 롤백하지 않음");
	}

	@Override
	public void createRBRoleTwoDeptsThenCheckedFail(DeptVO first, DeptVO second) throws Exception {
		deptMapper.insertDept(first);
		deptMapper.insertDept(second);
		throw new Exception("의도된 체크드 Exception - createRBRole 패턴이라 롤백됨");
	}

	@Override
	public void createNoRBRoleTwoDeptsThenCheckedFail(DeptVO first, DeptVO second) throws Exception {
		deptMapper.insertDept(first);
		deptMapper.insertDept(second);
		throw new Exception("의도된 체크드 Exception - createNoRBRole 패턴이라 롤백되지 않음");
	}

	@Override
	public DeptVO findDept(BigDecimal deptNo) {
		return deptMapper.selectDept(deptNo);
	}

	@Override
	public List<DeptVO> findAllDept() {
		return deptMapper.selectAllDept();
	}
}
