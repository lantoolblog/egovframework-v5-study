package egovframework.lab.emp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractDAO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.lab.emp.service.EmpVO;

/**
 * 사원(EMP) DAO — iBatis 방식 계승 (방법 1)
 *
 * ============================================================================
 * TODO [04. Emp 계층(iBatis 방식 계승) DAO 00] 개요
 *       EgovAbstractMapper 상속 방식으로 DAO를 작성하십시오.
 * ============================================================================
 * 요구사항:
 *   (1) @Repository("empDAO") 부착 (이 값이 Spring Bean 이름이 됨)
 *   (2) extends org.egovframe.rte.psl.dataaccess.EgovAbstractMapper
 *   (3) 부모 클래스가 제공하는 SQL 호출 헬퍼 메서드를 사용하여 본문을 채우십시오.
 *       각 메서드에 주석처리한 코드에서 주석을 제거하고
 *       그 아래 throw new UnsupportedOperationException() 코드를 삭제하십시오.
 *       - insert(...)     → INSERT 처리, 영향 행 수 반환
 *       - update(...)     → UPDATE 처리
 *       - delete(...)     → DELETE 처리
 *       - selectOne(...)  → 단건 조회
 *       - selectList(...) → 다건 조회
 *
 *   ※ 호출 시 statement id 는 반드시 "namespace.methodName" 형태로 작성.
 *     EmpMapper.xml의 namespace를 확인하십시오.
 *     (EmpMapper.xml 의 namespace 와 일치해야 함)
 *
 * 검증 테스트: EmpDAOTest
 * ============================================================================
 */
/* TODO [04. Emp 계층(iBatis 방식 계승) DAO 01] 어노테이션을 통해 빈을 등록하십시오. */
@Repository("empDAO")
public class EmpDAO extends EgovAbstractMapper
/* TODO [04. Emp 계층(iBatis 방식 계승) DAO 02] DAO가 상속할 추상클래스(Egov로 시작)를 입력하십시오. */ {

	/* TODO [04. Emp 계층(iBatis 방식 계승) DAO 03] 5개 메서드를 완성하십시오. */

	public int insertEmp(EmpVO vo) {
		return insert("Emp.insertEmp", vo);
		// throw new UnsupportedOperationException("insertEmp 미구현");
	}

	public int updateEmp(EmpVO vo) {
		return update("Emp.updateEmp", vo);
		// throw new UnsupportedOperationException("updateEmp 미구현");
	}

	public int deleteEmp(BigDecimal empNo) {
		 return delete("Emp.deleteEmp", empNo);
		// throw new UnsupportedOperationException("deleteEmp 미구현");
	}

	public EmpVO selectEmp(BigDecimal empNo) {
		return selectOne("Emp.selectEmp", empNo);
		// throw new UnsupportedOperationException("selectEmp 미구현");
	}

	public List<EmpVO> selectAllEmp() {
		 return selectList("Emp.selectAllEmp");
		// throw new UnsupportedOperationException("selectAllEmp 미구현");
	}
}
