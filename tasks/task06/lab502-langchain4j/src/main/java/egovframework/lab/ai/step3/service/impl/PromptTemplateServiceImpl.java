package egovframework.lab.ai.step3.service.impl;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import egovframework.lab.ai.step3.service.PromptTemplateService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Step 3. PromptTemplate 사용 서비스 구현체
 *
 * LangChain4j의 PromptTemplate은 {{variable}} 이중 중괄호 구문을 사용.
 * Spring AI의 PromptTemplate({variable} 단일 중괄호)과 비교.
 *
 * Spring AI 비교:
 *   PromptTemplate template = new PromptTemplate("{adjective} 언어를 추천.");
 *   Prompt prompt = template.create(Map.of("adjective", adjective));
 *   return chatClient.prompt(prompt).call().content();
 *
 * LangChain4j:
 *   PromptTemplate template = PromptTemplate.from("{{adjective}} 언어를 추천.");
 *   Prompt prompt = template.apply(Map.of("adjective", adjective));
 *   return chatModel.chat(prompt.toUserMessage()).aiMessage().text();
 */
@Service("promptTemplateService")
public class PromptTemplateServiceImpl implements PromptTemplateService {

    private final ChatModel chatModel;

    public PromptTemplateServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String askWithSingleVariable(String adjective) {
        // TODO [Step 3-1]: PromptTemplate.from("{{adjective}} 프로그래밍 언어 하나를 추천하고 이유를 설명.")
        //   로 템플릿을 생성하고 template.apply(Map.of("adjective", adjective))로 Prompt를 생성.
        // 주의: LangChain4j는 {{variable}} 이중 중괄호를 사용. (Spring AI의 {variable}과 다름)
        // 마지막으로 chatModel.chat(prompt.toUserMessage()).aiMessage().text()를 반환.
        return null;
    }

    @Override
    public String askWithMultipleVariables(String country, String category) {
        // TODO [Step 3-2]: PromptTemplate.from("{{country}}의 {{category}} TOP 3를 알려주세요.")
        return null;
    }

    @Override
    public String askWithSystemAndUser(String field, String topic) {
        // TODO [Step 3-3]: 시스템 메시지와 사용자 메시지를 각각 PromptTemplate으로 생성.
        return null;
    }
}
