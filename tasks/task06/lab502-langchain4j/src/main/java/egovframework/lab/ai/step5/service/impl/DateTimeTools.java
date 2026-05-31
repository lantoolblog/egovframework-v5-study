package egovframework.lab.ai.step5.service.impl;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Step 5. Tool Calling - Tool 정의 클래스
 *
 * @Component로 등록하여 Spring Bean으로 관리.
 * @Tool 어노테이션으로 AI가 호출할 수 있는 메서드를 정의.
 *
 * Spring AI 비교:
 *   Spring AI: @Tool(description = "설명")  →  LangChain4j: @Tool("설명")
 *   Spring AI: @ToolParam(description = "설명")  →  LangChain4j: @P("설명")
 */
@Component
public class DateTimeTools {

	// 주의: LangChain4j의 @Tool은 @Tool("description") 형식. (Spring AI의 @Tool(description="...") 와 다름)
    @Tool("현재 날짜와 시간을 반환합니다. 사용자가 현재 시간이나 날짜를 물을 때 사용하세요.")
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // 주의: LangChain4j의 파라미터 설명 어노테이션은 @P . (Spring AI의 @ToolParam(description="...") 와 다름)
    @Tool("특정 날짜의 요일을 반환합니다.")
    public String getDayOfWeek(@P("날짜 (yyyy-MM-dd 형식, 예: 2025-01-01)") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }
}
