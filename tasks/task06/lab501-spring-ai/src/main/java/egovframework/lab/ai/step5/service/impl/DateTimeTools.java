package egovframework.lab.ai.step5.service.impl;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Step 5. Tool Calling - Tool 정의 클래스
 *
 * @Tool 어노테이션이 붙은 메서드는 AI가 필요할 때 호출할 수 있는 "도구"로 등록된다.
 * LLM은 사용자 질문을 분석하여 어떤 Tool을 언제 사용할지 스스로 판단.
 *
 * 동작 흐름:
 *   1. AI가 Tool 사용이 필요하다고 판단
 *   2. Tool Calling Request를 애플리케이션으로 전달
 *   3. 애플리케이션이 해당 Java 메서드를 실제 실행
 *   4. 실행 결과를 AI에게 반환
 *   5. AI가 결과를 자연어로 변환하여 최종 응답 생성
 *
 */
@Component
public class DateTimeTools {

    @Tool(description = "현재 날짜와 시간을 반환합니다. 사용자가 현재 시간이나 날짜를 물을 때 사용하세요.")
    public String getCurrentDateTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
    }

    @Tool(description = "특정 날짜의 요일을 반환합니다.")
    public String getDayOfWeek(
            @ToolParam(description = "날짜 (yyyy-MM-dd 형식, 예: 2025-01-01)") String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date + "T00:00:00");
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

}
