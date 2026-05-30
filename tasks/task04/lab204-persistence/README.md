# egov-mybatis-lab-student (교육생 실습본)

전자정부 표준프레임워크 5.0 **데이터처리 계층(Data Access Layer)** 실습 프로젝트입니다.

실습의 목표는 다음 한 줄로 요약됩니다.

> **TODO [01] ~ [07] 을 모두 채워서 `AllTests` 가 전부 초록불(GREEN) 이 되도록 만들기.**

테스트가 모두 GREEN 이 되면 아래 네 가지 주제를 직접 손으로 구현해 본 셈이 됩니다.

1. **Spring + DBCP** 로 외부 HSQLDB 서버에 연결하기
2. **MyBatis-Spring 연동** (`SqlSessionFactoryBean` / `MapperConfigurer`)
3. **MyBatis 전역 설정** (`<settings>` / `<typeAliases>`)
4. **Mapper XML 작성 두 가지 방식**
  - 방법 1: `EgovAbstractMapper` 상속 방식 (iBatis 방식 계승) — `EmpDAO` + `EmpMapper.xml`
  - 방법 2: Mapper Interface 방식 (MyBatis 신규 방식) — `DeptMapper.java` + `DeptMapper.xml`

---

## 0. 전체 흐름 한눈에

각 TODO 는 수정할 **메인 파일 1개** 와 그것을 채점해주는 **검증 테스트 파일** 이 1:1 로 연결되어 있습니다.


| #      | 수정할 파일 (`src/main/...`)                                     | 무엇을 하는가                                                                 | 검증 테스트 (`src/test/...`)                |
| ------ | ----------------------------------------------------------- | ----------------------------------------------------------------------- | -------------------------------------- |
| **01** | `resources/egovframework/spring/context-datasource.xml`     | DBCP `BasicDataSource` 빈 등록 (HSQLDB 서버 접속 + 풀 설정)                       | `DataSourceTest`                       |
| **02** | `resources/egovframework/spring/context-mapper.xml`         | `SqlSessionFactoryBean` + `MapperConfigurer` 등록                         | `MyBatisSpringConfigTest`              |
| **03** | `resources/egovframework/sqlmap/lab/sql-mapper-config.xml`  | MyBatis 전역 `<settings>` + `<typeAliases>`                               | `MyBatisConfigTest`                    |
| **04** | `java/egovframework/lab/emp/service/impl/EmpDAO.java`       | `@Repository("empDAO")` + `extends EgovAbstractMapper` + 5개 CRUD 메서드 본문 | `EmpDAOTest`                           |
| **05** | `resources/egovframework/sqlmap/lab/mappers/EmpMapper.xml`  | namespace = `"Emp"` + `selectEmp` 의 `#{empNo}` 바인딩                      | `EmpMapperTest`, `EmpMapperCrudTest`   |
| **06** | `java/egovframework/lab/dept/service/impl/DeptMapper.java`  | `@EgovMapper("deptMapper")` + `updateDeptSelective` 메서드 선언              | `MapperInterfaceTest`                  |
| **07** | `resources/egovframework/sqlmap/lab/mappers/DeptMapper.xml` | namespace = DeptMapper **풀패키지명** + `selectDeptByCondition` Dynamic SQL  | `DeptMapperTest`, `DeptMapperCrudTest` |


> **TIP**. 각 `.xml` / `.java` 파일을 열어보면 최상단에 `TODO [NN. ... 개요]` 블록이 있고,
> 실제로 고쳐야 할 자리마다 `TODO [NN. ... 01]`, `TODO [NN. ... 02]` 같은 작은 주석이 붙어 있습니다.
> **자리표(placeholder)는 `여기에_xxx를_입력하세요` 같은 한글 문자열**로 표시됩니다.

---

## 1. 사전 준비

### 1.1 HSQLDB 서버 기동 (별도 터미널)

본 실습은 **외부에서 띄운 HSQLDB 서버(파일 모드)** 에 JDBC 로 접속합니다.
IDE 에서 테스트를 돌리기 **전에** 반드시 아래 방법으로 서버를 띄워 두세요.

```bash
# Windows: 아래 cmd 파일을 더블클릭
path\to\project\db\runHsqlDB.cmd

# macOS : 아래 명령 실행
$ cd path/to/project/db/
$ chmod +x runHsqlDB.sh
$ ./runHsqlDB.sh
```

다음 메시지가 보이면 준비 완료:

```
HSQLDB server 2.7.4 is online on port 9001
```

> 이 터미널 창은 **실습 내내 계속 켜 둬야 합니다**. 닫으면 테스트가 전부 실패합니다.

동일한 방식으로 `hsqlmanager.cmd`와 `hsqlmanager.sh`를 실행하면, 별도의 화면에서 db 내용을 조회할 수 있습니다.

### 1.2 Maven 의존성 다운로드 (처음 1회)

프로젝트 루트에서:

```bash
mvn -DskipTests install
```

---

## 2. 실습 진행 순서 (권장)

### Step 0 : 현재 상태 확인 — "전부 빨간 것이 정상"

먼저 아무 것도 안 고친 상태로 테스트를 돌려서, **모든 테스트가 FAIL 로 시작** 하는 것을 눈으로 확인하세요.

```bash
mvn test
```

- FAIL/ERROR 메시지에 `TODO [01. ...]`, `TODO [04. ...]` 같은 친절한 힌트가 들어 있습니다.
- 이제 한 단계씩 GREEN 으로 바꿔 가면 됩니다.

### Step 1 : TODO 01 — DataSource

**수정 파일**: `src/main/resources/egovframework/spring/context-datasource.xml`

완성 조건:

- `<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" ...>`
- `driverClassName` / `url` / `username` / `password` 는 `${jdbc.xxx}` 플레이스홀더로 주입
- 풀 설정 4종 (`initialSize`, `maxTotal`, `maxIdle`, `minIdle`)

**확인 방법**:

```bash
mvn test -Dtest=DataSourceTest
```

`Tests run: 4, Failures: 0` 이 나오면 Step 1 통과.

### Step 2 : TODO 02 — MyBatis-Spring 연동

**수정 파일**: `src/main/resources/egovframework/spring/context-mapper.xml`

완성 조건:

- `<bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">` + `dataSource` / `configLocation` / `mapperLocations` 주입
- `<bean class="org.egovframe.rte.psl.dataaccess.mapper.MapperConfigurer">` + `basePackage`

**확인 방법**:

```bash
mvn test -Dtest=MyBatisSpringConfigTest
```

### Step 3 : TODO 03 — MyBatis 전역 설정

**수정 파일**: `src/main/resources/egovframework/sqlmap/lab/sql-mapper-config.xml`

완성 조건:

- `<settings>` 의 `mapUnderscoreToCamelCase = true` 등
- `<typeAliases>` 4개 (`deptVO`, `deptSearchVO`, `empVO`, `egovMap`)

**확인 방법**:

```bash
mvn test -Dtest=MyBatisConfigTest
```

### Step 4 : TODO 04, 05 — iBatis 방식 계승 (Emp 계층, **EgovAbstractMapper 상속**)

**수정 파일**:

- `src/main/java/egovframework/lab/emp/service/impl/EmpDAO.java` (TODO 04)
- `src/main/resources/egovframework/sqlmap/lab/mappers/EmpMapper.xml` (TODO 05)

완성 조건:

- `EmpDAO` 에 `@Repository("empDAO")` 부착, `EgovAbstractMapper` 상속
- 5개 CRUD 메서드 본문의 `// return ...` 주석을 해제하고 `throw new UnsupportedOperationException(...)` 제거
- `EmpMapper.xml` 의 `namespace="Emp"` 지정 + `selectEmp` 의 `WHERE EMP_NO = #{empNo}` 로 바인딩

**확인 방법**:

```bash
mvn test -Dtest=EmpDAOTest
mvn test -Dtest=EmpMapperTest
# 참고 : mvn test -Dtest=EmpMapperCrudTest # 실제 DB CRUD 동작 검증
```

### Step 5 : TODO 06, 07 — MyBatis 신규 방식 (Dept 계층, Mapper Interface)

**수정 파일**:

- `src/main/java/egovframework/lab/dept/service/impl/DeptMapper.java` (TODO 06)
- `src/main/resources/egovframework/sqlmap/lab/mappers/DeptMapper.xml` (TODO 07)

완성 조건:

- `DeptMapper` 인터페이스에 `@EgovMapper("deptMapper")` 부착
- `int updateDeptSelective(DeptVO vo);` 메서드 선언 추가
- `DeptMapper.xml` 의 `namespace` 를 **인터페이스 풀패키지명** (`egovframework.lab.dept.service.impl.DeptMapper`) 과 정확히 일치
- `<select id="selectDeptByCondition">` 의 Dynamic SQL (`<where>` + `<if>`) 작성

**확인 방법**:

```bash
mvn test -Dtest=MapperInterfaceTest
mvn test -Dtest=DeptMapperTest
# 참고 : mvn test -Dtest=DeptMapperCrudTest # 실제 DB CRUD 동작 검증
```

### Step 6 : 통합 검증

```bash
mvn test -Dtest=AllTests
```

`BUILD SUCCESS` + `Failures: 0, Errors: 0` 이면 모든 TODO 완료.

---

## 3. 전체 테스트를 **한 번에** 돌리는 방법

실습 결과를 한 화면에 보여주기 위해 두 가지 방법이 제공됩니다.

### 방법 A. Maven 으로 일괄 실행

```bash
mvn test -Dtest=AllTests
```

- `mvn test`를 하면 Surefire 플러그인이 `src/test/java/**/*Test.java` 를 모두 수집하여 실행합니다.
- 제출과제에는 일부 `*Test.java` 파일은 제외되어 있습니다.
- 따라서 mvn 명령으로 실행시 반드시 `mvn test` 대신 `mvn test -Dtest=AllTests`로 테스트 해주시기 바랍니다.
- 콘솔 끝에 `Tests run: N, Failures: 0, Errors: 0, Skipped: 0` 로 요약됩니다.

### 방법 B. `AllTests` 를 JUnit 런처로 실행 (IDE) — **제출용**

`src/test/java/egovframework/lab/AllTests.java` 파일은 JUnit 4 **Test Suite** 로, 모든 제출과제 테스트를 하나로 묶어 둔 클래스입니다.

- **Eclipse**: `AllTests.java` 우클릭 → `Run As` → `JUnit Test`
- **IntelliJ IDEA**: `AllTests.java` 에디터에서 `▶︎` 클릭 → `Run 'AllTests'`
- **VS Code**: Testing 사이드바에서 `AllTests` 실행

IDE 의 JUnit View 를 켜 두면 왼쪽에 각 테스트 클래스 → 각 테스트 메서드가 트리로 펼쳐지고
**전부 초록색 체크(✓)** 로 표시됩니다. 이 화면이 곧 제출 과제입니다 (§5 참고).

---

## 4. 자주 막히는 포인트 (힌트 & FAQ)

### Q1. `BindingException: Invalid bound statement` 가 떠요

→ **Mapper XML 의 namespace 혹은 statement id 가 Java 와 어긋났을 때** 발생합니다.

- 방법 1(Emp): `EmpMapper.xml` 의 `namespace="Emp"` ↔ `EmpDAO` 가 호출하는 `"Emp.insertEmp"` 형태
- 방법 2(Dept): `DeptMapper.xml` 의 `namespace` ↔ `DeptMapper.class.getName()` **풀패키지명** 정확히 일치

### Q2. `selectDept` 결과가 모두 `null` 이에요

→ TODO 03 (`sql-mapper-config.xml`) 의 `<setting name="mapUnderscoreToCamelCase" value="true" />` 가 빠진 경우입니다.
DB 컬럼 `DEPT_NO` 를 자바 필드 `deptNo` 로 자동 매핑하려면 이 설정이 필요합니다.

### Q3. `CannotLoadBeanClassException: Cannot find class [여기에_class를_입력하십시오]` 같은 에러가 나요

→ TODO 01 의 `<bean id="dataSource" class="여기에_class를_입력하십시오">` 자리가 그대로 남아있다는 뜻입니다.
placeholder 문자열 자체가 클래스명으로 해석되어 실패한 것이니, 올바른 FQCN 으로 교체하세요.

### Q4. `EmpDAOTest` 는 Spring 이 로드되지 않아도 실행되나요?

→ 네. `EmpDAOTest` 는 **순수 JUnit 테스트** 로 재작성되어 있어 `@Repository` / `extends` / 메서드 본문 같은
**EmpDAO 클래스의 구조** 만 검증합니다. 따라서 TODO 01~03 이 아직 미완성이어도 TODO 04 진척도를 독립적으로 확인할 수 있습니다.

### Q5. IDE 의 `AllTests` 는 성공하는데 `mvn test` 는 실패해요

→ `AllTests.java` 에서는 `EmpMapperCrudTest` / `DeptMapperCrudTest` 를 주석처리(//) 했기 때문에 `mvn test`와는 결과가 다를 수 있습니다. IDE의 `AllTests`를 제출해주시기 바랍니다.  
(§3 방법 B 참고)

---

## 5. 제출 과제

다음 **한 장의 스크린샷** 을 제출합니다.

> **IDE 에서 `src/test/java/egovframework/lab/AllTests.java` 를 JUnit 으로 실행했을 때의 JUnit View 결과 화면**

채점 기준:

1. JUnit View 상단 프로그레스 바가 **초록색(전체 통과)** 일 것.
2. 트리에 `AllTests` 아래로 아래 **모든 테스트 클래스** 가 보이고, 각 메서드 옆에 ✓ 가 달려 있을 것:
  - `DataSourceTest`
  - `MyBatisSpringConfigTest`
  - `MyBatisConfigTest`
  - `EmpDAOTest`
  - `EmpMapperTest`
  - `MapperInterfaceTest`
  - `DeptMapperTest`
3. `Failures: 0, Errors: 0`.

### 스크린샷 예시 (Eclipse JUnit View)

```
┌─ JUnit ────────────────────────────────────────────────────┐
│ ■■■■■■■■■■■■■■■■■■■■  Runs: N/N   Errors: 0  Failures: 0   │
│                                                            │
│ ▼ AllTests  [Runner: JUnit 4]                              │
│   ▼ ✓ DataSourceTest                                       │
│   ▼ ✓ MyBatisSpringConfigTest                              │
│   ▼ ✓ MyBatisConfigTest                                    │
│   ▼ ✓ EmpDAOTest                                           │
│   ▼ ✓ EmpMapperTest                                        │
│   ▼ ✓ MapperInterfaceTest                                  │
│   ▼ ✓ DeptMapperTest                                       │
└────────────────────────────────────────────────────────────┘
```

*(숫자는 각자 환경에 따라 다를 수 있습니다. 중요한 것은 **초록색** 과 **Failures/Errors = 0**)*

---

## 6. 디렉토리 구조 (요약)

```
egov-mybatis-lab-student/
├── pom.xml
├── README.md                                   ← (이 문서)
├── src/main/java/egovframework/lab/
│   ├── dept/service/impl/
│   │   └── DeptMapper.java                     ← TODO 06
│   └── emp/service/impl/
│       └── EmpDAO.java                         ← TODO 04
├── src/main/resources/
│   ├── egovframework/spring/
│   │   ├── context-common.xml                  (제공됨 — 수정 금지)
│   │   ├── context-datasource.xml              ← TODO 01
│   │   └── context-mapper.xml                  ← TODO 02
│   ├── egovframework/sqlmap/lab/
│   │   ├── sql-mapper-config.xml               ← TODO 03
│   │   └── mappers/
│   │       ├── EmpMapper.xml                   ← TODO 05
│   │       └── DeptMapper.xml                  ← TODO 07
│   ├── jdbc.properties                         (제공됨)
│   └── log4j2.xml                              (제공됨)
└── src/test/                                    (모두 제공됨 — 수정 금지)
    ├── java/egovframework/lab/
    │   ├── AllTests.java                        ← 제출용 Suite
    │   ├── DataSourceTest.java
    │   ├── MyBatisSpringConfigTest.java
    │   ├── MyBatisConfigTest.java
    │   ├── dept/
    │   │   ├── MapperInterfaceTest.java
    │   │   ├── DeptMapperTest.java
    │   │   └── DeptMapperCrudTest.java
    │   └── emp/
    │       ├── EmpDAOTest.java
    │       ├── EmpMapperTest.java
    │       └── EmpMapperCrudTest.java
    └── resources/
        └── sample_schema_hsql.sql               ← @Before 마다 DB 초기화
```

---

## 7. 트러블슈팅 한 줄 가이드


| 증상                                                            | 원인 (가능성 순)                                               |
| ------------------------------------------------------------- | -------------------------------------------------------- |
| `java.net.ConnectException: Connection refused`               | HSQLDB 서버가 안 떠 있음 — §1.1                                 |
| `CannotLoadBeanClassException`                                | `context-*.xml` 안에 `여기에_xxx를_입력하십시오` 가 남아있음 — TODO 01/02 |
| `NoSuchBeanDefinitionException: dataSource`                   | `context-datasource.xml` 의 bean id 가 `"dataSource"` 가 아님 |
| `NoSuchBeanDefinitionException: sqlSession`                   | `context-mapper.xml` 의 bean id 가 `"sqlSession"` 이 아님     |
| `NoSuchBeanDefinitionException: deptMapper`                   | TODO 06 의 `@EgovMapper("deptMapper")` 누락                 |
| `NoSuchBeanDefinitionException: empDAO`                       | TODO 04 의 `@Repository("empDAO")` 누락 또는 컴포넌트 스캔 범위 밖     |
| `BindingException: Invalid bound statement`                   | TODO 05/07 의 namespace 또는 statement id 오타                |
| `selectXxx` 결과가 모두 `null`                                     | TODO 03 의 `mapUnderscoreToCamelCase=true` 누락             |
| `TypeAliasesException: Could not resolve type alias 'deptVO'` | TODO 03 의 `<typeAliases>` 누락                             |
| `UnsupportedOperationException: insertEmp 미구현`                | TODO 04 의 EmpDAO 5개 메서드 본문을 아직 수정 안 함                    |


---

감사합니다.