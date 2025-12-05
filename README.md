# cosmos
Term Project, Web Server Programming

# 개요
nasa api와 gemini api를 이용해
매일 한장의 천체 사진과 천체 사진 갤러리, 정보 사전을 제공하는 웹 사이트를 제작한다.

# 기능
- APOD(Astronomy Picture Of the Day)
- NASA 이미지 검색
- Gemini를 통한 이미지 태그 생성
- 태그 캐싱
- 우주 정보 Dictionary
- Dictionary 수정 요청

# 페이지 구성
- Home(APOD)
- Gallary
- Dictionary

# 흐름
- APOD
  1. 사용자가 날짜 선택(기본은 오늘) "api/apod?date=2025-12-05"
  2. DB apod 컬렉션에서 해당 날짜 조회
     if 데이터 존재 -> 반환
     else nasa apod api 호출 -> 결과 DB 저장 -> 반환
  3. 렌더링
- Gallary
  1. 초기 진입시 DB에서 랜덤 이미지 20개 반환 "api/gallary/random"
  2. 사용자가 검색 "api/gallary/search?q=moon"
     if 데이터 존재 -> 반환
     else nasa image search api 호출
        if DB에 존재하는 이미지 -> DB에서 태그 조회 후 반영
        else 이미지 gemini에 분석 요청 -> 태그와 사람, nasa 내부 회의 사진인지 판별 -> 천체 사진이라면 태그와 함께 DB 저장
  3. 필터링된 결과 렌더링
- Dictionary
  1. 목록 조회 "api/dictionary"
  2. 상세 조회 "api/dictionary/{id}
  3. 수정 요청 "api/dictionary/request"
  
# 주의 사항
- 데이터베이스는 MongoDB Atlas에 구축
  https://nano5.notion.site/MongoDB-229daf211d428155bec5e3f7c3a02bea?source=copy_link

- Spring Boot를 사용하여 Backend 개발
  https://nano5.notion.site/Backend-229daf211d4281baa4c6e1ea7f02feb9?source=copy_link

- React, Tailwind, React Router, TanStack Query를 사용하여 Backend에 접근하여 리스트를 보여주고 아이템을 클릭하면 세부 화면 출력하는 Frontend 개발
  https://nano5.notion.site/Frontend-229daf211d428175943acae3bbf4c3df?source=copy_link