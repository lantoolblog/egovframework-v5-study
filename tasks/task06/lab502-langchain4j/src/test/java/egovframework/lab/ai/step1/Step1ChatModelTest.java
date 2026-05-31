package egovframework.lab.ai.step1;

import dev.langchain4j.model.chat.response.ChatResponse;
import egovframework.lab.ai.step1.service.ChatModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 1. ChatModel 기본 사용 테스트
 *
 * 테스트 실행 전 application.yml의 gemini.api-key를 실제 Gemini API 키로 변경.
 * Rate Limit이 발생할 경우 테스트를 개별 실행 보도록 한다.
 */
@Slf4j
@SpringBootTest
class Step1ChatModelTest {

    @Autowired
    ChatModelService chatModelService;

    @Test
    void testSimpleChat() {
        String message = "대한민국의 수도는 어디인가요? 한 문장으로 답해주세요.";

        String response = chatModelService.simpleChat(message);

        log.debug("=== simpleChat 응답 ===");
        log.debug("{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    @Test
    void testChatWithUserMessage() {
        String message = "Spring Framework의 핵심 원칙을 두 가지만 알려주세요.";

        ChatResponse response = chatModelService.chatWithUserMessage(message);

        log.debug("=== chatWithUserMessage 응답 ===");
        log.debug("응답 내용: {}", response.aiMessage().text());
        log.debug("종료 이유: {}", response.finishReason());

        assertThat(response).isNotNull();
        assertThat(response.aiMessage().text()).isNotBlank();
    }

    @Test
    void testGetTotalTokens() {
        String message = "안녕하세요.";

        Integer totalTokens = chatModelService.getTotalTokens(message);

        log.debug("=== 토큰 사용량 ===");
        log.debug("총 사용 토큰: {}", totalTokens);

        if (totalTokens != null) {
            assertThat(totalTokens).isGreaterThan(0);
        } else {
            log.debug("※ 현재 모델은 토큰 사용량을 제공하지 않습니다.");
        }
    }
}
