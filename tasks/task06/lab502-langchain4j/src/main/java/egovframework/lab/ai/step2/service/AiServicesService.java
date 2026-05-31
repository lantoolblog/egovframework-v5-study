package egovframework.lab.ai.step2.service;

/**
 * Step 2. AiServices 사용 서비스 인터페이스
 *
 * LangChain4j의 핵심 패턴인 AiServices를 사용.
 * Spring AI의 ChatClient Fluent API에 대응하는 LangChain4j의 고수준 API.
 *
 * AiServices는 인터페이스 정의만으로 LangChain4j가 런타임에 구현체를 자동 생성.
 * Spring Data JPA의 Repository 패턴과 유사한 개발 경험을 제공.
 */
public interface AiServicesService {

    /** [Step 2-2] 기본 AiServices 호출 */
    String chat(String message);

    /** [Step 2-3] 동적 시스템 프롬프트를 사용한 호출 */
    String chatWithSystemPrompt(String systemPrompt, String userMessage);

    /** [Step 2-4] @UserMessage 템플릿 변수를 사용한 호출 */
    String chatAboutTopic(String topic);
}
