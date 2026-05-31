package egovframework.lab.ai.step1.service.impl;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import egovframework.lab.ai.step1.service.ChatModelService;
import org.springframework.stereotype.Service;

/**
 * Step 1. ChatModel 기본 사용 서비스 구현체
 *
 * ChatModel은 LangChain4jConfig에서 @Bean으로 등록한 Bean.
 * Spring AI의 ChatModel과 대응하는 LangChain4j의 저수준 API.
 */
@Service("chatModelService")
public class ChatModelServiceImpl implements ChatModelService {

    private final ChatModel chatModel;

    public ChatModelServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String simpleChat(String message) {
        // TODO [Step 1-1]: chatModel.chat(message) 를 호출하여 문자열을 반환.
        return null;
    }

    @Override
    public ChatResponse chatWithUserMessage(String message) {
        // TODO [Step 1-2]: UserMessage.from(message)로 UserMessage를 생성하고 chatModel.chat()를 호출.
        // chatModel.chat(UserMessage) 반환 타입은 ChatResponse.
        // 응답 텍스트: response.aiMessage().text()
        // 종료 이유: response.finishReason()
        return null;
    }

    @Override
    public Integer getTotalTokens(String message) {
        // TODO [Step 1-3]: chatWithUserMessage()를 호출하고 response.tokenUsage().totalTokenCount()를 반환.
        // tokenUsage()가 null인 경우도 처리.
        return null;
    }
}
