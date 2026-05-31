package egovframework.lab.ai.step4.service;

import java.util.List;

/**
 * Step 4. AI 응답을 매핑할 Java record
 *
 * LangChain4j AiServices에서 메서드 반환 타입으로 이 record를 선언하면
 * LangChain4j가 자동으로 JSON 스키마를 생성하여 AI에게 해당 형식으로 응답하도록 지시.
 *
 * Spring AI: chatClient.prompt()...call().entity(BookInfo.class)
 * LangChain4j: AiServices 인터페이스의 반환 타입으로 BookInfo를 선언
 */
public record BookInfo(
        String title,
        String author,
        int publishYear,
        String genre,
        List<String> keywords
) {}
