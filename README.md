# Test

- restdocs to oas
  - https://kong-dev.tistory.com/242
  - RESTDOCS mockMvc에서 사용하는 document객체를 epages 패키지꺼로 사용해야함.


- How to
  - RestDocs로 만들기 
    - `./gradlew test`시 `build/generated-snippets`에 `adoc` 문서들이 생성됨.
  - RestDocs로 만든 문서를 OAS로 변환하기
    - `./gradlew openapi3`시 `build/api-spec`에 `openapi3.yaml` 문서가 생성됨.
