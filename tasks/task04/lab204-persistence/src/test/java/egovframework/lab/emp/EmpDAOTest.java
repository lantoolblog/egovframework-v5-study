package egovframework.lab.emp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import egovframework.lab.emp.service.impl.EmpDAO;

/**
 * TODO [04. Emp 계층(iBatis 방식 계승) DAO 00 - Test] 개요
 *       EgovAbstractMapper 상속 방식으로 DAO 를 작성하십시오.
 *
 * <p>
 * EmpDAO 클래스의 <b>구조</b> 만을 검증한다 — Spring / DB 에 의존하지 않는
 * 순수 JUnit 테스트로 구성되어, 다른 테스트 케이스 (예: {@code context-datasource.xml},
 * {@code context-mapper.xml}, {@code EmpMapper.xml} 등) 완료 여부와 무관하게
 * EmpDAO 구현 여부만 집중적으로 검증한다.
 * </p>
 *
 * <ul>
 *   <li>01. {@code @Repository("empDAO")} 어노테이션이 부착되어 있는지</li>
 *   <li>02. {@code EgovAbstractMapper} 를 상속하는지</li>
 *   <li>03. 5개 메서드({@code insertEmp/updateEmp/deleteEmp/selectEmp/selectAllEmp})
 *       의 부모 헬퍼 호출 코드가 주석 없이 활성화되어 있고
 *       {@code UnsupportedOperationException} 이 제거되었는지</li>
 * </ul>
 *
 * <p>
 * - 테스트 환경: 실제 DB CRUD 동작은 {@link egovframework.lab.emp.EmpMapperCrudTest} 가,
 * EmpMapper.xml 자체는 {@link egovframework.lab.emp.EmpMapperTest} 가 담당한다.
 * </p>
 * <p>
 * - 작성할 소스코드: /src/main/java/egovframework/lab/emp/service/impl/EmpDAO.java
 * </p>
 */
public class EmpDAOTest {

	/* TODO [04. Emp 계층(iBatis 방식 계승) DAO 01 - Test] 어노테이션을 통해 빈을 등록하십시오. */
	@Test
	public void EmpDAO_클래스에_Repository_어노테이션이_empDAO_값으로_부착되어야_한다() {
		// @Repository 가 있어야 component-scan 이 EmpDAO 를 발견해 "empDAO" 빈으로 등록한다.
		// 리플렉션으로 직접 확인하여, 누락 시 명확한 실패 메시지를 제공한다.
		Repository annotation = EmpDAO.class.getAnnotation(Repository.class);
		assertNotNull(
				"EmpDAO 클래스에 @Repository 어노테이션이 부착되어야 합니다. "
						+ "component-scan 대상이 되도록 @Repository(\"empDAO\") 를 부착하세요.",
				annotation);

		assertEquals(
				"@Repository 의 value 는 \"empDAO\" 이어야 합니다. "
						+ "이 값이 Spring Bean 이름(= @Resource(name=\"empDAO\")) 으로 사용됩니다.",
				"empDAO", annotation.value());
	}

	/* TODO [04. Emp 계층(iBatis 방식 계승) DAO 02 - Test] DAO가 상속할 추상클래스(Egov로 시작)를 입력하십시오. */
	@Test
	public void EmpDAO_클래스가_EgovAbstractMapper를_상속해야_한다() {
		// EgovAbstractMapper 를 상속해야 SqlSession 이 자동 주입되고,
		// insert/update/delete/selectOne/selectList 헬퍼 메서드를 사용할 수 있다.
		assertTrue(
				"EmpDAO 클래스는 org.egovframe.rte.psl.dataaccess.EgovAbstractMapper 를 상속해야 합니다. "
						+ "상속하지 않으면 SqlSession 주입이 되지 않고 부모 클래스의 SQL 호출 헬퍼도 쓸 수 없습니다.",
				EgovAbstractMapper.class.isAssignableFrom(EmpDAO.class));
	}

	/* TODO [04. Emp 계층(iBatis 방식 계승) DAO 03 - Test] 5개 메서드를 완성하십시오. */
	@Test
	public void EmpDAO_5개_메서드가_모두_활성_코드로_구현되어야_한다() throws Exception {
		// EmpDAO.java 소스 파일을 직접 읽어 주석을 모두 제거한 뒤,
		// 각 메서드에 대해 (1) 부모 헬퍼 호출(return ...) 이 주석 없이 활성 코드로 존재하는지,
		// (2) `throw new UnsupportedOperationException("... 미구현")` 이 삭제 또는 주석 처리되었는지
		// 를 문자열 검사로 검증한다.
		// (리플렉션으로는 "주석 처리 여부"를 구분할 수 없으므로 소스 텍스트 검사 방식을 사용)
		String cleanedSource = stripComments(readEmpDAOSource());

		verifyMethodImplemented(cleanedSource, "insertEmp",
				"return insert(\"Emp.insertEmp\", vo);");
		verifyMethodImplemented(cleanedSource, "updateEmp",
				"return update(\"Emp.updateEmp\", vo);");
		verifyMethodImplemented(cleanedSource, "deleteEmp",
				"return delete(\"Emp.deleteEmp\", empNo);");
		verifyMethodImplemented(cleanedSource, "selectEmp",
				"return selectOne(\"Emp.selectEmp\", empNo);");
		verifyMethodImplemented(cleanedSource, "selectAllEmp",
				"return selectList(\"Emp.selectAllEmp\");");
	}

	/** 주석을 제거한 소스에서 특정 메서드의 (a) 기대 return 코드 존재, (b) UnsupportedOperationException 부재 를 검증. */
	private static void verifyMethodImplemented(String cleanedSource, String methodName, String expectedReturn) {
		assertTrue(
				String.format("EmpDAO#%s() 본문에 `%s` 가 주석 없이 활성 코드로 존재해야 합니다.",
						methodName, expectedReturn),
				cleanedSource.contains(expectedReturn));

		String throwStatement = "throw new UnsupportedOperationException(\""
				+ methodName + " 미구현\")";
		assertFalse(
				String.format("EmpDAO#%s() 본문의 `%s` 은 삭제하거나 주석 처리해야 합니다.",
						methodName, throwStatement),
				cleanedSource.contains(throwStatement));
	}

	/** EmpDAO.java 소스 파일을 UTF-8 로 로드. (Maven Surefire 는 프로젝트 루트를 working directory 로 사용) */
	private static String readEmpDAOSource() throws IOException {
		Path path = Paths.get("src/main/java/egovframework/lab/emp/service/impl/EmpDAO.java");
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	/** 블록 주석({@code /*...*}{@code /})과 라인 주석({@code //...})을 모두 제거해 "활성 코드"만 남긴다. */
	private static String stripComments(String source) {
		// (?s) DOTALL + non-greedy 매칭으로 여러 줄에 걸친 블록 주석까지 제거
		String noBlockComment = source.replaceAll("(?s)/\\*.*?\\*/", "");
		StringBuilder sb = new StringBuilder();
		for (String line : noBlockComment.split("\\r?\\n")) {
			int idx = line.indexOf("//");
			sb.append(idx >= 0 ? line.substring(0, idx) : line);
			sb.append('\n');
		}
		return sb.toString();
	}
}
