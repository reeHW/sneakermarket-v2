

# 중고신발거래사이트 : SneakerMarket


## 들어가며

### 1. 프로젝트 소개

웹 프로그래밍의 기본이라고 할 수 있는 CRUD 게시판을 구현해보고 하나씩 기능을 추가해보면서 웹 어플리케이션의 동작과정 및 흐름을 이해하고자 프로젝트를 시작하게 되었습니다.

### 2. 프로젝트의 기능

- 사용자 - OAth 2.0 구글/네이버 로그인, Security 회원가입 및 로그인, 회원가입 시 유효성 검사 및 중복 검사
- 게시판 - CRUD 기능, 조회수, 페이징 및 검색 처리, 파일 업로드
- 댓글 - CRUD 기능, 페이징

## 3. 사용 기술

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

## 4. 실행 화면

<details>
<summary>게시글</summary>
<div>

#### 1. 게시글 목록
  <img src="./images/리스트 화면.png">

    
  전체 목록은 페이징 처리하여 한 페이지에 10개의 게시물을 보여준다. 
  
  <br/> 
    
#### 2. 게시글 상세보기

  ![localhost_8088_post_view_id=1028.png](images%2Flocalhost_8088_post_view_id%3D1028.png)

  
  본인이 작성한 게시글이어야 수정, 삭제가 가능하다.     

 ![localhost_8088_post_view_id=1028 (1).png](images%2Flocalhost_8088_post_view_id%3D1028%20%281%29.png)
    
  작성자가 아닐 경우 수정, 삭제 버튼이 보이지 않는다.    
    
  뒤로 가기 버튼을 누르면 목록으로 돌아간다.   
  
  <br/>
    
 #### 3. 게시글 등록
  
  ![localhost_8088_post_write.png](images%2Flocalhost_8088_post_write.png)  
  로그인 한 사용자만 게시글 작성이 가능하다. 게시글 저장 후엔 목록으로 redirect한다.
  
  <br/>
    

 ![사본 -localhost_8088_post_write (1).png](images%2F%EC%82%AC%EB%B3%B8%20-localhost_8088_post_write%20%281%29.png)
  파일 추가 버튼을 통해 첨부파일을 여러개 첨부할 수 있다.  
    
<br/>
    
#### 4. 게시글 수정

  ![localhost_8088_post_write_id=1031.png](images%2Flocalhost_8088_post_write_id%3D1031.png)
    
  ![수정.png](images%2F%EC%88%98%EC%A0%95.png)

  게시글 수정이 완료되면 해당 게시글의 상세보기 화면으로 redirect한다.  

<br/>
    
#### 5. 게시글 삭제

 ![스크린샷 2023-08-17 194754.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-17%20194754.png)
  

  confirm으로 삭제여부를 확인 받고, 삭제 이후 리스트 화면으로 redirect한다.  

<br/>
    
#### 6. 게시글 검색 & 페이징


![스크린샷 2023-08-17 204419.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-17%20204419.png)
<br/>전체검색, 제목, 내용, 작성자 타입을 구분해서 검색한다.   

<br/>
    
 * 전체검색 결과<br/>
    

![전체 검색.png](images%2F%EC%A0%84%EC%B2%B4%20%EA%B2%80%EC%83%89.png)
    
게시글의 제목, 내용, 작성자를 모두 포함해서 검색한다.    
    
<br/>

* 제목 검색 결과<br/>


![제목 검색.png](images%2F%EC%A0%9C%EB%AA%A9%20%EA%B2%80%EC%83%89.png)
    
searchType = title 로 제목을 검색한다.  
    
<br/>

* 작성자 검색 결과<br/>

![작성자 검색.png](images%2F%EC%9E%91%EC%84%B1%EC%9E%90%20%EA%B2%80%EC%83%89.png)
    
searchType = wrtier로 작성자를 검색한다.  

<br/>

* 페이지 & 검색조건 유지<br/>

![페이징.png](images%2F%ED%8E%98%EC%9D%B4%EC%A7%95.png)

![스크린샷 2023-08-17 201107.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-17%20201107.png)
    
해당 페이지 번호는 활성화해서 보여준다.
<br/>상세 페이지에서 뒤로 버튼을 클릭하면, 이전의 검색 조건과 페이지 번호가 유지된다.  

<br/>

</div>
</details>

<br/>

<details>
<summary>회원</summary>
<div>

#### 1. 회원가입
<br/>

![[크기변환]회원가입.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5D%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png)
![[크기변환]회원가입 유효성.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5D%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85%20%EC%9C%A0%ED%9A%A8%EC%84%B1.png)
![userId_duplication.png](images%2FuserId_duplication.png)

유효성 검사와 중복을 확인하고 회원 가입이 성공하면 로그인 페이지로 이동한다.

#### 2-1. 로그인

![login.png](images%2Flogin.png)

로그인이 성공하면 리스트 페이지로 redirect한다.

#### 2-2. OAuth 2.0 소셜 로그인 

##### 구글 로그인
![google_login.png](images%2Fgoogle_login.png)

##### 네이버 로그인
![naver.login.png](images%2Fnaver.login.png)

구글 계정과 네이버 계정으로 로그인한다.

<br/>
</div>
</details>

<br/>

<details>
<summary>댓글</summary>
<div>

#### 1. 댓글 작성

![댓글 작성.png](images%2F%EB%8C%93%EA%B8%80%20%EC%9E%91%EC%84%B1.png)
댓글은 300자까지 작성할 수 있다. 댓글 작성 후에는 현재 페이지를 reload 한다.

![댓글 수정 삭제.png](images%2F%EB%8C%93%EA%B8%80%20%EC%88%98%EC%A0%95%20%EC%82%AD%EC%A0%9C.png)
![댓글수정삭제2.png](images%2F%EB%8C%93%EA%B8%80%EC%88%98%EC%A0%95%EC%82%AD%EC%A0%9C2.png)

해당 댓글 작성자만 수정/삭제가 가능하다.


#### 2. 댓글 페이징
![스크린샷 2023-08-20 193950.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-20%20193950.png)
10 페이지 단위로 보여준다. 해당 페이지 번호를 활성화 해서 보여준다.


#### 3. 댓글 수정/삭제
![스크린샷 2023-08-17 214551.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-17%20214551.png)
![스크린샷 2023-08-17 214608.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-17%20214608.png)

수정/삭제 후에는 현재 페이지를 reload 한다.



<br/>
</div>
</details>

## 구조 및 설계


### 2. DB 설계
![diagram.png](images%2Fdiagram.png)

![[크기변환]post.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5Dpost.png)
![[크기변환]member.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5Dmember.png)
![[크기변환]file.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5Dfile.png)
![[크기변환]comment.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5Dcomment.png)


### 3. API 설계

![스크린샷 2023-08-21 114119.png](images%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-21%20114119.png)
![[크기변환]스크린샷 2023-08-21 115645.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5D%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-21%20115645.png)
![[크기변환]스크린샷 2023-08-21 114312.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5D%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-21%20114312.png)
![[크기변환]스크린샷 2023-08-21 114257.png](images%2F%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5D%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-21%20114257.png)


<br/>






