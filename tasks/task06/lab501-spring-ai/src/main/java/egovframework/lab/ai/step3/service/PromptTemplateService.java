package egovframework.lab.ai.step3.service;

/**
 * Step 3. PromptTemplate 서비스 인터페이스
 *
 * PromptTemplate은 {변수명} 구문을 사용하여 재사용 가능한 프롬프트를 만드는 기능이다.
 * 동일한 구조의 질문을 다른 값으로 반복 호출할 때 활용 가능하다.
 *
 * SystemPromptTemplate은 PromptTemplate과 동일하지만 SystemMessage를 생성한다.
 * 사용자 질문(UserMessage)과 AI 역할(SystemMessage)을 조합하여 Prompt를 구성할 수 있다.
 */
public interface PromptTemplateService {

    /**
     * PromptTemplate으로 단일 변수를 바인딩하여 질문.
     * 템플릿: "{adjective} 프로그래밍 언어 하나를 추천하고 이유를 설명해주세요."
     *
     * @param adjective 형용사 (예: "함수형", "객체지향적인", "배우기 쉬운")
     * @return AI 응답 문자열
     */
    String askWithSingleVariable(String adjective);

    /**
     * PromptTemplate으로 복수 변수를 바인딩하여 질문.
     * 템플릿: "{country}의 {category} TOP 3를 알려주세요."
     *
     * @param country  국가명 (예: "대한민국", "일본")
     * @param category 카테고리 (예: "관광지", "음식", "축제")
     * @return AI 응답 문자열
     */
    String askWithMultipleVariables(String country, String category);

    /**
     * UserMessage와 SystemMessage를 조합하여 질문.
     * SystemPromptTemplate으로 AI의 역할을, PromptTemplate으로 사용자 질문을 구성.
     *
     * 시스템 템플릿: "당신은 {field} 분야의 전문가입니다. 모든 답변은 한국어로 해주세요."
     * 사용자 템플릿: "{topic}에 대해 핵심만 세 문장으로 설명해주세요."
     *
     * @param field 전문 분야 (예: "소프트웨어 아키텍처", "데이터베이스")
     * @param topic 질문 주제
     * @return AI 응답 문자열
     */
    String askWithSystemAndUser(String field, String topic);

}
