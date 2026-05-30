package egovframework.lab.dept.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.EgovMapper;

import egovframework.lab.dept.service.DeptSearchVO;
import egovframework.lab.dept.service.DeptVO;

/**
 * 부서(DEPT) Mapper Interface — MyBatis 신규 방식 (방법 2)
 *
 * ============================================================================
 * TODO [06. Dept 계층(Mapper Interface 방식) DAO] 개요
 *       Mapper Scan을 위한 어노테이션 부착 및 updateDeptSelective 메서드를 선언하십시오.
 * ============================================================================
 * 요구사항:
 *   (1) Mapper Scan을 위한 어노테이션 부착 (이 값이 Spring Bean 이름이 됨)
 *       - 패키지: org.egovframe.rte.psl.dataaccess.mapper.EgovMapper (이미 import 되어 있음)
 *       - context-mapper.xml 에 등록된 MapperConfigurer(basePackage="egovframework.lab")가
 *         해당 어노테이션을 스캔하여 구현체(MyBatis 프록시)를 Spring Bean 으로 자동 등록한다.
 *
 *   (2) updateDeptSelective 메서드 선언 추가
 *       - 메서드명: "updateDeptSelective"
 *         (DeptMapper.xml 의 <update id="updateDeptSelective"/> 와 정확히 일치해야 함)
 *       - 파라미터 타입: DeptVO (XML 의 parameterType="deptVO" 와 매핑)
 *       - 반환 타입: int (영향 받은 행 수)
 *       - Dynamic SQL(<set> + <if>)로 deptName, loc 중 null 이 아닌 필드만 갱신한다.
 *
 *   ※ Mapper Interface 방식(방법 2)의 일반 원칙:
 *     - 이 인터페이스의 풀패키지명({@code egovframework.lab.dept.service.impl.DeptMapper})은
 *       Mapper XML 의 {@code namespace} 속성과 정확히 일치해야 한다.
 *     - 각 메서드명은 Mapper XML 의 {@code id} 속성과 정확히 일치해야 한다.
 *     - 구현 클래스는 직접 작성하지 않는다. (MyBatis 가 런타임에 프록시로 자동 생성)
 *
 * 검증 테스트: MapperInterfaceTest
 * ============================================================================
 */
/* TODO [06. Dept 계층(Mapper Interface 방식) DAO 01] Mapper Interface 를 자동 스캔하기 위한 어노테이션을 부착하십시오. */
@EgovMapper("deptMapper")
public interface DeptMapper {

	/** 부서 등록 */
	void insertDept(DeptVO vo);

	/** 부서 수정 (PK 제외 모든 필드) */
	int updateDept(DeptVO vo);

	/**
	 * 부서 부분 수정 (Dynamic SQL의 &lt;set&gt; + &lt;if&gt; 조합).
	 * deptName, loc 중 null이 아닌 필드만 갱신한다.
	 */
	/* TODO [06. Dept 계층(Mapper Interface 방식) DAO 02] updateDeptSelective 메서드를 선언하십시오. */
	/* 여기에_updateDeptSelective_메서드_선언을_작성하십시오 (메서드명/파라미터/반환타입은 개요 요구사항 (2) 참조) */
	int updateDeptSelective(DeptVO vo);

	/** 부서 삭제 */
	int deleteDept(BigDecimal deptNo);

	/** 부서 단건 조회 */
	DeptVO selectDept(BigDecimal deptNo);

	/** 부서 전체 조회 */
	List<DeptVO> selectAllDept();

	/**
	 * 부서 검색 (Dynamic SQL: &lt;where&gt; + &lt;if&gt;).
	 * 검색 조건이 없으면 전체 결과를 반환한다.
	 */
	List<DeptVO> selectDeptByCondition(DeptSearchVO searchVO);

	/**
	 * 여러 부서번호로 일괄 조회 (Dynamic SQL: &lt;foreach&gt;).
	 * deptNoList가 null이거나 비어 있으면 빈 결과를 반환해야 한다.
	 */
	List<DeptVO> selectDeptByDeptNoIn(DeptSearchVO searchVO);
}
