package egovframework.lab.ai.step4.service.impl;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import egovframework.lab.ai.step4.service.BookInfo;
import egovframework.lab.ai.step4.service.StructuredOutputService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Step 4. Structured Output 서비스 구현체
 *
 * AiServices 인터페이스의 반환 타입을 POJO(record)로 선언하면
 * LangChain4j가 자동으로 JSON 스키마를 생성하고 응답을 역직렬화.
 *
 * Spring AI 비교:
 *   chatClient.prompt()...call().entity(BookInfo.class)
 *   chatClient.prompt()...call().entity(new ParameterizedTypeReference<List<BookInfo>>() {})
 *
 * LangChain4j:
 *   AiServices 인터페이스의 반환 타입으로 BookInfo 또는 List<BookInfo>를 선언
 */
@Service("structuredOutputService")
public class StructuredOutputServiceImpl implements StructuredOutputService {

    private final ChatModel chatModel;

    public StructuredOutputServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // TODO [Step 4-1]: 래퍼 record와 AiServices 내부 인터페이스를 정의.
    // List<BookInfo>를 직접 반환 타입으로 사용하면 IllegalStateException 발생.
    // JSON Schema 표준이 최상위 배열을 지원하지 않으므로, List를 필드로 감싸는 래퍼 record가 필요.
    //
    // 예시:
    //   private record BookInfoList(List<BookInfo> books) {}
    //   private interface BookInfoExtractor {
    //       BookInfo extractBookInfo(@UserMessage String prompt);
    //       BookInfoList extractBookList(@UserMessage String prompt);
    //   }

    @Override
    public BookInfo getBookInfo(String bookTitle) {
        // TODO [Step 4-2]: BookInfoExtractor를 AiServices.builder(...).chatModel(chatModel).build()로 생성하고
        //   extractor.extractBookInfo("'" + bookTitle + "' 책에 대한 정보를 알려주세요.") 를 반환.
        return null;
    }

    @Override
    public List<BookInfo> getBooksByAuthor(String authorName) {
        // TODO [Step 4-3]: BookInfoExtractor를 AiServices.builder(...).chatModel(chatModel).build()로 생성하고
        //   extractor.extractBookList("작가 '" + authorName + "'의 대표작 3권을 목록으로 알려주세요.").books() 를 반환.
        return null;
    }
}
