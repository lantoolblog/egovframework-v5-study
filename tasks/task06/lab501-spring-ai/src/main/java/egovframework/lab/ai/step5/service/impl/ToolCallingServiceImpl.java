package egovframework.lab.ai.step5.service.impl;

import egovframework.lab.ai.step5.service.ToolCallingService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Step 5. Tool Calling 서비스 구현체
 *
 * ChatClient에 Tool 인스턴스를 등록하면 AI가 필요 시 해당 Tool을 호출한다.
 * .tools(toolInstance) 를 사용하여 요청별로 Tool을 지정한다.
 */
@Service("toolCallingService")
public class ToolCallingServiceImpl implements ToolCallingService {

    private final ChatClient chatClient;
    private final DateTimeTools dateTimeTools;

    public ToolCallingServiceImpl(ChatClient.Builder builder, DateTimeTools dateTimeTools) {
        this.chatClient = builder.build();
        this.dateTimeTools = dateTimeTools;
    }

    @Override
    public String askDateTime(String question) {
        // TODO Step 5-1: ChatClient Fluent API에 .tools(dateTimeTools)를 추가하여 Tool을 등록.
        //
        // .tools(Object... toolObjects) 에 Tool이 정의된 객체 인스턴스를 전달한다.
        // AI는 @Tool 어노테이션의 description을 보고 언제 어떤 메서드를 호출할지 판단한다.

        return null;
    }

    @Override
    public String askDayOfWeek(String question) {
        // TODO Step 5-2: askDateTime()과 동일한 방식으로 작성하도록 한다.
        //               동일한 dateTimeTools 인스턴스를 사용.
        //               (getDayOfWeek Tool도 같은 DateTimeTools 클래스에 정의되어 있음.)

        return null;
    }

}
