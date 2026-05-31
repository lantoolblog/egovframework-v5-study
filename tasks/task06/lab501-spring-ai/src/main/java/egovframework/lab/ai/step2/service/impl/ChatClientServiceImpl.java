package egovframework.lab.ai.step2.service.impl;

import egovframework.lab.ai.step2.service.ChatClientService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Step 2. ChatClient Fluent API 서비스 구현체
 *
 * <p>ChatClient.Builder는 Spring Boot Auto Configuration에 의해 자동으로 Bean으로 등록.<br>
 * 생성자에서 ChatClient.Builder를 주입받아 ChatClient 인스턴스를 생성.
 */
@Service("chatClientService")
public class ChatClientServiceImpl implements ChatClientService {

  private final ChatClient chatClient;

  public ChatClientServiceImpl(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @Override
  public String chat(String message) {
    // TODO Step 2-1: ChatClient의 Fluent API를 사용하여 AI에게 질문.
    // <p>
    // 체이닝 순서: chatClient.prompt() → .user(message) → .call() → .content()  <br>
    // .content() 는 AI 응답을 String으로 반환한다.
    return chatClient
        .prompt() //
        .user(message)
        .call()
        .content();
  }

  @Override
  public String chatWithSystemPrompt(String systemPrompt, String userMessage) {
    // TODO Step 2-2: .system()으로 AI 역할을 설정하고, .user()로 질문을 전달.
    // <p>
    // .system(systemPrompt) 는 AI의 동작 방식을 정의하는 시스템 메시지이다. <br>
    // 예: "당신은 10년 경력의 Java 전문가입니다. 모든 답변은 한국어로 해주세요."
    return chatClient
        .prompt() //
        .system(systemPrompt)
        .user(userMessage)
        .call()
        .content();
  }

  @Override
  public String chatAboutTopic(String topic) {
    // TODO Step 2-3: .user()의 람다 방식으로 템플릿 변수를 사용.
    // <p>
    // u.text("{topic}에 대해 세 줄로 설명해주세요.").param("topic", topic) 과 같이 <br>
    // 텍스트 안의 {변수명}을 .param(key, value)으로 바인딩할 수 있다.  <br>
    // (Step 3의 PromptTemplate과 유사하지만 ChatClient 내에서 인라인으로 처리)
    // 람다 방식으로 인라인 템플릿 변수 처리
    return chatClient
        .prompt() //
        .user(u -> u.text("{topic}에 대해 세 줄로 설명해주세요.").param("topic", topic))
        .call()
        .content();
  }
}
