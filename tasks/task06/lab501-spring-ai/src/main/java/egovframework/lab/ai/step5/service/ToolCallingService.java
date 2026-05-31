package egovframework.lab.ai.step5.service;

/**
 * Step 5. Tool Calling 서비스 인터페이스
 *
 * Tool Calling은 AI가 외부 도구(Java 메서드)를 호출할 수 있는 기능이다.
 * AI는 사용자 질문을 분석하여 필요한 Tool을 스스로 선택하고 호출하게 된다.
 *
 * 핵심 개념:
 * - AI는 Tool의 실행을 "요청"만 한다.
 * - 실제 실행은 애플리케이션(Java 코드)이 담당.
 * - 실행 결과를 다시 AI에게 전달하면 AI가 자연어 응답을 생성.
 *
 * 대표적인 활용 사례:
 * - 현재 날씨/시간 조회 (AI는 실시간 정보를 모름)
 * - 데이터베이스 조회
 * - 외부 API 호출
 * - 계산 수행
 */
public interface ToolCallingService {

    /**
     * AI에게 현재 날짜/시간 관련 질문을 하여 Tool Calling을 유도.
     * AI가 DateTimeTools.getCurrentDateTime() Tool을 호출하도록 유도한다.
     *
     * @param question 사용자 질문 (예: "지금 몇 시야?", "오늘 날짜가 어떻게 돼?")
     * @return AI의 자연어 응답 (Tool 호출 결과를 포함)
     */
    String askDateTime(String question);

    /**
     * AI에게 특정 날짜의 요일을 물어보는 질문을 하여 Tool Calling을 유도.
     * AI가 DateTimeTools.getDayOfWeek() Tool을 호출하도록 유도한다.
     *
     * @param question 사용자 질문 (예: "2025년 8월 15일은 무슨 요일이야?")
     * @return AI의 자연어 응답 (Tool 호출 결과를 포함)
     */
    String askDayOfWeek(String question);

}
