package egovframework.lab.dept;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.lab.dept.service.DeptVO;
import egovframework.lab.dept.service.impl.DeptMapper;
import jakarta.annotation.Resource;

/**
 * DeptMapper 의 기본 CRUD 4종을 검증한다.
 *
 * <ul>
 *   <li>{@code insertDept} - 신규 등록</li>
 *   <li>{@code selectDept} - PK 단건 조회 + mapUnderscoreToCamelCase 동작 검증</li>
 *   <li>{@code updateDept} - 전체 갱신</li>
 *   <li>{@code deleteDept} - 삭제</li>
 *   <li>{@code selectAllDept} - 전체 조회 (초기 데이터 4건)</li>
 * </ul>
 *
 * <p>
 * 독립성을 위해 DataSource + Mapper 계층만 로드한다.
 * 서비스/트랜잭션 완성도와 무관하게 Mapper CRUD 를 단독 검증한다.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class DeptMapperCrudTest {

	@Resource(name = "deptMapper")
	private DeptMapper deptMapper;

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@Before
	public void onSetUp() throws Exception {
		// 매 테스트 전 DB 초기화
		try (Connection conn = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(conn,
					new ClassPathResource("sample_schema_hsql.sql"));
		}
	}

	@Test
	public void selectAllDept_초기데이터_4건이_조회되어야_한다() {
		List<DeptVO> all = deptMapper.selectAllDept();
		assertEquals("초기 데이터에는 ACCOUNTING, RESEARCH, SALES, OPERATIONS 4개 부서가 있어야 합니다.",
				4, all.size());
	}

	@Test
	public void selectDept_PK로_단건_조회되어야_한다() {
		DeptVO dept = deptMapper.selectDept(new BigDecimal(10));
		assertNotNull("DEPT_NO=10인 부서가 조회되어야 합니다.", dept);
		assertEquals("ACCOUNTING", dept.getDeptName());
		assertEquals("NEW YORK", dept.getLoc());
	}

	@Test
	public void selectDept_컬럼_언더스코어가_camelCase로_매핑되어야_한다() {
		// mapUnderscoreToCamelCase=true 가 동작해야 DEPT_NO -> deptNo 자동 매핑된다.
		DeptVO dept = deptMapper.selectDept(new BigDecimal(20));
		assertNotNull(dept);
		assertEquals(new BigDecimal(20), dept.getDeptNo());
		assertEquals("RESEARCH", dept.getDeptName());
	}

	@Test
	public void insertDept_신규_부서를_등록할_수_있어야_한다() {
		DeptVO newDept = new DeptVO(new BigDecimal(50), "MARKETING", "SEOUL");
		deptMapper.insertDept(newDept);

		DeptVO loaded = deptMapper.selectDept(new BigDecimal(50));
		assertNotNull(loaded);
		assertEquals("MARKETING", loaded.getDeptName());
		assertEquals("SEOUL", loaded.getLoc());
	}

	@Test
	public void updateDept_부서명과_위치를_수정할_수_있어야_한다() {
		DeptVO update = new DeptVO(new BigDecimal(10), "FINANCE", "BUSAN");
		int affected = deptMapper.updateDept(update);
		assertEquals("UPDATE 영향 행 수가 1이어야 합니다.", 1, affected);

		DeptVO loaded = deptMapper.selectDept(new BigDecimal(10));
		assertEquals("FINANCE", loaded.getDeptName());
		assertEquals("BUSAN", loaded.getLoc());
	}

	@Test
	public void deleteDept_부서를_삭제할_수_있어야_한다() {
		int affected = deptMapper.deleteDept(new BigDecimal(40));
		assertEquals("DELETE 영향 행 수가 1이어야 합니다.", 1, affected);

		DeptVO loaded = deptMapper.selectDept(new BigDecimal(40));
		assertNull("삭제된 부서는 조회되지 않아야 합니다.", loaded);
	}
}
