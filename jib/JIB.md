# 숭잼의 Tech-Lab
이것 저것 기술을 연습해보는 공간
- gradle 사용중
  
## [jib](https://github.com/GoogleContainerTools/jib)
도커 빌드를 하려면 보통은 다음과 같은 과정을 거친다.
- maven나 gradle 등을 이용해 jar 빌드를 한다.
- `Dockerfile`을 이용해 `docker build -t myproject .` 같은 명령어로 도커 이미지를 빌드한다.
- [선택] 도커 허브에 push를 한다.  

jib을 이용하면 위 과정을 1번의 작업으로 줄여준다.
- `./gradlew jib` 으로 바로 도커 이미지 빌드하고 도커 허브에 push한다.

**느낀점**  
도커 이미지 빌드를 뭐 밥먹듯이 하는 것도 아니고, 이게 뭐가 그리 좋냐. 생각할 수 있지만, 실제로 개발을 하다보며 느낀게 별거 아닌 과정이라도 생략되면 상당히 편하더라..   
- elastic apm 설정이나, 분산 처리 환경을 만들어 실험해볼 때 코드를 고쳐가며 계속 빌드할 일이 있었는데 jib을 이용하니 상당히 편리했다.  
- `Dockerfile` 을 작성하지 않아도 되고, 도커 명령어를 사용하지 않아도 되서 좋았는데, 실제로 jib의 readme에 나와있는 장점 중 하나이다. (Daemonless, cli 의존도를 낮춤) 

### 사용법
-` gradle.build` 에 plugin 추가  
```
plugins {
    ...    
    id 'com.google.cloud.tools.jib' version '3.1.2'
}
```
- 기타 설정 추가 (아래는 base image )
```
jib {
    from {
        image = "adoptopenjdk/openjdk11:alpine" // base image
    }
    to {
        image = "kimstz0/hello-jib" // 도커 허브 레포지토리
        tags = ["latest"]
    }
    container {
        creationTime = "USE_CURRENT_TIMESTAMP" // 설정 안하면 50년 전으로 나옴
    }
}
```

- 빌드 (`./gradlew jib`)