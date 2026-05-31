package egovframework.lab.ai.step1;

import egovframework.lab.ai.step1.service.ChatModelService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 1. ChatModel 기본 사용 테스트
 *
 * 테스트 실행 전 application.yml의 api-key를 실제 OpenRouter API 키로 변경하도록 한다.
 *
 * 테스트 실행 방법:
 *   각 @Test 메서드를 개별 실행하거나, 클래스 전체를 JUnit으로 실행.
 */
@Slf4j
@SpringBootTest
class Step1ChatModelTest {

    @Autowired
    ChatModelService chatModelService;

    /**
     * [Step 1-2] 단순 문자열 호출 테스트
     * ChatModelServiceImpl.simpleCall() 구현 후 실행.
     */
    @Test
    void testSimpleCall() throws Exception {
        String message = "대한민국의 수도는 어디인가요? 한 문장으로 답해주세요.";

        String response = chatModelService.simpleCall(message);

        log.debug("=== simpleCall 응답 ===");
        log.debug("{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    /**
     * [Step 1-3] Prompt 객체 기반 호출 테스트
     * ChatModelServiceImpl.callWithPrompt() 구현 후 실행.
     */
    @Test
    void testCallWithPrompt() throws Exception {
        String message = "Spring Framework의 핵심 원칙을 두 가지만 알려주세요.";

        ChatResponse response = chatModelService.callWithPrompt(message);

        log.debug("=== callWithPrompt 응답 ===");
        log.debug("응답 내용: {}", response.getResult().getOutput().getText());
        log.debug("모델 정보: {}", response.getMetadata().getModel());

        assertThat(response).isNotNull();
        assertThat(response.getResult()).isNotNull();
        assertThat(response.getResult().getOutput().getText()).isNotBlank();
    }

    /**
     * [Step 1-4] 토큰 사용량 확인 테스트
     * ChatModelServiceImpl.getTotalTokens() 구현 후 실행.
     *
     * 참고: 무료 모델 중 일부는 토큰 사용량을 반환하지 않을 수 있습니다.
     */
    @Test
    void testGetTotalTokens() throws Exception {
        String message = "안녕하세요.";

        Integer totalTokens = chatModelService.getTotalTokens(message);

        log.debug("=== 토큰 사용량 ===");
        log.debug("총 사용 토큰: {}", totalTokens);

        // 일부 모델은 null을 반환할 수 있으므로 null이 아닌 경우에만 검증
        if (totalTokens != null) {
            assertThat(totalTokens).isGreaterThan(0);
        } else {
            log.debug("※ 현재 모델은 토큰 사용량을 제공하지 않습니다.");
        }
    }

}
