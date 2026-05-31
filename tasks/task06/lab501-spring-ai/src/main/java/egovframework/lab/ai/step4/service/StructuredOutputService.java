package egovframework.lab.ai.step4.service;

import java.util.List;

/**
 * Step 4. Structured Output 서비스 인터페이스
 *
 * AI 응답을 자연어 문자열이 아닌 Java 객체로 직접 받을 수 있다.
 * Spring AI가 record/클래스 구조를 분석하여 AI에게 JSON 형식 응답을 요청하고,
 * 응답을 자동으로 Java 객체로 역직렬화하게 됨.
 *
 * 장점:
 * - JSON 파싱 코드 불필요
 * - 타입 안전(Type-safe)한 데이터 처리
 * - 컴파일 타임 오류 감지
 */
public interface StructuredOutputService {

    /**
     * 특정 책 제목에 대한 정보를 BookInfo 객체로 반환.
     * ChatClient의 .call().entity(Class) 방식을 사용.
     *
     * @param bookTitle 책 제목
     * @return BookInfo 객체 (title, author, publishYear, genre, keywords)
     */
    BookInfo getBookInfo(String bookTitle);

    /**
     * 특정 작가의 대표작 목록을 List<BookInfo>로 반환.
     * ChatClient의 .call().entity(ParameterizedTypeReference) 방식을 사용.
     *
     * @param authorName 작가 이름
     * @return BookInfo 목록 (최대 3권)
     */
    List<BookInfo> getBooksByAuthor(String authorName);

}
