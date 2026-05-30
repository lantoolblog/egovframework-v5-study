package egovframework.lab.emp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.lab.emp.service.EmpVO;
import egovframework.lab.emp.service.impl.EmpDAO;
import jakarta.annotation.Resource;

/**
 * EmpDAO CRUD 동작 검증 (EgovAbstractMapper 상속 방식).
 *
 * <p>
 * {@link egovframework.lab.emp.EmpDAOTest} 가 EmpDAO 의 "구조"
 * (@Repository 어노테이션, EgovAbstractMapper 상속, EmpMapper XML namespace) 를
 * 담당한다면, 이 클래스는 실제 DB 에 대한 CRUD 동작 결과를 검증한다.
 * </p>
 *
 * <ul>
 *   <li>selectAllEmp : 전체 조회</li>
 *   <li>selectEmp   : 단건 조회</li>
 *   <li>insertEmp   : 신규 등록</li>
 *   <li>updateEmp   : 수정</li>
 *   <li>deleteEmp   : 삭제 (FK CASCADE 포함)</li>
 * </ul>
 *
 * <p>
 * 독립성을 위해 DataSource + Mapper 계층 + <b>emp 패키지 전용 component-scan</b> 만 로드한다.
 * 매 테스트 전 초기 데이터를 적재하여 테스트 간 영향을 배제한다.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/test-context-emp-scan.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class EmpMapperCrudTest {

	@Resource(name = "empDAO")
	private EmpDAO empDAO;

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

	@Test
	public void selectAllEmp_초기데이터_14건이_조회되어야_한다() {
		List<EmpVO> all = empDAO.selectAllEmp();
		assertEquals("초기 데이터에는 사원 14명이 있어야 합니다.", 14, all.size());
	}

	@Test
	public void selectEmp_특정_사번으로_조회되어야_한다() {
		EmpVO smith = empDAO.selectEmp(new BigDecimal(7369));
		assertNotNull(smith);
		assertEquals("SMITH", smith.getEmpName());
		assertEquals("CLERK", smith.getJob());
		assertEquals(new BigDecimal(20), smith.getDeptNo());
	}

	@Test
	public void insertEmp_신규_사원을_등록할_수_있어야_한다() {
		EmpVO newEmp = new EmpVO();
		newEmp.setEmpNo(new BigDecimal(8001));
		newEmp.setEmpName("NEWBIE");
		newEmp.setJob("CLERK");
		newEmp.setHireDate(Date.valueOf("2024-01-01"));
		newEmp.setSal(new BigDecimal(2000));
		newEmp.setDeptNo(new BigDecimal(10));

		int affected = empDAO.insertEmp(newEmp);
		assertEquals(1, affected);

		EmpVO loaded = empDAO.selectEmp(new BigDecimal(8001));
		assertNotNull(loaded);
		assertEquals("NEWBIE", loaded.getEmpName());
	}

	@Test
	public void updateEmp_사원_정보를_수정할_수_있어야_한다() {
		EmpVO emp = empDAO.selectEmp(new BigDecimal(7369));
		emp.setSal(new BigDecimal(9999));
		int affected = empDAO.updateEmp(emp);
		assertEquals(1, affected);

		EmpVO loaded = empDAO.selectEmp(new BigDecimal(7369));
		assertEquals(new BigDecimal(9999), loaded.getSal());
	}

	@Test
	public void deleteEmp_사원을_삭제할_수_있어야_한다() {
		// FK CASCADE 설정으로 jobhist 도 함께 삭제된다 (sample_schema_hsql.sql 의 jobhist_ref_emp_fk).
		int affected = empDAO.deleteEmp(new BigDecimal(7934));
		assertEquals(1, affected);

		EmpVO loaded = empDAO.selectEmp(new BigDecimal(7934));
		assertNull(loaded);
	}
}
