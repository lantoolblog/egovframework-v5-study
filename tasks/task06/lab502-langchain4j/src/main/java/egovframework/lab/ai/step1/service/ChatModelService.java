package egovframework.lab.ai.step1.service;

import dev.langchain4j.model.chat.response.ChatResponse;

/**
 * Step 1. ChatModel 기본 사용 서비스 인터페이스
 *
 * Spring AI의 ChatModel에 대응하는 LangChain4j의 ChatModel을 사용.
 */
public interface ChatModelService {

    /**
     * [Step 1-1] 가장 간단한 문자열 호출
     * chatModel.chat(String) 사용 → String 반환
     */
    String simpleChat(String message);

    /**
     * [Step 1-2] UserMessage를 명시적으로 생성하여 호출
     * chatModel.chat(UserMessage) 사용 → ChatResponse 반환
     * ChatResponse에는 응답 텍스트 외에 토큰 사용량, 종료 이유 등이 포함.
     * Spring AI 비교: chatModel.call(new Prompt(new UserMessage(message))) → ChatResponse
     */
    ChatResponse chatWithUserMessage(String message);

    /**
     * [Step 1-3] 토큰 사용량 반환
     * response.tokenUsage().totalTokenCount() 사용
     */
    Integer getTotalTokens(String message);
}
