package egovframework.lab.dept.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 부서(DEPT) 비즈니스 서비스 인터페이스
 *
 * <p>
 * 트랜잭션 AOP는 {@code egovframework.lab..impl.*Impl.*(..)} 패턴에 적용되므로,
 * 모든 트랜잭션 검증은 {@code EgovDeptServiceImpl} 메서드 호출을 통해 이루어져야 한다.
 * </p>
 */
public interface EgovDeptService {

	/**
	 * 부서를 등록한다.
	 * <p>
	 * 트랜잭션 advice의 {@code create*} 패턴에 매칭되어 기본 동작(REQUIRED)을 따른다.
	 * RuntimeException이 발생하면 롤백되고, 체크드 예외는 롤백되지 않는다.
	 * </p>
	 */
	void createDept(DeptVO vo) throws Exception;

	/**
	 * 부서를 등록한다 - 체크드 예외 롤백 옵션 켜짐.
	 * <p>
	 * 트랜잭션 advice의 {@code createRBRole} 패턴에 매칭되어
	 * 체크드 예외(Exception)도 롤백시킨다.
	 * </p>
	 */
	void createRBRole(DeptVO vo) throws Exception;

	/**
	 * 부서를 등록한다 - 체크드 예외 롤백 옵션 꺼짐.
	 * <p>
	 * 트랜잭션 advice의 {@code createNoRBRole} 패턴에 매칭되어
	 * 체크드/언체크드 예외 모두 롤백되지 않는다.
	 * </p>
	 */
	void createNoRBRole(DeptVO vo) throws Exception;

	/**
	 * 두 부서를 한 트랜잭션으로 등록한다.
	 * <p>
	 * 첫 번째 INSERT 직후 RuntimeException을 발생시키므로
	 * 둘 다 롤백되어 등록 건수가 0이어야 한다.
	 * </p>
	 */
	void createTwoDeptsThenFail(DeptVO first, DeptVO second);

	/**
	 * 두 부서를 한 트랜잭션으로 등록한다 (체크드 예외 발생).
	 * <p>
	 * 첫 번째 INSERT 직후 체크드 Exception을 발생시킨다.
	 * </p>
	 *
	 * <ul>
	 *   <li>{@code create*} (기본) 매칭이면 → 롤백되지 않는다 (커밋된다).</li>
	 *   <li>{@code createRBRole} 매칭이면 → 롤백된다.</li>
	 * </ul>
	 */
	void createTwoDeptsThenCheckedFail(DeptVO first, DeptVO second) throws Exception;

	/**
	 * createRBRole 패턴: 체크드 예외도 반드시 롤백된다.
	 */
	void createRBRoleTwoDeptsThenCheckedFail(DeptVO first, DeptVO second) throws Exception;

	/**
	 * createNoRBRole 패턴: 체크드 예외가 발생해도 롤백되지 않는다.
	 */
	void createNoRBRoleTwoDeptsThenCheckedFail(DeptVO first, DeptVO second) throws Exception;

	DeptVO findDept(BigDecimal deptNo);

	List<DeptVO> findAllDept();
}
