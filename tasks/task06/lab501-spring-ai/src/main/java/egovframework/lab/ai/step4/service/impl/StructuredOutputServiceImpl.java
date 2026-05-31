package egovframework.lab.ai.step4.service.impl;

import egovframework.lab.ai.step4.service.BookInfo;
import egovframework.lab.ai.step4.service.StructuredOutputService;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * Step 4. Structured Output 서비스 구현체
 *
 * <p>ChatClient의 .entity() 메서드를 사용하면 AI 응답을 Java 객체로 바로 받을 수 있다.<br>
 * Spring AI가 내부적으로 record의 구조를 분석하여 JSON 형식 지시를 프롬프트에 추가하고,<br>
 * 응답 JSON을 역직렬화하여 객체를 반환하게 된다.
 */
@Service("structuredOutputService")
public class StructuredOutputServiceImpl implements StructuredOutputService {

  private final ChatClient chatClient;

  public StructuredOutputServiceImpl(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @Override
  public BookInfo getBookInfo(String bookTitle) {
    // TODO Step 4-1: ChatClient Fluent API로 질문하되, .content() 대신 .entity(BookInfo.class)를 사용.
    // <p>
    // .entity(Class<T>) 는 AI 응답을 지정한 클래스의 인스턴스로 역직렬화하여 반환한다.<br>
    // Spring AI가 BookInfo record의 필드를 분석하여 AI에게 다음과 같은 지시를 자동 추가:<br>
    //   "다음 JSON 형식으로 응답하세요: {"title": "...", "author": "...", ...}"
    // .entity(BookInfo.class): Spring AI가 BookInforecord의 필드를 분석하여
    // AI에게 JSON 형식 지시를 자동으로 추가하고, 응답을 BookInfo로 역직렬화.
    return chatClient
        .prompt()
        .user(u -> u.text("'{bookTitle}' 책에 대한 정보를 알려주세요.").param("bookTitle", bookTitle))
        .call()
        .entity(BookInfo.class);
  }

  @Override
  public List<BookInfo> getBooksByAuthor(String authorName) {
    // TODO Step 4-2: List<BookInfo> 형태로 받으려면 ParameterizedTypeReference를 사용.
    // <p>
    // Java의 타입 소거(Type Erasure) 때문에 List<BookInfo>.class 는 사용 불가능.<br>
    // 대신 new ParameterizedTypeReference<List<BookInfo>>() {} 를 사용.
    return chatClient
        .prompt()
        .user(u -> u.text("작가 '{authorName}'의 대표작 3권을 목록으로 알려주세요.").param("authorName", authorName))
        .call()
        .entity(new ParameterizedTypeReference<List<BookInfo>>() {});
  }
}
