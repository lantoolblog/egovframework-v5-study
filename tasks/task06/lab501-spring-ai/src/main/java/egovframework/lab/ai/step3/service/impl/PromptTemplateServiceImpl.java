package egovframework.lab.ai.step3.service.impl;

import egovframework.lab.ai.step3.service.PromptTemplateService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Step 3. PromptTemplate 서비스 구현체
 *
 * PromptTemplate: 사용자 질문(UserMessage) 생성에 사용
 * SystemPromptTemplate: AI 역할 정의(SystemMessage) 생성에 사용
 * 두 메시지를 List로 묶어 Prompt를 구성하고 ChatClient로 전달.
 */
@Service("promptTemplateService")
public class PromptTemplateServiceImpl implements PromptTemplateService {

    private final ChatClient chatClient;

    public PromptTemplateServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String askWithSingleVariable(String adjective) {
        // TODO Step 3-1: PromptTemplate에 단일 변수를 바인딩하여 질문.
        //
        // 1. 템플릿 문자열을 정의합니다. 변수는 {변수명} 형식으로 표기.
        //    예) "{adjective} 프로그래밍 언어 하나를 추천하고 이유를 설명해주세요."
        // 2. new PromptTemplate(templateString) 으로 PromptTemplate을 생성.
        // 3. template.create(Map.of("adjective", adjective)) 로 Prompt를 생성.
        // 4. chatClient.prompt(prompt).call().content() 로 응답을 가져옴.

        return null;
    }

    @Override
    public String askWithMultipleVariables(String country, String category) {
        // TODO Step 3-2: PromptTemplate에 복수 변수를 바인딩하여 질문.
        //
        // Map.of()에 여러 키-값 쌍을 넣어 복수 변수를 동시에 바인딩 가능하다.

        return null;
    }

    @Override
    public String askWithSystemAndUser(String field, String topic) {
        // TODO Step 3-3: SystemPromptTemplate과 PromptTemplate을 조합하여 Prompt를 구성.
        //
        // 1. SystemPromptTemplate으로 systemMessage를 생성.
        //    예) "당신은 {field} 분야의 전문가입니다. 모든 답변은 한국어로 해주세요."
        //    → systemPromptTemplate.createMessage(Map.of("field", field))
        //
        // 2. PromptTemplate으로 userMessage를 생성.
        //    예) "{topic}에 대해 핵심만 세 문장으로 설명해주세요."
        //    → promptTemplate.createMessage(Map.of("topic", topic))
        //
        // 3. 두 메시지를 List.of(userMessage, systemMessage)로 묶어 new Prompt(...)에 전달.

        return null;
    }

}
