package egovframework.lab.emp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.annotation.Resource;

/**
 * TODO [05. Emp 계층(iBatis 방식 계승) Mapper XML 파일 00 - Test] 개요
 *       EgovAbstractMapper 상속 방식의 Mapper XML 을 완성하십시오.
 *
 * <p>
 * {@link egovframework.lab.emp.EmpDAOTest} 가 Java DAO (EmpDAO.java) 측면을 담당한다면,
 * 이 클래스는 <b>EmpMapper.xml</b> 쪽의 작성 품질을 담당한다.
 * </p>
 *
 * <ul>
 *   <li>01. {@code <mapper namespace="Emp">} 로 작성되어 EmpDAO 의 {@code "Emp.xxx"}
 *       호출 형식과 일치하는지</li>
 *   <li>02. {@code <select id="selectEmp">} 의 WHERE 절이 {@code #{empNo}} 형태의
 *       PreparedStatement 바인딩으로 작성되었는지 ({@code ${empNo}} 또는 하드코딩이 아닌지)</li>
 * </ul>
 *
 * <p>
 * - 테스트 환경: MyBatis 의 {@link MappedStatement#getBoundSql(Object)} 를 활용해
 * 실제 DB 호출 없이 최종 SQL 문자열과 파라미터 매핑을 검증한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/resources/egovframework/sqlmap/lab/mappers/EmpMapper.xml
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class EmpMapperTest {

	@Resource(name = "sqlSession")
	private SqlSessionFactory sqlSessionFactory;

	/* TODO [05. Emp 계층(iBatis 방식 계승) Mapper XML 파일 01  - Test] namespace 를 EmpDAO 의 호출 prefix 와 일치하도록 지정하십시오. */
	@Test
	public void EmpMapper_XML의_namespace가_Emp로_작성되어야_한다() {
		Configuration configuration = sqlSessionFactory.getConfiguration();

		// namespace 가 "Emp" 여야 statement id 가 "Emp.xxx" 형태로 등록된다.
		// EmpDAO 는 "Emp.insertEmp", "Emp.selectEmp" 등의 형식으로 호출하므로
		// namespace 가 "Emp" 가 아니면 런타임에 BindingException 이 발생한다.
		assertTrue("<mapper namespace=\"Emp\"> 로 작성되어야 합니다. "
				+ "(EmpDAO 가 \"Emp.selectEmp\" 형식으로 호출)",
				configuration.hasStatement("Emp.selectEmp"));
		//assertTrue(configuration.hasStatement("Emp.insertEmp"));
		//assertTrue(configuration.hasStatement("Emp.updateEmp"));
		//assertTrue(configuration.hasStatement("Emp.deleteEmp"));
		//assertTrue(configuration.hasStatement("Emp.selectAllEmp"));
	}

	/* TODO [05. Emp 계층(iBatis 방식 계승) Mapper XML 파일 02  - Test] WHERE 조건의 empNo 파라미터를 바인딩 문법으로 작성하십시오. */
	@Test
	public void selectEmp의_WHERE절이_empNo로_바인딩되어야_한다() {
		MappedStatement stmt = sqlSessionFactory.getConfiguration()
				.getMappedStatement("Emp.selectEmp");
		assertNotNull(stmt);

		// parameterType="java.math.BigDecimal" 이므로 BigDecimal 로 바인딩 호출.
		BoundSql boundSql = stmt.getBoundSql(new BigDecimal(7369));
		String sqlNormalized = boundSql.getSql().replaceAll("\\s+", " ").toUpperCase();

		// 1) "WHERE EMP_NO" 이 SQL 에 포함되어야 한다.
		assertTrue("selectEmp 에 WHERE EMP_NO 조건이 포함되어야 합니다.",
				sqlNormalized.contains("WHERE EMP_NO"));

		// 2) #{empNo} 로 작성되어야 PreparedStatement 플레이스홀더(?) 로 바인딩된다.
		//    - #{empNo} → SQL 은 "...WHERE EMP_NO = ?" 가 되고
		//      ParameterMapping.property="empNo" 가 1개 생성된다.
		//    - ${empNo} → 단순 문자열 치환이므로 ParameterMapping 이 생성되지 않는다. (SQL Injection 위험)
		//    - 하드코딩(예: WHERE EMP_NO = 7369) 이면 역시 ParameterMapping 이 생성되지 않는다.
		List<ParameterMapping> mappings = boundSql.getParameterMappings();
		Set<String> properties = new HashSet<>();
		for (ParameterMapping m : mappings) {
			properties.add(m.getProperty());
		}
		assertTrue(
				"selectEmp 의 WHERE 절은 #{empNo} 로 작성되어야 합니다. "
						+ "(${empNo} 또는 하드코딩으로 작성하면 ParameterMapping 의 property 로 \"empNo\" 가 생성되지 않습니다.)",
				properties.contains("empNo"));
	}
}
