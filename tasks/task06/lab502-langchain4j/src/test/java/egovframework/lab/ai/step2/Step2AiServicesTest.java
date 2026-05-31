package egovframework.lab.ai.step2;

import egovframework.lab.ai.step2.service.AiServicesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 2. AiServices 테스트
 *
 * AiServices는 LangChain4j의 핵심 패턴.
 * 인터페이스 정의만으로 LangChain4j가 구현체를 자동 생성.
 */
@Slf4j
@SpringBootTest
class Step2AiServicesTest {

    @Autowired
    AiServicesService aiServicesService;

    @Test
    void testChat() {
        String response = aiServicesService.chat("안녕하세요. 자기소개를 한 문장으로 해주세요.");

        log.debug("=== chat 응답 ===");
        log.debug("{}", response);

        assertThat(response).isNotBlank();
    }

    @Test
    void testChatWithSystemPrompt() {
        String systemPrompt = "당신은 시인입니다. 모든 답변을 짧은 시 형식으로 작성하세요.";
        String userMessage = "봄에 대해 이야기해주세요.";

        String response = aiServicesService.chatWithSystemPrompt(systemPrompt, userMessage);

        log.debug("=== chatWithSystemPrompt 응답 ===");
        log.debug("시스템 프롬프트: {}", systemPrompt);
        log.debug("응답:\n{}", response);

        assertThat(response).isNotBlank();
    }

    @Test
    void testChatAboutTopic() {
        String topic = "Spring Framework";

        String response = aiServicesService.chatAboutTopic(topic);

        log.debug("=== chatAboutTopic 응답 ===");
        log.debug("토픽: {}", topic);
        log.debug("응답:\n{}", response);

        assertThat(response).isNotBlank();
    }
}
