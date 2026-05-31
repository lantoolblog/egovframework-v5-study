package egovframework.lab.ai.step4.service;

import java.util.List;

/**
 * Step 4. Structured Output - AI 응답을 매핑할 Java record
 *
 * record는 Java 16+에서 도입된 불변 데이터 클래스.
 * Spring AI는 이 record의 필드 구조를 분석하여 AI에게 "이 형식으로 JSON을 생성하라"는
 * 지시를 자동으로 프롬프트에 추가한다. 별도 JSON 파싱 코드가 필요하지 않음.
 *
 * @param title       책 제목
 * @param author      저자명
 * @param publishYear 출판 연도
 * @param genre       장르 (예: "소설", "기술서", "자기계발")
 * @param keywords    책의 주요 키워드 목록
 */
public record BookInfo(
        String title,
        String author,
        int publishYear,
        String genre,
        List<String> keywords
) {
}
