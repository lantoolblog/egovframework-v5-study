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
 * LangChain4j PromptTemplate은 {{variable}} 이중 중괄호를 사용.
 * Spring AI의 {variable} 단일 중괄호와 비교.
 */
@Slf4j
@SpringBootTest
class Step3PromptTemplateTest {

    @Autowired
    PromptTemplateService promptTemplateService;

    @Test
    void testAskWithSingleVariable() {
        String adjective = "함수형";

        String response = promptTemplateService.askWithSingleVariable(adjective);

        log.debug("=== askWithSingleVariable 응답 ===");
        log.debug("형용사: {}", adjective);
        log.debug("응답:\n{}", response);

        assertThat(response).isNotBlank();
    }

    @Test
    void testAskWithMultipleVariables() {
        String country = "대한민국";
        String category = "관광지";

        String response = promptTemplateService.askWithMultipleVariables(country, category);

        log.debug("=== askWithMultipleVariables 응답 ===");
        log.debug("국가: {}", country + ", 카테고리: " + category);
        log.debug("응답:\n{}", response);

        assertThat(response).isNotBlank();
    }

    @Test
    void testAskWithSystemAndUser() {
        String field = "Java 백엔드 개발";
        String topic = "디자인 패턴";

        String response = promptTemplateService.askWithSystemAndUser(field, topic);

        log.debug("=== askWithSystemAndUser 응답 ===");
        log.debug("전문 분야: {}", field + ", 주제: " + topic);
        log.debug("응답:\n{}", response);

        assertThat(response).isNotBlank();
    }
}
