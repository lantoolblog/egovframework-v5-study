package egovframework.lab.ai.step2.service;

/**
 * Step 2. ChatClient Fluent API 서비스 인터페이스
 *
 * ChatClient는 ChatModel보다 고수준의 Fluent API를 제공한다.
 * 메서드 체이닝 방식으로 직관적인 코드 작성이 가능하며,
 * 시스템 프롬프트, 사용자 메시지, 옵션 설정 등을 한 번에 구성 가능.
 */
public interface ChatClientService {

    /**
     * 사용자 질문을 받아 AI 응답을 문자열로 반환.
     * ChatClient의 Fluent API를 사용.
     *
     * @param message 사용자 질문
     * @return AI 응답 문자열
     */
    String chat(String message);

    /**
     * 시스템 프롬프트(AI 역할 정의)와 사용자 질문을 함께 전달.
     * 시스템 프롬프트로 AI의 답변 방식과 역할을 제어 가능하다.
     *
     * @param systemPrompt AI 역할 정의 (예: "당신은 친절한 Java 전문가입니다.")
     * @param userMessage  사용자 질문
     * @return AI 응답 문자열
     */
    String chatWithSystemPrompt(String systemPrompt, String userMessage);

    /**
     * 인라인 템플릿 변수를 사용하여 질문.
     * ChatClient의 .user(u -> u.text(...).param(...)) 방식을 사용.
     *
     * @param topic 질문할 주제
     * @return AI 응답 문자열
     */
    String chatAboutTopic(String topic);

}
