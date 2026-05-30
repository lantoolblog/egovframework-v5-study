package egovframework.lab;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import egovframework.lab.dept.DeptMapperCrudTest;
import egovframework.lab.dept.DeptMapperTest;
import egovframework.lab.dept.MapperInterfaceTest;
import egovframework.lab.emp.EmpDAOTest;
import egovframework.lab.emp.EmpMapperCrudTest;
import egovframework.lab.emp.EmpMapperTest;

/**
 * 전체 테스트를 한 번에 실행하는 JUnit 4 Suite.
 *
 * <h3>실행 방법</h3>
 * <ul>
 *   <li><b>Maven CLI</b>:
 *       <pre>mvn test -Dtest=AllTests</pre>
 *   </li>
 *   <li><b>IDE</b> (Eclipse / IntelliJ / VS Code):
 *       이 파일에서 "Run as JUnit Test" (또는 ▶︎ 아이콘) 실행
 *   </li>
 * </ul>
 *
 * <h3>검증 구조 (평가 순서)</h3>
 * <ol>
 *   <li><b>기반</b> : DataSource 연결 자체 검증</li>
 *   <li><b>설정</b> : MyBatis-Spring 연동 검증, MyBatis Configuration XML 검증</li>
 *   <li><b>Emp 계층</b> :EgovAbstractMapper 상속 방식 검증 (방법 1: iBatis 방식 계승)</li>
 *   <li><b>Dept 계층</b> : Mapper Interface 방식 검증 (방법 2: MyBatis 신규 방식)</li>
 * </ol>
 *
 * <p><b>참고</b>: {@code mvn test} 는 Surefire 가 개별 테스트 클래스를 직접 수집하여
 * 실행하며, 이 Suite 자체는 자체 {@code @Test} 메서드가 없으므로 Surefire 의 테스트
 * 수집 시 건너뛰어진다. 즉 전체를 한 번씩만 실행한다.
 * 따라서 Suite 단독 실행은 {@code mvn test -Dtest=AllTests} 또는 IDE 의 Run 기능을 이용한다.</p>
 */
@RunWith(Suite.class)
@SuiteClasses({
		// [1] 기반 — DataSource 연결 자체 검증
		DataSourceTest.class,

		// [2] 설정 — MyBatis-Spring 연동 검증, MyBatis Configuration XML 검증
		MyBatisSpringConfigTest.class,
		MyBatisConfigTest.class,

		// [3] Emp 계층 — EgovAbstractMapper 상속 방식 검증 (방법 1: iBatis 방식 계승)
		EmpDAOTest.class,
		EmpMapperTest.class,
		//EmpMapperCrudTest.class,

		// [4] Dept 계층 — Mapper Interface 방식 검증 (방법 2: MyBatis 신규 방식)
		MapperInterfaceTest.class,
		DeptMapperTest.class,
		//DeptMapperCrudTest.class,
})
public class AllTests {
}
