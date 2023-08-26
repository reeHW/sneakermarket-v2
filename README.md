

# 중고신발거래사이트 : SneakerMarket


## 들어가며

### 1. 프로젝트 소개

웹 프로그래밍의 기본이라고 할 수 있는 CRUD 게시판을 구현해보고 하나씩 기능을 추가해보면서 웹 어플리케이션의 동작과정 및 흐름을 이해하고자 프로젝트를 시작하게 되었습니다.

### 2. 프로젝트의 기능

- 사용자 - Security 회원가입 및 로그인, 회원가입 시 유효성 검사 및 중복 검사
- 게시판 - CRUD 기능, 조회수, 페이징 및 검색 처리, 파일 업로드
- 댓글 - CRUD 기능, 페이징

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
  <img src="./images/리스트 화면.png">

    
  전체 목록은 페이징 처리하여 한 페이지에 10개의 게시물을 보여준다. 
  
  <br/> 
    
#### 2. 게시글 상세보기

  ![localhost_8088_post_view_id=1028](https://github.com/reeHW/sneakermarket-v2/assets/68371436/b92511f8-a39e-4165-b29c-819f39f530d3)

  
  본인이 작성한 게시글이어야 수정, 삭제가 가능하다.     

 ![localhost_8088_post_view_id=1028 (1).png](images%2Flocalhost_8088_post_view_id%3D1028%20%281%29.png)
    
  작성자가 아닐 경우 수정, 삭제 버튼이 보이지 않는다.    
    
  뒤로 가기 버튼을 누르면 목록으로 돌아간다.   
  
  <br/>
    
 #### 3. 게시글 등록
  ![localhost_8088_post_write](https://github.com/reeHW/sneakermarket-v2/assets/68371436/009d011a-664c-47ab-bb01-50af26e84491)
    
  로그인 한 사용자만 게시글 작성이 가능하다. 게시글 저장 후엔 목록으로 redirect한다.
  
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
    
 * 전체검색 결과<br/>
    
![전체 검색](https://github.com/reeHW/sneakermarket-v2/assets/68371436/71504b5a-22e9-4335-bc21-95b6abac5474)

    
게시글의 제목, 내용, 작성자를 모두 포함해서 검색한다.    
    
<br/>

* 제목 검색 결과<br/>

![제목 검색](https://github.com/reeHW/sneakermarket-v2/assets/68371436/d2a7507c-7d89-46f7-8f93-ad228529d603)

    
searchType = title 로 제목을 검색한다.  
    
<br/>

* 작성자 검색 결과<br/>
    
![작성자 검색](https://github.com/reeHW/sneakermarket-v2/assets/68371436/cbe4ed05-38d6-410b-83d2-47dc2caed520)

    
searchType = wrtier로 작성자를 검색한다.  

<br/>

* 페이지 & 검색조건 유지<br/>

![페이징](https://github.com/reeHW/sneakermarket-v2/assets/68371436/95db0267-fc35-40a2-aea7-39a87c30055c)

    
![스크린샷 2023-08-17 201107](https://github.com/reeHW/sneakermarket-v2/assets/68371436/7a049fc2-5fc1-4be1-a347-a3adafd14482)

    
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

![크기변환 회원가입](https://github.com/reeHW/sneakermarket-v2/assets/68371436/c485a3ca-2776-452e-930c-7caf36e288a5)
![크기변환 회원가입 유효성](https://github.com/reeHW/sneakermarket-v2/assets/68371436/8550e34a-acef-4373-aaf6-932331b3b6d5)
![image](https://github.com/reeHW/sneakermarket-v2/assets/68371436/3a1b8cfb-a697-40ec-84a6-09b3853c8a7b)


유효성 검사와 중복을 확인하고 회원 가입이 성공하면 로그인 페이지로 이동한다.

#### 2. 로그인

![로그인](https://github.com/reeHW/sneakermarket-v2/assets/68371436/6b110982-2f6b-4785-adb9-a99b6d617d34)

로그인이 성공하면 리스트 페이지로 redirect한다.

<br/>
</div>
</details>

<br/>

<details>
<summary>댓글</summary>
<div>

#### 1. 댓글 작성

![댓글 작성](https://github.com/reeHW/sneakermarket-v2/assets/68371436/42c73afb-c897-40ae-9a7a-67c297552b0e)

댓글은 300자까지 작성할 수 있다. 댓글 작성 후에는 현재 페이지를 reload 한다.

![댓글 수정 삭제](https://github.com/reeHW/sneakermarket-v2/assets/68371436/6d79abd1-d9cd-4961-8374-5df76edb5658)
![댓글수정삭제2](https://github.com/reeHW/sneakermarket-v2/assets/68371436/69d4fbe8-21d4-4a6f-8a6c-dd7735cab543)

해당 댓글 작성자만 수정/삭제가 가능하다.


#### 2. 댓글 페이징
![image](https://github.com/reeHW/sneakermarket-v2/assets/68371436/12378810-c251-4b6d-974b-483e23afb350)
10 페이지 단위로 보여준다. 해당 페이지 번호를 활성화 해서 보여준다.


#### 3. 댓글 수정/삭제
![스크린샷 2023-08-17 214551](https://github.com/reeHW/sneakermarket-v2/assets/68371436/6db88c9c-fe33-49a3-ad53-7088a1c97a64)

![image](https://github.com/reeHW/sneakermarket-v2/assets/68371436/7dfba29d-9768-4795-9e2a-f1fd1459d8bd)

수정/삭제 후에는 현재 페이지를 reload 한다.



<br/>
</div>
</details>

## 구조 및 설계


### 2. DB 설계
![diagram](https://github.com/reeHW/sneakermarket-v2/assets/68371436/e99a4225-531e-43f7-b1e0-fbc7084ad09c)

![크기변환 post](https://github.com/reeHW/sneakermarket-v2/assets/68371436/afc06790-2874-4c89-902a-b9d5ced61652)
![크기변환 member](https://github.com/reeHW/sneakermarket-v2/assets/68371436/ad5766b3-2f16-42c1-8c84-4347f480ea54)
![크기변환 comment](https://github.com/reeHW/sneakermarket-v2/assets/68371436/72087ee1-ca57-40c9-999c-cd37e7f7ba7e)

![크기변환 file](https://github.com/reeHW/sneakermarket-v2/assets/68371436/6ab90dfc-af42-4d87-b4eb-5e351aec7213)


### 3. API 설계
![image](https://github.com/reeHW/sneakermarket-v2/assets/68371436/a98b769b-0311-4c03-bd8c-56087a34bbdb)

![크기변환 스크린샷 2023-08-21 115645](https://github.com/reeHW/sneakermarket-v2/assets/68371436/a4d69cd0-8533-4f07-a2f3-10a103da92fd)

![크기변환 스크린샷 2023-08-21 114257](https://github.com/reeHW/sneakermarket-v2/assets/68371436/5c7d721a-baa7-4bc9-83b6-520df4611ab2)

![크기변환 스크린샷 2023-08-21 114312](https://github.com/reeHW/sneakermarket-v2/assets/68371436/15a6f5f8-0c98-4f3f-bce8-64999f81f39a)


<br/>






