package egovframework.lab.ai.step3.service;

/**
 * Step 3. PromptTemplate 사용 서비스 인터페이스
 *
 * LangChain4j의 PromptTemplate을 사용.
 * Spring AI의 PromptTemplate과 유사하지만 변수 구문이 다릅니다.
 *   Spring AI: {variable}  (단일 중괄호)
 *   LangChain4j: {{variable}} (이중 중괄호)
 */
public interface PromptTemplateService {

    /** [Step 3-1] 단일 변수 바인딩 */
    String askWithSingleVariable(String adjective);

    /** [Step 3-2] 복수 변수 바인딩 */
    String askWithMultipleVariables(String country, String category);

    /** [Step 3-3] 시스템 메시지 + 사용자 메시지 조합 */
    String askWithSystemAndUser(String field, String topic);
}
