package egovframework.lab.ai.step2;

import egovframework.lab.ai.step2.service.ChatClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 2. ChatClient Fluent API 테스트
 *
 * ChatClient의 Fluent API, 시스템 프롬프트, 인라인 템플릿 변수 사용을 확인.
 */
@Slf4j
@SpringBootTest
class Step2ChatClientTest {

    @Autowired
    ChatClientService chatClientService;

    /**
     * [Step 2-2] 기본 Fluent API 호출 테스트
     * ChatClientServiceImpl.chat() 구현 후 실행.
     */
    @Test
    void testChat() throws Exception {
        String message = "Java의 람다 표현식이란 무엇인가요? 두 문장으로 요약해주세요.";

        String response = chatClientService.chat(message);

        log.debug("=== chat 응답 ===");
        log.debug("{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    /**
     * [Step 2-3] 시스템 프롬프트 + 사용자 메시지 테스트
     * ChatClientServiceImpl.chatWithSystemPrompt() 구현 후 실행.
     *
     * 동일한 질문이라도 시스템 프롬프트에 따라 답변 스타일이 달라짐을 확인하도록 한다.
     */
    @Test
    void testChatWithSystemPrompt() throws Exception {
        String systemPrompt = "당신은 초등학생도 이해할 수 있도록 쉽고 친근하게 설명하는 선생님입니다. 모든 답변은 한국어로 해주세요.";
        String userMessage = "데이터베이스 트랜잭션이란 무엇인가요?";

        String response = chatClientService.chatWithSystemPrompt(systemPrompt, userMessage);

        log.debug("=== chatWithSystemPrompt 응답 ===");
        log.debug("시스템 프롬프트: {}", systemPrompt);
        log.debug("응답: {}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    /**
     * [Step 2-4] 인라인 템플릿 변수 테스트
     * ChatClientServiceImpl.chatAboutTopic() 구현 후 실행.
     *
     * topic 변수가 프롬프트에 올바르게 바인딩되는지 확인하도록 한다.
     */
    @Test
    void testChatAboutTopic() throws Exception {
        String topic = "Spring Boot";

        String response = chatClientService.chatAboutTopic(topic);

        log.debug("=== chatAboutTopic 응답 (topic: {}", topic + ") ===");
        log.debug("{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

}
