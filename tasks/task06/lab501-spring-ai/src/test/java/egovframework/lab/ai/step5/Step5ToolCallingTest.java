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
 * AI가 실제로 Tool을 호출하는지 확인한다.
 * 현재 날짜/시간은 AI가 학습 데이터로 알 수 없으므로, 반드시 Tool을 통해 가져와야 한다.
 *
 * ※ 주의: Tool Calling은 모든 AI 모델이 지원하지 않음.
 *    application.yml의 모델을 Tool Calling 지원 모델로 변경하고 테스트하도록 한다.
 *    지원 모델 예시: llama-3.3-70b-versatile
 */
@Slf4j
@SpringBootTest
class Step5ToolCallingTest {

    @Autowired
    ToolCallingService toolCallingService;

    /**
     * [Step 5-1] 현재 날짜/시간 Tool Calling 테스트
     *
     * AI가 "현재 시간을 모른다"는 것을 인지하고 getCurrentDateTime() Tool을 호출하는지 확인.
     * 응답에 실제 현재 날짜/시간 정보가 포함되어 있으면 Tool이 정상 동작한 것이다.
     */
    @Test
    void testAskDateTime() throws Exception {
        String question = "지금 몇 시야? 한국 시간 기준으로 알려줘.";

        String response = toolCallingService.askDateTime(question);

        log.debug("=== askDateTime 응답 ===");
        log.debug("질문: {}", question);
        log.debug("응답: {}", response);
        log.debug("※ 응답에 실제 현재 시간이 포함되어 있으면 Tool Calling 성공입니다.");

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    /**
     * [Step 5-2] 요일 조회 Tool Calling 테스트
     *
     * AI가 getDayOfWeek() Tool을 호출하여 날짜 정보를 받아오는지 확인하도록 한다.
     */
    @Test
    void testAskDayOfWeek() throws Exception {
        String question = "2025년 8월 15일은 무슨 요일이야?";

        String response = toolCallingService.askDayOfWeek(question);

        log.debug("=== askDayOfWeek 응답 ===");
        log.debug("질문: {}", question);
        log.debug("응답: {}", response);
        log.debug("※ '금요일'이 응답에 포함되어 있으면 Tool Calling 성공입니다.");

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
        // 2025-08-15는 금요일
        assertThat(response).containsIgnoringCase("금요일");
    }

}
