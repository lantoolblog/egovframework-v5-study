package egovframework.lab.ai.step2.service.impl;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import egovframework.lab.ai.step2.service.AiServicesService;
import org.springframework.stereotype.Service;
/**
 * Step 2. AiServices 사용 서비스 구현체
 *
 * AiServices 패턴: 내부 인터페이스를 정의하면 LangChain4j가 런타임에 구현체를 자동 생성.
 *
 * Spring AI 비교:
 *   Spring AI: chatClient.prompt().system("...").user("...").call().content()
 *   LangChain4j: 내부 인터페이스 + @SystemMessage/@UserMessage 어노테이션 → AiServices.builder().build()
 */
@Service("aiServicesService")
public class AiServicesServiceImpl implements AiServicesService {

    private final ChatModel chatModel;

    public AiServicesServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // TODO [Step 2-1]: AiServices 내부 인터페이스를 정의.
    // LangChain4j는 이 인터페이스를 런타임에 자동으로 구현.
    //
    // 예시 - 기본 챗봇:
    //   private interface BasicChatbot {
    //       String chat(String userMessage);
    //   }
    //
    // 예시 - 동적 시스템 프롬프트 챗봇:
    //   private interface DynamicSystemChatbot {
    //       @SystemMessage("{{systemPrompt}}")
    //       String chat(@V("systemPrompt") String systemPrompt, @UserMessage String userMessage);
    //   }
    //
    // 예시 - 주제 설명 챗봇:
    //   private interface TopicChatbot {
    //       @UserMessage("{{topic}}에 대해 세 줄로 설명.")
    //       String chat(@V("topic") String topic);
    //   }

    @Override
    public String chat(String message) {
        // TODO [Step 2-2]: AiServices.builder(BasicChatbot.class).chatModel(chatModel).build() 를 사용.
        return null;
    }

    @Override
    public String chatWithSystemPrompt(String systemPrompt, String userMessage) {
        // TODO [Step 2-3]: AiServices.builder(DynamicSystemChatbot.class).chatModel(chatModel).build() 를 사용.
        return null;
    }

    @Override
    public String chatAboutTopic(String topic) {
        // TODO [Step 2-4]: AiServices.builder(TopicChatbot.class).chatModel(chatModel).build() 를 사용.
        return null;
    }
}
