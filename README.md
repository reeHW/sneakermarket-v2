

# 중고신발거래사이트 : SneakerMarket

## 들어가며

### 1. 프로젝트 소개

- 접속 URL : http://ec2-3-34-83-230.ap-northeast-2.compute.amazonaws.com/

Spring Boot를 이용한 개인 프로젝트 입니다.

웹 프로그래밍의 기본이라고 할 수 있는 CRUD 게시판을 구현해보고 하나씩 기능을 추가해보면서 웹 어플리케이션의 동작과정 및 흐름을 이해하고자 프로젝트를 시작하게 되었습니다.

스니커마켓 v2는 스니커마켓 v1 (https://github.com/reeHW/sneakermarket-v1) 의 개선점을 해결하고, Spring Security와 JPA를 적용합니다.
- Spring Security 적용한 로그인 프로세스를 구현하며 사용자 인증과 권한에 대해 이해하고자 했습니다.
- ver.1에서는 SQL Mapper를 이용해서 데이터베이스의 쿼리를 작성해보았습니다. 기존 MyBatis로 작업했던 영역에 JPA 기술을 적용해보면서 ORM에 대해 알게 되고, SQL에 종속되지 않는 보다 더 객체지향적인 개발을 할 수 있었습니다.
- 관심 게시물 기능을 추가했습니다.
- STOMP, WebSocket을 활용한 실시간 채팅 기능을 추가했습니다.
- AWS의 EC2, RDS를 이용하여 배포를 경험했습니다.
- Github Action과 AWS S3을 연동하여 CI/CD 파이프라인을 구축하였습니다.


### 2. 프로젝트의 기능

- 사용자 - OAth 2.0 구글/네이버 로그인, Security 회원가입 및 로그인, 회원가입 시 유효성 검사 및 중복 검사
- 게시판 - CRUD 기능, 조회수, 페이징 및 검색 처리, 파일 업로드, 관심 게시글(좋아요) 등록/취소
- 댓글 - CRUD 기능, 페이징
- 채팅 - 사용자 간의 1:1 실시간 채팅 기능, 채팅 목록 및 채팅 내용 조회

### 3. 사용 기술

**3-1 백엔드**

**주요 프레임워크 / 라이브러리**

- Java 11
- SpringBoot 2.7
- JPA
- MyBatis
- Spring Security
- OAuth 2.0

**Build Tool**

- Gradle 7.6.1

**DataBase**

- MariaDB

**3.2 프론트엔드**

- html/css
- JavaScript
- thymeleaf

**3.3 배포**

- AWS EC2
- AWS RDS


### 4. 실행 화면

### 게시글
![board.gif](images%2Fboard.gif)

- 게시글 목록
    - 전체 목록은 페이징 처리하여 한 페이지에 10개의 게시물을 보여준다.

  <br/>
- 게시글 상세보기
  - 본인이 작성한 게시글이어야 수정, 삭제가 가능하다.
  - 작성자가 아닐 경우 수정, 삭제 버튼이 보이지 않는다.    
  - 뒤로 가기 버튼을 누르면 목록으로 돌아간다.
  
  <br/>
- 게시글 등록
  - 로그인 한 사용자만 게시글 작성이 가능하다. 게시글 저장 후엔 목록으로 redirect한다.
  - 파일 추가 버튼을 통해 첨부파일을 여러개 첨부할 수 있다.
    - 첨부파일은 5개까지 첨부 가능하다.
  
  <br/>
- 게시글 수정
  - 게시글 수정이 완료되면 해당 게시글의 상세보기 화면으로 redirect한다.  

  <br/>
- 게시글 삭제
  - confirm으로 삭제여부를 확인 받고, 삭제 이후 리스트 화면으로 redirect한다.
  
  <br/>
- 관심 게시글(좋아요)
  - 로그인하지 않은 사용자는 관심 게시물을 등록할 수 없다.
  - 사용자가 좋아요를 누르면 즉시 게시글의 총 좋아요 개수에 반영한다.
  - 사용자가 관심 게시글로 등록한 게시글의 리스트를 조회할 수 있다.
  
  <br/>
- 게시글 검색 & 페이징
  - 10 페이지 단위로 보여준다.
  - 해당 페이지 번호는 활성화해서 보여준다.
  - 전체검색, 제목, 내용, 작성자 타입을 구분해서 검색한다.
  - 상세 페이지에서 뒤로 버튼을 클릭하면, 이전의 검색 조건과 페이지 번호가 유지된다.  


<br/>


### 회원가입 / 로그인
![login.gif](images%2Flogin.gif)

- 유효성 검사와 중복을 확인하고 회원 가입이 성공하면 로그인 페이지로 이동한다.

- 로그인이 성공하면 리스트 페이지로 redirect한다.

- OAuth 2.0 소셜 로그인 

    - 구글 계정과 네이버 계정으로 로그인한다.

<br/>

### 댓글

![comment.gif](images%2Fcomment.gif)

- 댓글 작성
    - 댓글은 300자까지 작성할 수 있다. 댓글 작성 성공 후에는 비동기방식으로 현재 페이지를 유지하고 새로 등록된 댓글을 즉시 조회할 수 있다.
    - 해당 댓글 작성자만 수정/삭제가 가능하다.

- 댓글 페이징
  - 10 페이지 단위로 보여준다. 해당 페이지 번호를 활성화 해서 보여준다.

- 댓글 수정/삭제
  - 수정/삭제 후에 메시지를 닫으면, 즉시 댓글에 반영된다.

<br/>

### 채팅
![sneakermarket-chat.gif](images%2Fsneakermarket-chat.gif)

게시판 이용자들 간의 1:1 실시간 채팅이 가능하다.


## 구조 및 설계

### 1. 배포 과정
![deploy.png](images%2Fdeploy.png)

### 2. 패키지 구조

```
── com
    └── sneakermarket
        ├── domain
        │   ├── chat
        │   │   ├── ChatApiController.java
        │   │   ├── ChatController.java
        │   │   ├── ChatDto.java
        │   │   ├── Chat.java
        │   │   ├── ChatRepository.java
        │   │   ├── ChatRoom.java
        │   │   ├── ChatRoomRepository.java
        │   │   └── ChatService.java
        │   ├── comment
        │   │   ├── CommentApiController.java
        │   │   ├── CommentDto.java
        │   │   ├── Comment.java
        │   │   ├── CommentMapper.java
        │   │   ├── CommentRepository.java
        │   │   ├── CommentSearchDto.java
        │   │   └── CommentService.java
        │   ├── file
        │   │   ├── FileApiController.java
        │   │   ├── FileDto.java
        │   │   ├── File.java
        │   │   ├── FileRepository.java
        │   │   └── FileService.java
        │   ├── likes
        │   │   ├── LikePostApiController.java
        │   │   ├── LikePostController.java
        │   │   ├── LikePostDto.java
        │   │   ├── LikePost.java
        │   │   ├── LikePostRepository.java
        │   │   └── LikePostService.java
        │   ├── member
        │   │   ├── MemberController.java
        │   │   ├── MemberDto.java
        │   │   ├── Member.java
        │   │   ├── MemberRepository.java
        │   │   ├── MemberService.java
        │   │   └── Role.java
        │   └── post
        │       ├── PostApiController.java
        │       ├── PostController.java
        │       ├── PostDto.java
        │       ├── Post.java
        │       ├── PostMapper.java
        │       ├── PostRepository.java
        │       ├── PostService.java
        │       └── SaleStatus.java
        ├── global
        │   ├── common
        │   │   ├── dto
        │   │   │   ├── MessageDto.java
        │   │   │   └── SearchDto.java
        │   │   ├── file
        │   │   │   └── FileUtils.java
        │   │   └── paging
        │   │       ├── Pagination.java
        │   │       └── PagingResponse.java
        │   ├── config
        │   │   ├── auth
        │   │   │   ├── CustomUserDetailService.java
        │   │   │   ├── CustomUserDetails.java
        │   │   │   ├── LoggedInMemberArgumentResolver.java
        │   │   │   ├── LoggedInMember.java
        │   │   │   └── LoginFailHandler.java
        │   │   ├── DatabaseConfig.java
        │   │   ├── oauth
        │   │   │   ├── CustomOAuth2UserService.java
        │   │   │   └── OAuthAttributes.java
        │   │   ├── SecurityConfig.java
        │   │   ├── StompConfig.java
        │   │   └── WebMvcConfig.java
        │   ├── interceptor
        │   │   └── LoggerInterceptor.java
        │   └── util
        │       ├── aop
        │       │   └── LoggerAspect.java
        │       └── exception
        │           ├── CustomException.java
        │           ├── ErrorCode.java
        │           ├── ErrorResponse.java
        │           └── ExceptionController.java
        └── MarketApplication.java

```

### 2. DB 설계

![sneakermarket_diagram.png](images%2Fsneakermarket_diagram.png)
![screencapture-notion-so-somethingabout-4b34e693af1e4b6489831651479bd15e-2023-11-02-20_53_37.png](images%2Fscreencapture-notion-so-somethingabout-4b34e693af1e4b6489831651479bd15e-2023-11-02-20_53_37.png)

### 3. API 설계

![api.png](images%2Fapi.png)

<br/>

## 개발 내용
- [DTO ↔ Entity 간 변환, 어느 Layer에서 해야할까?](https://somethingabout.notion.site/DTO-Entity-Layer-fd7ef68fe5244e3a8cc7a0c1d209adac?pvs=4)
- [Spring Security 적용](https://somethingabout.notion.site/Spring-Security-3e4098e571a5451a97d5ccf0f16e145d?pvs=4)
- [글 작성자만 수정, 삭제 가능하게 하기 : JPA 연관관계 매핑](https://somethingabout.notion.site/JPA-c83d0d0e4bdc4a118a180904c798d3f9?pvs=4)
- [관심 게시물 기능 추가](https://somethingabout.notion.site/de3d03b9be8c459a960e808c6786713c?pvs=4)
- [채팅 기능 추가](https://somethingabout.notion.site/WebSocket-Stomp-ccb7e4117e0b4c5a85484d9cf7ba2a55?pvs=4)
- [N+1 문제 해결 : 지연로딩, Pagination의 경우](https://somethingabout.notion.site/N-1-Pagination-fe1e56cf4cbd4e6ebb05b923b3a3a48e?pvs=4)
- [로그인 실패시 메시지 출력하기 : Spring Security - LoginFailuerHandler](https://somethingabout.notion.site/Spring-Security-LoginFailuerHandler-6a2397786b2a497fb2025693f5fbd857?pvs=4)
- [회원가입 유효성 검사 : Validation](https://somethingabout.notion.site/Validation-BindingResult-ce47399db71a4f90b554d801729eb656?pvs=4)
- [예외를 통합 관리 : @ExceptionHandler](https://somethingabout.notion.site/ExceptionHandler-c8ced854d2224991a8f854668e3d8100?pvs=4)










