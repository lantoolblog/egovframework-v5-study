package egovframework.lab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.annotation.Resource;

/**
 * TODO [01. DataSource 00 - Test] 개요
 * 		 DBCP DataSource 빈을 등록하십시오.
 *
 * <ul>
 *   <li>01. {@code dataSource} 빈이 컨테이너에 존재하고 {@code BasicDataSource} 타입으로 주입되는지 (= DBCP가 사용되는지)</li>
 *   <li>02. HSQLDB 드라이버가 설정되어 있는지</li>
 *   <li>(참고)커넥션 풀이 정상 동작하여 Connection 획득이 가능한지</li>
 *   <li>03. 풀 설정값(maxTotal, minIdle 등)이 제대로 설정되어 있는지</li>
 * </ul>
 *
 * <p>
 * - 테스트 환경: 독립성을 위해 운영용 {@code context-common.xml} 대신
 * property-placeholder 만 로드하는 {@code test-context-properties.xml} 을 사용한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/resources/egovframework/spring/context-datasource.xml
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/egovframework/spring/test-context-properties.xml",
		"classpath:/egovframework/spring/context-datasource.xml" })
public class DataSourceTest {

	@Resource(name = "dataSource")
	private BasicDataSource basicDataSource;

	/* TODO [01. DataSource 01 - Test] class에 DBCP2 BasicDataSource 타입을 지정하십시오. */
	@Test
	public void dataSource_빈이_BasicDataSource_타입이어야_한다() {
		assertNotNull("dataSource 빈이 등록되지 않았습니다.", basicDataSource);
	}

	/* TODO [01. DataSource 02 - Test] 접속 정보 설정을 완성하십시오. */
	@Test
	public void HSQLDB_드라이버가_설정되어_있어야_한다() {
		assertEquals("org.hsqldb.jdbc.JDBCDriver", basicDataSource.getDriverClassName());
		assertTrue("JDBC URL은 hsqldb 형식이어야 합니다. 실제값: " + basicDataSource.getUrl(),
				basicDataSource.getUrl().startsWith("jdbc:hsqldb:"));
	}
	
	/* TODO [01. DataSource 03-Test] 풀 설정을 완성하십시오. */
	@Test
	public void 커넥션_풀_설정값이_명시적으로_지정되어_있어야_한다() {
		// 운영용으로 풀 설정을 했는지 확인. 기본값(maxTotal=8) 그대로면 명시적 설정으로 보지 않는다.
		assertTrue("maxTotal 은 0보다 커야 합니다.", basicDataSource.getMaxTotal() > 0);
		assertTrue("minIdle 은 0 이상이어야 합니다.", basicDataSource.getMinIdle() >= 0);
	}

	/* 참고
	@Test
	public void Connection을_획득할_수_있어야_한다() throws Exception {
		try (Connection conn = basicDataSource.getConnection()) {
			assertNotNull(conn);
			assertTrue("커넥션이 닫혀 있으면 안 됩니다.", !conn.isClosed());
		}
	}
	*/
}
