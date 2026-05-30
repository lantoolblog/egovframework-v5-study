package egovframework.lab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.psl.dataaccess.mapper.MapperConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.annotation.Resource;

/**
 * TODO [02. MyBatis-Spring 연동 00 - Test] 개요
 *       MyBatis-Spring 연동 빈을 등록하십시오.
 *
 * <p>
 * 주 검증 대상은 {@code context-mapper.xml} 에 정의된 두 빈이다.
 * </p>
 *
 * <ul>
 *   <li>01. {@code SqlSessionFactoryBean} 으로 등록된 {@code sqlSession} 빈의 세 속성
 *       (dataSource / configLocation / mapperLocations) 이 올바르게 지정되어 있는지</li>
 *   <li>02. {@code MapperConfigurer} 에 basePackage 속성이 지정되어 있는지</li>
 *   <li>(참고){@code mapperLocations} 에 의해 Mapper XML 이 실제로 로드되었는지</li>
 *   <li>(참고){@code MapperConfigurer} 에 의해 {@code @EgovMapper} 인터페이스가 빈으로 등록되는지</li>
 * </ul>
 *
 * <p>
 * - 테스트 환경: MyBatis 자체 설정(<code>sql-mapper-config.xml</code> 의 settings, typeAliases 등) 검증은
 * {@link egovframework.lab.MyBatisConfigTest} 가 담당한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/resources/egovframework/spring/context-mapper.xml
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml",
		"classpath:/egovframework/spring/context-mapper.xml" })
public class MyBatisSpringConfigTest {

	@Resource(name = "sqlSession")
	private SqlSessionFactory sqlSessionFactory;

	@Resource
	private ApplicationContext applicationContext;

	/* TODO [02. MyBatis-Spring 연동 01 - Test] SqlSessionFactoryBean 등록하십시오. */
	@Test
	public void sqlSession_빈이_등록되어_있어야_한다() {
		assertNotNull("sqlSession 빈이 등록되지 않았거나 SqlSessionFactory 타입이 아닙니다.", sqlSessionFactory);

		// sqlSession 빈 정의에 dataSource / configLocation / mapperLocations 가 올바르게 설정되어 있는지 검증
		ConfigurableListableBeanFactory factory =
				((ConfigurableApplicationContext) applicationContext).getBeanFactory();
		BeanDefinition definition = factory.getBeanDefinition("sqlSession");

		// 1) dataSource 는 <property ref="dataSource"/> 로 주입되어야 한다.
		PropertyValue dsPv = definition.getPropertyValues().getPropertyValue("dataSource");
		assertNotNull("sqlSession 에 dataSource 속성이 설정되어야 합니다.", dsPv);
		Object dsValue = dsPv.getValue();
		assertTrue("dataSource 는 <property ref=\"...\"/> 형태의 빈 참조여야 합니다.",
				dsValue instanceof RuntimeBeanReference);
		assertEquals("dataSource 는 id=\"dataSource\" 인 빈을 참조해야 합니다.",
				"dataSource", ((RuntimeBeanReference) dsValue).getBeanName());

		// 2) configLocation 은 sql-mapper-config.xml 위치를 가리켜야 한다.
		PropertyValue cfgPv = definition.getPropertyValues().getPropertyValue("configLocation");
		assertNotNull("sqlSession 에 configLocation 속성이 설정되어야 합니다.", cfgPv);
		assertEquals("configLocation 값이 올바르지 않습니다.",
				"classpath:/egovframework/sqlmap/lab/sql-mapper-config.xml",
				unwrapStringValue(cfgPv.getValue()));

		// 3) mapperLocations 는 Mapper XML 패턴을 가리켜야 한다.
		PropertyValue mapPv = definition.getPropertyValues().getPropertyValue("mapperLocations");
		assertNotNull("sqlSession 에 mapperLocations 속성이 설정되어야 합니다.", mapPv);
		assertEquals("mapperLocations 값이 올바르지 않습니다.",
				"classpath*:/egovframework/sqlmap/lab/mappers/**/*.xml",
				unwrapStringValue(mapPv.getValue()));
	}

	/* TODO [02. MyBatis-Spring 연동 02 - Test] MapperConfigurer 등록하십시오. */
	@Test
	public void MapperConfigurer_에_basePackage가_egovframework_lab으로_설정되어야_한다() {
		// 1) MapperConfigurer 빈이 등록되어 있어야 한다.
		String[] beanNames = applicationContext.getBeanNamesForType(MapperConfigurer.class);
		assertTrue("MapperConfigurer 빈이 등록되어야 합니다.", beanNames.length > 0);

		// 2) XML 에 <property name="basePackage" value="..." /> 이 지정되어 있어야 한다.
		ConfigurableListableBeanFactory factory =
				((ConfigurableApplicationContext) applicationContext).getBeanFactory();
		BeanDefinition definition = factory.getBeanDefinition(beanNames[0]);
		PropertyValue pv = definition.getPropertyValues().getPropertyValue("basePackage");
		assertNotNull("MapperConfigurer 에 basePackage 속성이 지정되어야 합니다.", pv);

		// 3) basePackage 값은 "egovframework.lab" 이어야 한다.
		assertEquals("실습용 프로젝트에서는 basePackage 값이 \"egovframework.lab\" 이어야 합니다.",
				"egovframework.lab", unwrapStringValue(pv.getValue()));
	}

	/** XML {@code <property value="..." />} 로 지정된 값은 내부적으로 TypedStringValue 로 래핑되므로 풀어낸다. */
	private static String unwrapStringValue(Object raw) {
		return (raw instanceof TypedStringValue) ? ((TypedStringValue) raw).getValue() : String.valueOf(raw);
	}

	/* 참고용 테스트 메서드
	@Test
	public void Mapper_XML_들이_모두_로드되어야_한다() {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		// DeptMapper.xml: namespace = 풀패키지명
		assertTrue("DeptMapper의 selectDept 가 등록되어야 합니다.",
				configuration.hasStatement("egovframework.lab.dept.service.impl.DeptMapper.selectDept"));
		// EmpMapper.xml: namespace = 짧은 임의의 이름
		assertTrue("EmpMapper의 selectEmp(namespace=Emp) 가 등록되어야 합니다.",
				configuration.hasStatement("Emp.selectEmp"));
	}

	@Test
	public void MapperConfigurer_에_의해_DeptMapper_빈이_등록되어야_한다() {
		assertTrue("@EgovMapper(\"deptMapper\") 가 부착된 DeptMapper 인터페이스가 빈으로 등록되어야 합니다.",
				applicationContext.containsBean("deptMapper"));
	}
	*/
}
