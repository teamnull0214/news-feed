![image](https://github.com/user-attachments/assets/f008fea3-f33e-4f89-a9cf-f55ef5c51003)# 📘 기초 프로젝트 - 뉴스피드 프로젝트

  * 게시글 및 댓글 작성, 팔로우 기능, 좋아요 기능이 있는 뉴스피드 서버 프로그램을 개발합니다.
  * JPA를 바탕으로 뉴스피드 앱을 구현하는 과제입니다.
  * Postman을 이용한 요청 및 응답으로 일정을 CRUD 및 DB에 저장할 수 있습니다.

* 개발 기간 : 25.02.14 ~ 25.02.20 (7일)
 
* 발표자료: https://www.canva.com/design/DAGffUxgFnU/vDAZVQAcd2H2Xh-ZFEU8Ew/view?utm_content=DAGffUxgFnU&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h7761aaa551

* 개발 환경
  * development : IntelliJ IDEA, git, github
  * environment : JAVA JDK 17, Spring Boot 3.4.2, JPA, MySQL, swagger 2.3.0

<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
 

<br><br>


# 🖼️ API 명세서, ERD 다이어그램


* 상세 API 명세서(설계단계)
https://flaxen-swan-41e.notion.site/API-19fb649ebbbd80848818c2ada3e92982


* ERD 다이어그램
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FYUDTr%2FbtsMnAaH7fR%2Fx1dVP8GvmPNAnGpKNYynr0%2Fimg.png">
  

* 와이어 프레임
<img src="https://files.slack.com/files-pri/T06B9PCLY1E-F08DYMZK0SJ/image.png">


* 유스케이스
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbzItx7%2FbtsMplQAxAo%2FGQA5qzG0PQyYXbJcarKvMk%2Fimg.png">

<br><br>



# 💁 팀원 소개 및 역할 분담


|                            남윤재                            |                            박현승                            |                            서지원                            |                            이지은                            |                            이호준                            |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|          [Github](https://github.com/yjn33)        |            [Github](https://github.com/hyeons22)          |           [Github](https://github.com/jiwonclvl)           |             [Github](https://github.com/queenriwon)             |          [Github](https://github.com/ComfyTime)          |
|          [Blog](https://computersystem.tistory.com/)        |            [Blog](https://ski0123.tistory.com/)          |           [Blog](https://velog.io/@clvl1004/posts)           |             [Blog](https://queenriwon3.tistory.com/)             |          [Blog](https://velog.io/@comfytime/posts)          |
|                           유저 프로필 <br> 본인, 타인, 전체 조회 <br> 게시물 <br> 단건, 전체 조회                           |                           유저 프로필 수정 <br> 게시물 생성 <br> 댓글 생성, 댓글 수정 <br> 댓글 조회, 댓글 삭제                           |                           회원가입 <br> 비밀번호 업데이트 <br> 댓글 좋아요/좋아요 취소 <br> 공통 예외 처리                           |                           로그인/로그아웃 <br> 유저 삭제 <br> 팔로우/언팔로우 <br> 게시글 좋아요/좋아요 취소                           |                           게시물 수정 <br> 게시물 삭제                           |


<br><br>

# ⚙️ 구현 내용

<details>
	<summary>필수 구현사항</summary>


* Lv.1 (프로필 관리)
  * 프로필 조회 기능(본인/타인)
  * 프로필 수정 기능(프로필/비밀번호)

* Lv.2 (뉴스피드 게시물)
  * 게시물 CRUD

* Lv.3 (사용자 인증)
  * 회원 가입/탈퇴
  * 로그인/로그아웃

* Lv.4 (팔로우)
  * 팔로우 / 언팔로우
  * 팔로우한 유저 조회
 
<br><br>

</details>


<details>
	<summary>선택 구현사항</summary>

* Lv.5 (업그레이드 뉴스피드)(구현)
  * 정렬기능(수정일자 / 등록일자 / 좋아요 기준)
  * 검색기능 (기간 검색)

* Lv.6 (댓글)(구현)
  * 댓글 CRUD
  
* Lv.7 (좋아요)(구현)
  * 게시물 좋아요 / 좋아요 취소
  * 댓글 좋아요 / 좋아요 취소

</details>


<br><br>

# 💡 프로젝트 목적
* Spring Framework의 전반적인 개발 프로세스 이해
* 요구사항 분석을 통해 기획자 또는 발주자의 의도 파악
* 깃허브를 이용하여 팀원간 협업 개발과 버전 관리 이해
* 팀원들과 코드 리뷰를 통한 Pull Request, Merge 이해와 브랜치를 통한 개발 흐름 이해


<br><br>

# 🔧 부족하거나 아쉬운 점, 공부하고 싶은 내용
* 친구 관리하기
* JWT로 로그인 구현하기
* 이미지 업로드 및 DB저장


