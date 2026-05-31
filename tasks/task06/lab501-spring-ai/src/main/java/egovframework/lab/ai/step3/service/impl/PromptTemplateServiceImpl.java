package egovframework.lab.ai.step3.service.impl;

import egovframework.lab.ai.step3.service.PromptTemplateService;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

/**
 * Step 3. PromptTemplate 서비스 구현체
 *
 * <p>PromptTemplate: 사용자 질문(UserMessage) 생성에 사용<br>
 * SystemPromptTemplate: AI 역할 정의(SystemMessage) 생성에 사용<br>
 * 두 메시지를 List로 묶어 Prompt를 구성하고 ChatClient로 전달.
 */
@Service("promptTemplateService")
public class PromptTemplateServiceImpl implements PromptTemplateService {

  private final ChatClient chatClient;

  public PromptTemplateServiceImpl(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @Override
  public String askWithSingleVariable(String adjective) {
    // TODO Step 3-1: PromptTemplate에 단일 변수를 바인딩하여 질문.
    // <p>
    // 1. 템플릿 문자열을 정의합니다. 변수는 {변수명} 형식으로 표기.<br>
    //    예) "{adjective} 프로그래밍 언어 하나를 추천하고 이유를 설명해주세요."<br>
    // 2. new PromptTemplate(templateString) 으로 PromptTemplate을 생성.<br>
    // 3. template.create(Map.of("adjective", adjective)) 로 Prompt를 생성.<br>
    // 4. chatClient.prompt(prompt).call().content() 로 응답을 가져옴.
    String template = "{adjective} 프로그래밍 언어 하나를 추천하고 이유를 설명해주세요.";
    PromptTemplate promptTemplate = new PromptTemplate(template);
    Prompt prompt = promptTemplate.create(Map.of("adjective", adjective));
    return chatClient.prompt(prompt).call().content();
  }

  @Override
  public String askWithMultipleVariables(String country, String category) {
    // TODO Step 3-2: PromptTemplate에 복수 변수를 바인딩하여 질문.
    // <p>
    // Map.of()에 여러 키-값 쌍을 넣어 복수 변수를 동시에 바인딩 가능하다.
    String template = "{country}의 {category} TOP 3를 알려주세요.";
    PromptTemplate promptTemplate = new PromptTemplate(template);
    Prompt prompt = promptTemplate.create(Map.of("country", country, "category", category));
    return chatClient.prompt(prompt).call().content();
  }

  @Override
  public String askWithSystemAndUser(String field, String topic) {
    // TODO Step 3-3: SystemPromptTemplate과 PromptTemplate을 조합하여 Prompt를 구성.
    // <p>
    // 1. SystemPromptTemplate으로 systemMessage를 생성.<br>
    //    예) "당신은 {field} 분야의 전문가입니다. 모든 답변은 한국어로 해주세요."<br>
    //    → systemPromptTemplate.createMessage(Map.of("field", field))
    // <p>
    // 2. PromptTemplate으로 userMessage를 생성.<br>
    //    예) "{topic}에 대해 핵심만 세 문장으로 설명해주세요."<br>
    //    → promptTemplate.createMessage(Map.of("topic", topic))
    // <p>
    // 3. 두 메시지를 List.of(userMessage, systemMessage)로 묶어 new Prompt(...)에 전달.

    // SystemPromptTemplate으로 AI 역할 메시지 생성
    String systemText = "당신은 {field} 분야의 전문가입니다. 모든 답변은 한국어로 해주세요.";
    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
    Message systemMessage = systemPromptTemplate.createMessage(Map.of("field", field));
    // PromptTemplate으로 사용자 질문 메시지 생성
    String userText = "{topic}에 대해 핵심만 세 문장으로 설명해주세요.";
    PromptTemplate promptTemplate = new PromptTemplate(userText);
    Message userMessage = promptTemplate.createMessage(Map.of("topic", topic));
    // 두 메시지를 조합하여 Prompt 구성
    Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
    return chatClient.prompt(prompt).call().content();
  }
}
