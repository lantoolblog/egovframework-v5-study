package egovframework.lab.dept;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.lab.dept.service.DeptSearchVO;
import egovframework.lab.dept.service.DeptVO;
import egovframework.lab.dept.service.impl.DeptMapper;
import jakarta.annotation.Resource;

/**
 * TODO [07. Dept 계층(Mapper Interface 방식) Mapper XML 파일 00 - Test] 개요
 *       Mapper Interface(DeptMapper) 와 매핑되는 Mapper XML 을 완성하십시오.
 *
 * <p>검사 포인트:</p>
 * <ul>
 *   <li>01. DeptMapper.xml 의 {@code <mapper namespace="...">} 가 DeptMapper 인터페이스의
 *       풀패키지명과 정확히 일치하여 모든 statement 가 등록되는지</li>
 *   <li>02. {@code <select id="selectDeptByCondition">} 의 Dynamic SQL
 *       ({@code <where>} + {@code <if>}) 이 문법에 맞게 작성되어 실제 조회 결과가
 *       조건 조합에 따라 기대대로 달라지는지</li>
 *   <ul>
 *     <li>조건 없을 시 전체가 조회 되는지 여부</li>
 *     <li>부서명 검색 동작 여부 확인</li>
 *     <li>위치 검색 동작 여부 확인</li>
 *     <li>위치와 부서명 복합 검색 동작 여부 확인</li>
 *     <li>빈 문자열이 조건에 포함되지 않는지 여부 확인</li>
 *   </ul>
 * </ul>
 *
 * <p>
 * - 테스트 환경: 독립성을 위해 DataSource + Mapper 계층만 로드한다.
 * Dynamic SQL 의 실제 결과를 검증하기 위해 매 테스트 전 초기 데이터를 적재한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/resources/egovframework/sqlmap/lab/mappers/DeptMapper.xml
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class DeptMapperTest {

	@Resource(name = "deptMapper")
	private DeptMapper deptMapper;

	@Resource(name = "sqlSession")
	private SqlSessionFactory sqlSessionFactory;

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@Before
	public void onSetUp() throws Exception {
		// 매 테스트 전 DB 를 초기 상태로 원복 (DROP/CREATE + 초기 데이터 INSERT)
		try (Connection conn = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(conn,
					new ClassPathResource("sample_schema_hsql.sql"));
		}
	}

	/* TODO [07. Dept 계층(Mapper Interface 방식) Mapper XML 파일 01 - Test] namespace 를 DeptMapper 인터페이스의 풀패키지명과 정확히 일치시키십시오. */
	@Test
	public void Mapper_XML_namespace가_인터페이스_풀패키지명과_일치해야_한다() {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		String fqcn = DeptMapper.class.getName(); // "egovframework.lab.dept.service.impl.DeptMapper"

		// namespace 가 일치해야 hasStatement 가 true 를 반환한다.
		assertTrue("namespace=\"" + fqcn + "\" 인 selectDept statement 가 등록되어야 합니다.",
				configuration.hasStatement(fqcn + ".selectDept"));
		assertTrue(configuration.hasStatement(fqcn + ".insertDept"));
		assertTrue(configuration.hasStatement(fqcn + ".updateDept"));
		assertTrue(configuration.hasStatement(fqcn + ".updateDeptSelective"));
		assertTrue(configuration.hasStatement(fqcn + ".deleteDept"));
		assertTrue(configuration.hasStatement(fqcn + ".selectAllDept"));
		assertTrue(configuration.hasStatement(fqcn + ".selectDeptByCondition"));
		assertTrue(configuration.hasStatement(fqcn + ".selectDeptByDeptNoIn"));
	}

	// =====================================================================
	/* TODO [07. Dept 계층(Mapper Interface 방식) Mapper XML 파일 02 - Test] selectDeptByCondition 의 Dynamic SQL 을 완성하십시오. */
	// =====================================================================

	@Test
	public void selectByCondition_조건이_하나도_없으면_전체_조회되어야_한다() {
		// <where> 태그: 안쪽 <if> 가 모두 false 면 WHERE 절 자체가 사라져
		// 전체 행이 조회되어야 한다.
		DeptSearchVO searchVO = new DeptSearchVO();
		List<DeptVO> result = deptMapper.selectDeptByCondition(searchVO);
		assertEquals("초기 데이터 4건이 모두 조회되어야 합니다.", 4, result.size());
	}

	@Test
	public void selectByCondition_부서명_LIKE_검색이_동작해야_한다() {
		// #{deptName} 이 '%' || ? || '%' 로 바인딩되어 부분일치 검색.
		DeptSearchVO searchVO = new DeptSearchVO();
		searchVO.setDeptName("SAL");
		List<DeptVO> result = deptMapper.selectDeptByCondition(searchVO);
		assertEquals("SALES 부서 1건만 조회되어야 합니다.", 1, result.size());
		assertEquals("SALES", result.get(0).getDeptName());
	}

	@Test
	public void selectByCondition_위치_검색이_동작해야_한다() {
		// loc 만 지정했을 때, <where> 가 첫 AND 를 제거하고 LOC = ? 만 남긴다.
		DeptSearchVO searchVO = new DeptSearchVO();
		searchVO.setLoc("DALLAS");
		List<DeptVO> result = deptMapper.selectDeptByCondition(searchVO);
		assertEquals("DALLAS 에 있는 RESEARCH 1건만 조회되어야 합니다.", 1, result.size());
		assertEquals("RESEARCH", result.get(0).getDeptName());
	}

	@Test
	public void selectByCondition_부서명과_위치_복합_검색이_동작해야_한다() {
		// 두 조건이 모두 있으면 WHERE DEPT_NAME LIKE ? AND LOC = ? 형태로 결합된다.
		DeptSearchVO searchVO = new DeptSearchVO();
		searchVO.setDeptName("ACCOUNT");
		searchVO.setLoc("NEW YORK");
		List<DeptVO> result = deptMapper.selectDeptByCondition(searchVO);
		assertEquals("ACCOUNTING + NEW YORK 조합으로 1건만 조회되어야 합니다.", 1, result.size());
	}

	@Test
	public void selectByCondition_빈_문자열은_조건에서_제외되어야_한다() {
		// 빈 문자열 "" 은 조건에 포함되지 않아야 한다.
		// <if test="deptName != null and deptName != ''"> 형태로 작성했는지를 확인한다.
		// (만약 != '' 체크가 빠지면 LIKE '%%' 가 되어 결과는 동일할 수도 있으나
		//  쿼리 의도가 '전체 조회' 임을 명확히 해야 한다.)
		DeptSearchVO searchVO = new DeptSearchVO();
		searchVO.setDeptName("");
		searchVO.setLoc("");
		List<DeptVO> result = deptMapper.selectDeptByCondition(searchVO);
		assertEquals("빈 문자열 조건은 제외되어 전체 조회되어야 합니다.", 4, result.size());
	}
}
