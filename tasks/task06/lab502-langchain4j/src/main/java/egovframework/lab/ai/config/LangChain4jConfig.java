package egovframework.lab.ai.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4j ChatModel Bean 설정
 *
 * Spring AI는 application.yml 설정만으로 ChatModel Bean이 자동 생성되지만,
 * LangChain4j는 @Bean으로 명시적으로 생성.
 * OpenAiChatModel은 OpenAI 호환 API를 지원하므로 Groq와 함께 사용할 수 있음.
 */
@Configuration
public class LangChain4jConfig {

    @Bean
    public ChatModel chatModel(
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.base-url}") String baseUrl,
            @Value("${gemini.model}") String model) {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(model)
                .maxCompletionTokens(1024)
                .build();
    }
}
