# polibrary

- 사용자의 인증을 통한 학교 도서관 대리대출 서비스
- 학교 도서관이 자주 닫혀 있어서 도서관 활성화를 바라며 개발
- 개발 인원 총 1명 (웹 1), 웹 개발 담당

## 진행 기간: 2021.04 ~ 2021.06

## 시연영상


[https://www.youtube.com/watch?v=eQ_8Tp8v30Y](https://www.youtube.com/watch?v=eQ_8Tp8v30Y)

## 사용 기술


- Java 8 - 개발 언어
- Spring Framework, Spring Boot - 웹 프레임워크
- Spring Cloud - MSA 전략을 위한 프레임워크
- Gradle - 의존성 관리 프로그램
- MariaDB - 사용자 데이터베이스
- MongoDB - 책 데이터베이스
- Git - 형상관리
- AWS EC2, RDS, Route 53 - 서버 인프라

## 서비스 구성도


![Polibrary서비스구성도.png](https://user-images.githubusercontent.com/64997244/183342386-d1231fbf-69cf-44e1-8322-12550f90ac6d.png)

## 화면설계서


![Polibrary화면설계서.png](https://user-images.githubusercontent.com/64997244/183342388-1d103176-88e3-4679-8841-bbd5b3adb84c.png)

## ERD


![PolibraryERD.png](https://user-images.githubusercontent.com/64997244/183342383-ebb5d3e2-cea3-4036-b776-87c9c1c26646.png)

## 설명

![Polibrary1.png](https://user-images.githubusercontent.com/64997244/183342376-38fae949-a9a6-43cc-bf35-b1581bbf493f.png)

![Polibrary2.png](https://user-images.githubusercontent.com/64997244/183342381-0b83f2f2-6e77-4124-a2ed-500ff73162aa.png)

- 학교 도서관 도서 대리 대출/배송 플랫폼 서비스
- 대출 희망자가 학생증으로 인증을 하고 원하는 책을 대리대출을 요청
- 대리대출자가 요청글을 확인 후 희망자와의 채팅을 통해 배송약속 (위치, 시간 등)
- ~~대리대출 시, 포인트를 지급하여 차후 학식 or 봉사시간으로 활용~~

## 담당 기능

- 서비스 별 DB 분리하고 서로 API로 통신하는 MSA 전략 선택
- BeatifulSoup를 활용해 학교 홈페이지 도서자료 스크래핑
- STOMP를 이용하여 pub/sub 구조로 채팅 기능 구현
- AWS EC2 + RDS + Route53 인프라 구성, 배포, 운영
- WBS, 테이블&프로그램 명세서 등 개발 산출물 작성
