package egovframework.example.bat.transform;
import java.util.List;

/**
 * EgovLineTokenizer 인터페이스
 *
 * @author 실행환경 개발팀 이도형
 * @since 2012.07.20
 * @version 1.0
 * <pre>
 * 개정이력(Modification Information)
 *
 * 수정일		    수정자				수정내용
 * ----------   --------------   ----------------------
 * 2012.07.20	이도형             최초 생성
 * 2023.03.17   신용호             Generic Type 관련 수정
 * </pre>
*/
public interface EgovLineTokenizer {

	/**
	 * Token 목록을 생성한다. 실제 구현은 하위 클래스에서 이루어 진다. 
	 * 
	 * @param line
	 * @return List String : token 목록 
	 */
	List<String> tokenize(String line) throws Exception;

}
