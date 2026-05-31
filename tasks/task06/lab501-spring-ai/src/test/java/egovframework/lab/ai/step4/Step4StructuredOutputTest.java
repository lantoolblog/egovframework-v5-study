package egovframework.lab.ai.step4;

import egovframework.lab.ai.step4.service.BookInfo;
import egovframework.lab.ai.step4.service.StructuredOutputService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 4. Structured Output 테스트
 *
 * AI 응답이 Java 객체로 정확히 역직렬화되는지 확인.
 * 응답 내용의 사실 여부보다 객체 구조(필드 존재 여부)를 검증.
 */
@Slf4j
@SpringBootTest
class Step4StructuredOutputTest {

    @Autowired
    StructuredOutputService structuredOutputService;

    /**
     * [Step 4-2] 단일 객체 반환 테스트
     * StructuredOutputServiceImpl.getBookInfo() 구현 후 실행.
     *
     * AI가 BookInfo record의 모든 필드를 채워서 반환하는지 확인하도록 한다.
     * JSON 파싱 코드 없이 바로 Java 객체로 사용할 수 있음을 확인하도록 한다.
     */
    @Test
    void testGetBookInfo() throws Exception {
        String bookTitle = "노인과 바다";

        BookInfo bookInfo = structuredOutputService.getBookInfo(bookTitle);

        log.debug("=== getBookInfo 결과 ===");
        log.debug("제목: {}", bookInfo.title());
        log.debug("저자: {}", bookInfo.author());
        log.debug("출판년도: {}", bookInfo.publishYear());
        log.debug("장르: {}", bookInfo.genre());
        log.debug("키워드: {}", bookInfo.keywords());

        assertThat(bookInfo).isNotNull();
        assertThat(bookInfo.title()).isNotBlank();
        assertThat(bookInfo.author()).isNotBlank();
        assertThat(bookInfo.publishYear()).isGreaterThan(1900);
        assertThat(bookInfo.keywords()).isNotEmpty();
    }

    /**
     * [Step 4-3] List 반환 테스트
     * StructuredOutputServiceImpl.getBooksByAuthor() 구현 후 실행.
     *
     * ParameterizedTypeReference를 사용하는 부분을 확인한다.
     * (Java 타입 소거로 인해 List<BookInfo>.class 사용 불가)
     */
    @Test
    void testGetBooksByAuthor() throws Exception {
        String authorName = "어니스트 헤밍웨이";

        List<BookInfo> books = structuredOutputService.getBooksByAuthor(authorName);

        log.debug("=== getBooksByAuthor 결과 (저자: {}", authorName + ") ===");
        for (int i = 0; i < books.size(); i++) {
            BookInfo book = books.get(i);
            System.out.printf("[%d] %s (%d) - %s%n", i + 1, book.title(), book.publishYear(), book.genre());
            log.debug("    키워드: {}", book.keywords());
        }

        assertThat(books).isNotNull();
        assertThat(books).isNotEmpty();
        books.forEach(book -> {
            assertThat(book.title()).isNotBlank();
            assertThat(book.author()).isNotBlank();
        });
    }

}
