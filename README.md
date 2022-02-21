# MeetHere V1 -> V2

------------
## 여기서 만나 - 백엔드 파트의 전반적인 코드 리팩토링
 
> v1 코드 및 프로젝트 소개 : https://github.com/TEAM-MeetHere/springboot-wwg
------------
## config
+ security 설정 수정
+ swagger 문서 추가(변경 예정)

## controller
+ 불필요한 코드 제거 및 수정
+ controller 역할에 맞춘 코드 리팩토링

## dto, entity
+ 불필요한 set함수 제거
+ success, error 해당 response dto 설정

## errorhandling
+ exception, resolver 설정
+ exception 세분화

## repository
+ JPQL -> Spring Data Jpa, Querydsl
+ Pagination 사용

## service
+ 불필요한 코드 제거 및 수정
+ service 역할에 맞춘 코드 리팩토링

## application
+ 개발환경에 맞춘 application 파일 세분화(local, dev, ...) 및 aws 정보 분리

## branch
+ local branch에서 개발 진행
+ 개발 완료 및 테스트 진행 후 master branch push 예정


