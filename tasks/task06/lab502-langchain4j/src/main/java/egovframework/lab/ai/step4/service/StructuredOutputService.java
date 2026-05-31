package egovframework.lab.ai.step4.service;

import java.util.List;

/**
 * Step 4. Structured Output 서비스 인터페이스
 *
 * LangChain4j AiServices에서 POJO를 반환 타입으로 선언하면
 * JSON 파싱 코드 없이 타입 안전한 AI 응답 처리가 가능.
 */
public interface StructuredOutputService {

    /** [Step 4-2] 단일 객체 반환 */
    BookInfo getBookInfo(String bookTitle);

    /** [Step 4-3] List 반환 */
    List<BookInfo> getBooksByAuthor(String authorName);
}
