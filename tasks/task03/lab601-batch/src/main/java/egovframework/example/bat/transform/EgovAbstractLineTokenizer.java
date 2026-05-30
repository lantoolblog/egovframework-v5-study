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
public abstract class EgovAbstractLineTokenizer implements EgovLineTokenizer {

	/**
	 * doTokenize를 호출하여 Token목록을 생성한다.
	 * @param line
	 * @return List String: token 목록 
	 */
	public List<String> tokenize(String line) throws Exception {
		if (line == null) {
			line = "";
		}
		List<String> tokens = doTokenize(line);
		return tokens;
	}
	
	/**
	 * doTokenize를 호출하여 Encoding Type에 따라 Token목록을 생성한다.
	 * @param line
	 * @param encoding
	 * @return List String: token 목록 
	 */
	public List<String> tokenize(String line, String encoding) throws Exception {
		if (line == null) {
			line = "";
		}
		List<String> tokens = doTokenize(line, encoding);
		return tokens;
	}

	/**
	 * Token 목록을 생성한다. 
	 * 실제 구현은 Token을 만드는 방식에 따라 하위 클래스에서 이루어진다.
	 * @param line
	 * @return List String: token 목록
	 */
	protected abstract List<String> doTokenize(String line) throws Exception;

	/**
	 * Encoding Type에 따른 Token 목록을 생성한다. 
	 * 실제 구현은 Token을 만드는 방식에 따라 하위 클래스에서 이루어진다.
	 * @param line
	 * @return List String: token 목록
	 */
	protected  List<String> doTokenize(String line, String encoding) throws Exception {
		return null;
	}

}