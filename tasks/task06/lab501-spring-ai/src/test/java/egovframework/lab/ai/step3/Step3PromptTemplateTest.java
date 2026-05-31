package egovframework.lab.ai.step3;

import egovframework.lab.ai.step3.service.PromptTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step 3. PromptTemplate 테스트
 *
 * 변수 바인딩과 SystemMessage + UserMessage 조합을 확인.
 */
@Slf4j
@SpringBootTest
class Step3PromptTemplateTest {

    @Autowired
    PromptTemplateService promptTemplateService;

    /**
     * [Step 3-2] 단일 변수 바인딩 테스트
     * PromptTemplateServiceImpl.askWithSingleVariable() 구현 후 실행.
     */
    @Test
    void testAskWithSingleVariable() throws Exception {
        String adjective = "함수형";

        String response = promptTemplateService.askWithSingleVariable(adjective);

        log.debug("=== 단일 변수 바인딩 응답 (adjective: {}", adjective + ") ===");
        log.debug("{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    /**
     * [Step 3-3] 복수 변수 바인딩 테스트
     * PromptTemplateServiceImpl.askWithMultipleVariables() 구현 후 실행.
     *
     * country, category 변수가 각각 올바르게 바인딩되는지 확인하도록 한다.
     */
    @Test
    void testAskWithMultipleVariables() throws Exception {
        String country = "대한민국";
        String category = "관광지";

        String response = promptTemplateService.askWithMultipleVariables(country, category);

        log.debug("=== 복수 변수 바인딩 응답 ({}", country + " / " + category + ") ===");
        log.debug("{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

    /**
     * [Step 3-4] SystemMessage + UserMessage 조합 테스트
     * PromptTemplateServiceImpl.askWithSystemAndUser() 구현 후 실행.
     *
     * 시스템 프롬프트에 설정된 전문가 역할이 답변에 반영되는지 확인하도록 한다.
     * 동일한 topic이라도 field가 달라지면 답변 관점이 달라짐을 비교해 보도록 한다.
     */
    @Test
    void testAskWithSystemAndUser() throws Exception {
        String field = "소프트웨어 아키텍처";
        String topic = "마이크로서비스 아키텍처";

        String response = promptTemplateService.askWithSystemAndUser(field, topic);

        log.debug("=== SystemMessage + UserMessage 조합 응답 ===");
        log.debug("전문 분야: {}", field);
        log.debug("질문 주제: {}", topic);
        log.debug("응답:\n{}", response);

        assertThat(response).isNotNull();
        assertThat(response).isNotBlank();
    }

}
