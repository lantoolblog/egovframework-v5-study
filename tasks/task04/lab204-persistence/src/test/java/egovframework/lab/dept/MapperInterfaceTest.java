package egovframework.lab.dept;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.lab.dept.service.DeptVO;
import egovframework.lab.dept.service.impl.DeptMapper;
import jakarta.annotation.Resource;

/**
 * TODO [06. Dept 계층(Mapper Interface 방식) DAO 00 - Test] 개요
 *       Mapper Scan을 위한 어노테이션 부착 및 updateDeptSelective 메서드를 선언하십시오.
 *
 * <p>
 * MyBatis 신규 방식의 핵심 검사 포인트:
 * </p>
 *
 * <ul>
 *   <li>01. {@code @EgovMapper("deptMapper")} 가 부착된 인터페이스가 빈으로 자동 등록되었는지</li>
 *   <li>02. Mapper XML 의 namespace 가 인터페이스의 풀패키지명과 일치하여 메서드가 호출 가능한지</li>
 *   <li>(참고) 주입된 빈이 인터페이스의 동적 프록시인지 (MyBatis가 구현체를 만들어주는지)</li>
 *   <li>(참고)MyBatis Configuration에 Mapper Interface 가 등록되어 있는지</li>
 * </ul>
 *
 * <p>
 * - 테스트 환경: 독립성을 위해 DataSource + Mapper 계층만 로드한다.
 * 실제 SQL 호출(selectAllDept) 검증을 위해 매 테스트 전 초기 데이터를 적재한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/java/egovframework/lab/dept/service/impl/DeptMapper.java
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class MapperInterfaceTest {

	@Resource
	private ApplicationContext applicationContext;

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

	/* TODO [06. Dept 계층(Mapper Interface 방식) DAO 01 - Test] Mapper Interface 를 자동 스캔하기 위한 어노테이션을 부착하십시오. */
	@Test
	public void DeptMapper_클래스에_EgovMapper_어노테이션이_deptMapper_값으로_부착되어야_한다() {
		assertTrue("@EgovMapper(\"deptMapper\") 의 value 가 빈 이름으로 사용되어야 합니다.",
				applicationContext.containsBean("deptMapper"));
	}

	/* TODO [06. Dept 계층(Mapper Interface 방식) DAO 02 - Test] 메서드명은 statement id와 동일해야 한다. */
	@Test
	public void 메서드명은_statement_id와_동일해야_한다() {
		// DeptMapper.java 의 메서드 "부서 부분 수정 (Dynamic SQL의 &lt;set&gt; + &lt;if&gt; 조합)" 메서드를 검증한다.

		// 1) 메서드명: "updateDeptSelective" 라는 이름의 메서드가 선언되어 있어야 한다.
		Method method = null;
		for (Method m : DeptMapper.class.getDeclaredMethods()) {
			if ("updateDeptSelective".equals(m.getName())) {
				method = m;
				break;
			}
		}
		assertNotNull("DeptMapper 에 \"updateDeptSelective\" 메서드가 선언되어야 합니다. "
				+ "Mapper XML 의 <update id=\"updateDeptSelective\"> 와 이름이 정확히 일치해야 합니다.", method);

		// 2) parameterType: 파라미터는 DeptVO 1개 여야 한다.
		assertArrayEquals(
				"updateDeptSelective 의 파라미터 타입은 DeptVO 한 개여야 합니다. "
						+ "Mapper XML 의 parameterType=\"deptVO\" 와 일치해야 합니다.",
				new Class<?>[] { DeptVO.class }, method.getParameterTypes());

		// 3) resultType: 반환 타입은 int 여야 한다 (UPDATE 영향 행 수).
		assertEquals(
				"updateDeptSelective 의 반환 타입은 int 여야 합니다. "
						+ "Update 문의 경우 UPDATE 행 수를 반환해야 합니다.",
				int.class, method.getReturnType());
	}

	/* 참고용 테스트 메서드
	@Test
	public void 주입된_deptMapper_클래스가_DeptMapper_인터페이스의_인스턴스여야_한다() {
		Object bean = applicationContext.getBean("deptMapper");
		assertNotNull(bean);
		// MyBatis가 정상적으로 설정되었다면 인터페이스는 직접 인스턴스화할 수 없습니다.
		// 대신에 MapperConfigurer 설정과 @EgovMapper 어노테이션 설정에 의해
		// MyBatis가 자동으로 "프록시 구현체(인스턴스)"를 생성해서 Spring 빈으로 등록합니다.
		// 따라서 이 테스트 메서드는 MapperConfigurer 설정과 @EgovMapper 어노테이션 설정이 정상적으로 작동하는지 검증합니다.
		assertTrue("주입된 빈은 DeptMapper 인터페이스 프록시 구현체여야 합니다.",
				bean instanceof DeptMapper);
	}

	@Test
	public void Mapper_Interface가_MyBatis_Configuration에_등록되어야_한다() {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		assertTrue("DeptMapper 인터페이스가 MyBatis Mapper Registry에 등록되어 있어야 합니다.",
				configuration.hasMapper(DeptMapper.class));
	}

	@Test
	public void deptMapper를_통해_실제_SQL이_호출되어야_한다() {
		// namespace 또는 메서드명이 잘못되어 있으면 BindingException 이 발생한다.
		assertEquals(4, deptMapper.selectAllDept().size());
	}
	*/
}
