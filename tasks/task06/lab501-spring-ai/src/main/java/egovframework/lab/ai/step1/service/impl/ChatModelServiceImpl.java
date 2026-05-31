package egovframework.lab.ai.step1.service.impl;

import egovframework.lab.ai.step1.service.ChatModelService;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

/**
 * Step 1. ChatModel 기본 사용 서비스 구현체
 *
 * <p>ChatModel은 Spring Boot Auto Configuration에 의해 자동으로 Bean으로 등록.<br>
 * application.yml에 설정된 API 키와 모델 정보를 사용하여 AI와 통신을 수행.
 */
@Service("chatModelService")
public class ChatModelServiceImpl implements ChatModelService {

  private final ChatModel chatModel;

  public ChatModelServiceImpl(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  @Override
  public String simpleCall(String message) {
    // TODO Step 1-1: chatModel.call(message) 을 호출하여 AI 응답을 String으로 반환.
    // <p>
    // ChatModel의 call(String) 메서드는 가장 간단한 호출 방법입니다. <br>
    // 단순 문자열을 넣으면 단순 문자열로 돌려줍니다.
    return chatModel.call(message);
  }

  @Override
  public ChatResponse callWithPrompt(String message) {
    // TODO Step 1-2: Prompt와 UserMessage를 사용하여 AI를 호출하고 ChatResponse를 반환.
    //  <p>
    // Prompt 객체는 UserMessage, SystemMessage 등 여러 메시지를 담을 수 있는 컨테이너이다. <br>
    // chatModel.call(Prompt) 는 ChatResponse를 반환한다.<br>
    // ChatResponse에는 응답 내용(generations) 과 메타데이터(토큰 사용량 등)가 포함된다.

    Prompt prompt = new Prompt(new UserMessage(message));
    return chatModel.call(prompt);
  }

  @Override
  public Integer getTotalTokens(String message) {
    // TODO Step 1-3: callWithPrompt()를 호출한 뒤 ChatResponse에서 총 토큰 사용량을 추출.
    // <p>
    // ChatResponse → getMetadata() → getUsage() → getTotalTokens() <br>
    // Spring AI 1.0.x 에서 getTotalTokens()는 Integer를 반환함.<br>
    // 모델에 따라 토큰 정보를 제공하지 않을 수 있으므로 null을 반환할 수 있음.

    ChatResponse response = callWithPrompt(message);
    // Usage: 입력 토큰 + 출력 토큰 합계 (Spring AI 1.0.x에서 Integer 반환)
    return response.getMetadata().getUsage().getTotalTokens();
  }
}
