package egovframework.lab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.annotation.Resource;

/**
 * TODO [03. MyBatis Configuration XML 파일 00 - Test] 개요
 *       MyBatis 전역 설정을 등록하십시오.
 *
 * <p>
 * Spring 연동이 아닌, MyBatis Configuration 자체의 속성을 검증한다.
 * 검증 대상 설정은 {@code src/main/resources/egovframework/sqlmap/lab/sql-mapper-config.xml}
 * 에 작성되어 있어야 한다.
 * </p>
 *
 * <ul>
 *   <li>01. {@code <settings>} 의 {@code mapUnderscoreToCamelCase} 가 true 로 설정되어
 *       DB 의 {@code DEPT_NO} 컬럼이 VO 의 {@code deptNo} 필드로 자동 매핑되는지</li>
 *   <li>02. {@code <typeAliases>} 가 등록되어 있어 Mapper XML 에서
 *       {@code DeptVO}, {@code EmpVO} 등 짧은 별칭(alias) 사용이 가능한지</li>
 * </ul>
 *
 * <p>
 * - 테스트 환경: MyBatis-Spring 연동({@code sqlSession}, {@code MapperConfigurer} 등) 자체는
 * {@link egovframework.lab.MyBatisSpringConfigTest} 가 담당한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/resources/egovframework/sqlmap/lab/sql-mapper-config.xml
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class MyBatisConfigTest {

	@Resource(name = "sqlSession")
	private SqlSessionFactory sqlSessionFactory;

	/* TODO [03. MyBatis Configuration XML 파일 01 - Test] settings를 등록하십시오. */
	@Test
	public void mapUnderscoreToCamelCase_설정이_true여야_한다() {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		assertTrue("mapUnderscoreToCamelCase 가 true 로 설정되어야 DB 컬럼 DEPT_NO가 deptNo로 매핑됩니다.",
				configuration.isMapUnderscoreToCamelCase());
	}

	/* TODO [03. MyBatis Configuration XML 파일 02 - Test] typeAlias를 등록하십시오. */
	@Test
	public void typeAliases_가_등록되어_있어야_한다() {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		assertEquals("deptVO 별칭은 DeptVO 클래스를 가리켜야 합니다.",
				egovframework.lab.dept.service.DeptVO.class,
				configuration.getTypeAliasRegistry().resolveAlias("deptVO"));
		assertEquals("empVO 별칭은 EmpVO 클래스를 가리켜야 합니다.",
				egovframework.lab.emp.service.EmpVO.class,
				configuration.getTypeAliasRegistry().resolveAlias("empVO"));
	}
}
