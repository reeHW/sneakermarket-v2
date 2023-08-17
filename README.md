# 중고신발거래사이트 : SneakerMarket


## 들어가며

### 1. 프로젝트 소개

웹 프로그래밍의 기본이라고 할 수 있는 CRUD 게시판을 구현해보고 하나씩 기능을 추가해보면서 웹 어플리케이션의 동작과정 및 흐름을 이해하고자 프로젝트를 시작하게 되었습니다.

### 2. 프로젝트의 기능

- 사용자 - Security 회원가입 및 로그인, 회원가입 시 유효성 검사 및 중복 검사
- 게시판 - CRUD 기능, 조회수, 페이징 및 검색 처리, 파일 업로드
- 댓글 - CRUD 기능

## 3. 사용 기술

**3-1 백엔드**

**주요 프레임워크 / 라이브리**

- Java 11
- SpringBoot 2.7
- JPA
- MyBatis
- Spring Security

**Build Tool**

- Gradle 7.6.1

**DataBase**

- MariaDB

**3.2 프론트엔드**

- html/css
- JavaScript
- thymeleaf

## 4. 실행 화면

<details>
<summary>게시글</summary>
<div>

#### 1. 게시글 목록
  ![리스트 화면](https://github.com/reeHW/sneakermarket-v2/assets/68371436/f8bff4f5-ac52-4271-bbde-f5eeb4231463)

    
  전체 목록은 페이징 처리하여 한 페이지에 10개의 게시물을 보여준다. 
  
  <br/> 
    
#### 2. 게시글 상세보기

  ![localhost_8088_post_view_id=1028](https://github.com/reeHW/sneakermarket-v2/assets/68371436/b92511f8-a39e-4165-b29c-819f39f530d3)

  
  본인이 작성한 게시글이어야 수정, 삭제가 가능하다.     

  ![localhost_8088_post_view_id=1028 (1)](https://github.com/reeHW/sneakermarket-v2/assets/68371436/a104708a-9374-4ab3-bff9-0624944bc21b)
    
  작성자가 아닐 경우 수정, 삭제 버튼이 보이지 않는다.    
    
  뒤로 가기 버튼을 누르면 목록으로 돌아간다.   
  
  <br/>
    
 #### 3. 게시글 등록
  ![localhost_8088_post_write](https://github.com/reeHW/sneakermarket-v2/assets/68371436/009d011a-664c-47ab-bb01-50af26e84491)
    
  로그인 한 사용자만 게시글 작성이 가능하다.
  <br/>게시글 저장 후엔 목록으로 redirect한다.
   <br/>
    
  ![사본 -localhost_8088_post_write (1)](https://github.com/reeHW/sneakermarket-v2/assets/68371436/fd0c8d1f-cfce-4a12-9c5d-df56f5d7c7ff)

  파일 추가 버튼을 통해 첨부파일을 여러개 첨부할 수 있다.  
    
<br/>
    
#### 4. 게시글 수정
   ![localhost_8088_post_write_id=1031](https://github.com/reeHW/sneakermarket-v2/assets/68371436/77e21ce1-0116-41b4-b4a4-af89c48eddc7)

    
  ![수정](https://github.com/reeHW/sneakermarket-v2/assets/68371436/15403a8c-ac2b-44b8-b998-851c24d4c28d)

  게시글 수정이 완료되면 해당 게시글의 상세보기 화면으로 redirect한다.  

<br/>
    
#### 5. 게시글 삭제
    
  ![스크린샷 2023-08-17 194754](https://github.com/reeHW/sneakermarket-v2/assets/68371436/592733b7-2b0a-4f8d-ae56-76a341f80f60)

  confirm으로 삭제여부를 확인 받고, 삭제 이후 리스트 화면으로 redirect한다.  

<br/>
    
#### 6. 게시글 검색 & 페이징
    
![스크린샷 2023-08-17 204419](https://github.com/reeHW/sneakermarket-v2/assets/68371436/41d3e421-d924-449d-a28b-eca78da618c2)

    
  전체검색, 제목, 내용, 작성자 타입을 구분해서 검색한다.   

<br/>
    
 * 전체검색 결과
    
![전체 검색](https://github.com/reeHW/sneakermarket-v2/assets/68371436/71504b5a-22e9-4335-bc21-95b6abac5474)

    
게시글의 제목, 내용, 작성자를 모두 포함해서 검색한다.    
    
<br/>

* 제목 검색 결과

![제목 검색](https://github.com/reeHW/sneakermarket-v2/assets/68371436/d2a7507c-7d89-46f7-8f93-ad228529d603)

    
searchType = title 로 제목을 검색한다.  
    
<br/>

* 작성자 검색 결과
    
![작성자 검색](https://github.com/reeHW/sneakermarket-v2/assets/68371436/cbe4ed05-38d6-410b-83d2-47dc2caed520)

    
searchType = wrtier로 작성자를 검색한다.  

<br/>

* 페이지 & 검색조건 유지

![페이징](https://github.com/reeHW/sneakermarket-v2/assets/68371436/95db0267-fc35-40a2-aea7-39a87c30055c)

    
![스크린샷 2023-08-17 201107](https://github.com/reeHW/sneakermarket-v2/assets/68371436/7a049fc2-5fc1-4be1-a347-a3adafd14482)

    
상세 페이지에서 뒤로 버튼을 클릭하면, 이전의 검색 조건과 페이지 번호가 유지된다.  
    

</div>
</details>

## 구조 및 설계

### 1. 패키지 구조

### 2. DB 설계

![market diagram.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0ba4c392-fde2-4b8d-b8bb-68e3b67a768a/market_diagram.png)

### Posts

### 3. API 설계

