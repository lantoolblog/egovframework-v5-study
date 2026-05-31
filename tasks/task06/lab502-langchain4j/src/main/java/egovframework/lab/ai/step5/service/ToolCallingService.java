package egovframework.lab.ai.step5.service;

/**
 * Step 5. Tool Calling 서비스 인터페이스
 *
 * LangChain4j의 @Tool 어노테이션을 사용.
 * Spring AI의 @Tool과 이름은 같지만 패키지와 파라미터 어노테이션에 차이점 존재.
 *   Spring AI: @Tool(description="..."), @ToolParam(description="...")
 *   LangChain4j: @Tool("..."), @P("...")
 */
public interface ToolCallingService {

    /** [Step 5-4] 현재 날짜/시간 질문 */
    String askDateTime(String question);

    /** [Step 5-5] 특정 날짜의 요일 질문 */
    String askDayOfWeek(String question);
}
