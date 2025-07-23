# 📄 google-sheet-to-json

Google Spreadsheet 데이터를 Java로 불러와 JSON 파일로 저장하는 프로젝트입니다.  
게임 데이터, 설정값, 외부 콘텐츠 등을 스프레드시트로 관리하며 개발 워크플로우를 자동화하고 싶은 분들을 위한 도구입니다.

---

## 🚀 주요 기능

### 🔧 Backend (Java + SparkJava)
- Google Apps Script API를 통해 Google Spreadsheet 데이터 수신
- 🔍 Google Spreadsheet IGNORE 필터 기능 : Google 시트에서 특정 행(row) 또는 열(column)에 "IGNORE" 키워드를 지정하면 자동으로 해당 데이터는 제외되어 처리됩니다.
- 전체 시트 이름 목록 조회
- 시트 선택 → 해당 시트의 데이터를 JSON으로 저장
- JavaPoet을 활용한 자동 Java 클래스 생성 (`/model/SheetName.java`)
- JSON 결과는 `/resources/static/data/SheetName.json`에 저장

### 💻 Frontend (HTML + JS)
- 시트 이름을 드롭다운으로 표시
- 버튼 클릭으로 시트 데이터 요청 및 저장
- 저장 완료 메시지 및 JSON 미리보기 UI 출력

---

## 🖥️ 구현 화면

> ✅ 시트 로딩 완료 → 시트 선택 → JSON 저장 및 클래스 생성  
> ✅ JSON 저장 결과 및 미리보기 화면 출력

![시트 데이터 미리보기](./resources/static/data/screenshots/sheet-preview.png)

---
## 📚 사용 기술
- **Java 17** – 주요 비즈니스 로직 구현
- **SparkJava** – 경량 REST API 서버 구축
- **JavaPoet** – 시트 구조 기반 Java 클래스 자동 생성
- **org.json** – JSON 파싱 및 처리
- **Google Apps Script API** – Google Sheet 데이터를 JSON 형태로 불러오기

### 🌐 Frontend
- **HTML5 / CSS3** – 정적 페이지 구성
- **Vanilla JavaScript (ES6+)** – DOM 조작, 이벤트 핸들링, API 호출

### 📁 프로젝트 구성 및 빌드
- **Gradle** – 프로젝트 빌드 및 의존성 관리
- **VSCode** – 개발 및 디버깅 환경
- **JSON** – 데이터 직렬화 및 저장 포맷

