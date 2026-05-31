package egovframework.lab.ai.step1.service;

import org.springframework.ai.chat.model.ChatResponse;

/**
 * Step 1. ChatModel 기본 사용 서비스 인터페이스
 *
 * Spring AI의 가장 기초적인 API인 ChatModel을 사용하여 AI에게 질문.
 * ChatModel은 다양한 AI 모델(OpenAI, Anthropic, Ollama 등)에 대한 통합 인터페이스로,
 * application.yml 설정만 변경하면 모델 교체가 가능.
 */
public interface ChatModelService {

    /**
     * 단순 문자열로 AI에게 질문하고 응답을 문자열로 반환.
     *
     * @param message 사용자 질문
     * @return AI 응답 문자열
     */
    String simpleCall(String message);

    /**
     * Prompt 객체를 사용하여 AI에게 질문하고 ChatResponse를 반환.
     * ChatResponse에는 응답 내용뿐 아니라 토큰 사용량 등 메타데이터가 포함됨.
     *
     * @param message 사용자 질문
     * @return ChatResponse (응답 + 메타데이터)
     */
    ChatResponse callWithPrompt(String message);

    /**
     * AI 응답에서 사용된 토큰 수(입력 + 출력)의 합계를 반환.
     *
     * @param message 사용자 질문
     * @return 총 사용 토큰 수
     */
    Integer getTotalTokens(String message);

}
