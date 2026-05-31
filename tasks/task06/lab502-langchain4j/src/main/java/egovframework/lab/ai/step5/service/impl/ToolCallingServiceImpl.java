package egovframework.lab.ai.step5.service.impl;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import egovframework.lab.ai.step5.service.ToolCallingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Step 5. Tool Calling 서비스 구현체
 *
 * <pre>
 * AiServices.builder().tools(dateTimeTools)로 Tool을 등록.<br>
 * AI가 질문을 분석하여 필요한 Tool을 자율적으로 선택하고 호출.
 *
 * Spring AI 비교:<br>
 *   chatClient.prompt().user(question).tools(dateTimeTools).call().content()
 *
 * LangChain4j:<br>
 *   AiServices.builder(Chatbot.class).chatModel(model).tools(dateTimeTools).build()
 *   bot.chat(question)
 * </pre>
 */
@Service("toolCallingService")
public class ToolCallingServiceImpl implements ToolCallingService {

  private final ChatModel chatModel;
  private final DateTimeTools dateTimeTools;
  private final String systemPrompt;

  public ToolCallingServiceImpl(
      ChatModel chatModel,
      DateTimeTools dateTimeTools,
      @Value("${gemini.system-prompt}") String systemPrompt) {
    this.chatModel = chatModel;
    this.dateTimeTools = dateTimeTools;
    this.systemPrompt = systemPrompt;
  }

  // TODO [Step 5-3]: AiServices 내부 인터페이스를 정의.
  // Tool Calling에서도 AiServices 패턴을 사용.
  // .tools(dateTimeTools)로 Tool 객체를 등록하면 AI가 필요할 때 자동으로 호출.
  //
  // 예시:
  //   private interface DateTimeChatbot {
  //       String chat(@UserMessage String question);
  //   }
  /** Tool이 등록된 AiServices 챗봇 인터페이스 (어노테이션 대신 systemPromptProvider 사용) */
  private interface DateTimeChatbot {
    String chat(@UserMessage String question);
  }

  @Override
  public String askDateTime(String question) {
    // TODO [Step 5-4]: AiServices.builder(DateTimeChatbot.class)
    //   .chatModel(chatModel)
    //   .tools(dateTimeTools)
    //   .build() 를 사용.
    DateTimeChatbot bot =
        AiServices.builder(DateTimeChatbot.class)
            .chatModel(chatModel)
            .tools(dateTimeTools)
            .systemMessageProvider(chatMemoryId -> systemPrompt) // application.yml에서 읽어온 값 동적 주입!
            .build();
    return bot.chat(question);
  }

  @Override
  public String askDayOfWeek(String question) {
    // TODO [Step 5-5]: askDateTime()과 동일한 방식으로 구현.
    //   (동일한 dateTimeTools에 getDayOfWeek Tool도 함께 등록.)
    DateTimeChatbot bot =
        AiServices.builder(DateTimeChatbot.class)
            .chatModel(chatModel)
            .tools(dateTimeTools)
            .systemMessageProvider(chatMemoryId -> systemPrompt) // 동일하게 적용
            .build();
    return bot.chat(question);
  }
}
