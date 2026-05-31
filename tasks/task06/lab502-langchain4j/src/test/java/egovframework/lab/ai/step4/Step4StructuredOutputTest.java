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
 * AiServices 반환 타입으로 POJO를 선언하면 JSON 파싱 없이 타입 안전하게 응답을 받을 수 있음.
 * Spring AI의 .entity(BookInfo.class) 와 동일한 결과를 다른 방식으로 구현.
 */
@Slf4j
@SpringBootTest
class Step4StructuredOutputTest {

    @Autowired
    StructuredOutputService structuredOutputService;

    @Test
    void testGetBookInfo() {
        String bookTitle = "노인과 바다";

        BookInfo bookInfo = structuredOutputService.getBookInfo(bookTitle);

        log.debug("=== getBookInfo 응답 ===");
        log.debug("제목: {}", bookInfo.title());
        log.debug("저자: {}", bookInfo.author());
        log.debug("출판년도: {}", bookInfo.publishYear());
        log.debug("장르: {}", bookInfo.genre());
        log.debug("키워드: {}", bookInfo.keywords());

        assertThat(bookInfo).isNotNull();
        assertThat(bookInfo.title()).isNotBlank();
        assertThat(bookInfo.author()).isNotBlank();
    }

    @Test
    void testGetBooksByAuthor() {
        String authorName = "어니스트 헤밍웨이";

        List<BookInfo> books = structuredOutputService.getBooksByAuthor(authorName);

        log.debug("=== getBooksByAuthor 응답 ===");
        books.forEach(book -> {
            log.debug("- {}", book.title() + " (" + book.publishYear() + ") by " + book.author());
            log.debug("  키워드: {}", book.keywords());
        });

        assertThat(books).isNotNull();
        assertThat(books).isNotEmpty();
        books.forEach(book -> assertThat(book.title()).isNotBlank());
    }
}
