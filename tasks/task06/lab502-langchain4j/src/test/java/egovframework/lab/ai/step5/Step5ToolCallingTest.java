package egovframework.lab.ai.step5;

import egovframework.lab.ai.step5.service.ToolCallingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 5. Tool Calling 테스트
 *
 * 테스트 실행 전 application.yml의 groq.model을 llama-3.3-70b-versatile로 변경.
 * llama-3.1-8b-instant는 Tool Calling을 안정적으로 지원하지 않을 수 있음.
 */
@Slf4j
@SpringBootTest
class Step5ToolCallingTest {

    @Autowired
    ToolCallingService toolCallingService;

    @Test
    void testAskDateTime() {
        String question = "지금 몇 시야?";

        String response = toolCallingService.askDateTime(question);

        log.debug("=== askDateTime 응답 ===");
        log.debug("질문: {}", question);
        log.debug("응답: {}", response);

        assertThat(response).isNotBlank();
    }

    @Test
    void testAskDayOfWeek() {
        String question = "2025년 3월 1일은 무슨 요일인가요?";

        String response = toolCallingService.askDayOfWeek(question);

        log.debug("=== askDayOfWeek 응답 ===");
        log.debug("질문: {}", question);
        log.debug("응답: {}", response);

        // 2025-03-01은 토요일
        assertThat(response).isNotBlank();
        assertThat(response).containsIgnoringCase("토");
    }
}
