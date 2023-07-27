# 정광철-서버-jgc1619@naver.com

## 설계 내용
- Redis를 사용한 이유는 랭킹 조회라는 성격과 빈번하게 조회가 이루어지는 API임을 고려하여서 조회시에 
성능을 위해 Redis를 고려하게 되었습니다. 
- Redis를 사용할 경우 정합성 문제가 발생할 수 있는데 이를 위해서 RDB에도 백업 데이터를 저장하고 Redis에 저장
하는 방식을 고려하였습니다.
- Redis의 사용시 메모리 문제에 대해서는 만료일(expire)설정을 통해서 랭킹을 위한 1일 데이터가 1일 이후에 삭제되어서
메모리에 부하를 줄이도록 하였습니다.
- 거래되어지는 정보에 대해서는 휘발되어지면 안되는 데이터기 때문에 RDB에 거래정보 저장 테이블을 생성하여서 관리하도록 하였습니다.
### 저장 처리
<img width="728" alt="스크린샷 2023-07-27 오후 5 32 13" src="https://github.com/kakaopayseccoding-server/202307-jgc1619-naver.com/assets/37210747/79074652-424c-4a8c-a684-0d45bdc847bc">



## 조회 처리
<img width="703" alt="스크린샷 2023-07-27 오후 5 32 22" src="https://github.com/kakaopayseccoding-server/202307-jgc1619-naver.com/assets/37210747/79bc3afc-5f5f-43f6-aa5a-4a2aa992fc5d">

## 실행방법
1. jar file 생성
```
./gradlew bootJar
```
2. docker compose 실행
```
$ docker-compose up --build -d 
```
터미널에 입력하여 실행합니다.

2. swagger 접속
- http://localhost:8080/swagger-ui/index.html#/

