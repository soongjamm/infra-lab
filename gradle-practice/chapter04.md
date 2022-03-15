
## Chapter04 : 그레이들 객체
### Project 객체

기본 도메인 객체중 하나.

- 프로젝트의 환경 구성, 의존관계, 테스크등의 내용을 제어 및 참조
- build.gradle 과 1:1로 대응
- 생명주기
    - 빌드 수행을 위한 Settings 객체 생성
    - settings.gradle 스크립트 파일이 있을 경우 Settings 객체 비교
    - Setting  객체를 이용해서 Project 객체를 생성함
    - 멀티 프로젝트면 부모 → 자식 프로젝트 순으로 Project 객체 생성
        - 즉 Settings 1 : Project n 이 되는데, Setting가 기반으로 Project  객체 생성
        

프로젝트 객체 구조

- 여섯가지 요소
- TaskContainer
    - .create() 로 단위테스트 실행, 와르압축 등의 작업 수행
    - getByName() 메소드로 테스크 정보를 얻을 수 있음
    - 프로젝트 정보를 참조해서 기능을 제공함
- ConfigurationContainer
    - 프로젝트의 구성을 관리할 수 있도록 기능 제공
- DependencyHandler
    - 의존관계를 관리
- ArtifactHandler
    - 프로젝트의 결과물을 관리하는 기능 제공
- RepositoryHandler
    - 프로젝트의 저장공간을 관리

→ 프로젝트 객체중심으로 구성요소들의 기능을 프로젝트객체가 위임받거나 생성해서 사용하고 기능 제공.

프로젝트 객체의 속성

- version
- description
- name
- state : 프로젝트 빌드 상태
- status : 프로젝트 결과물의 상태
- path : 구분자 ‘:’ 로 프로젝트 경로 표시

프로젝트 객체의 API

- project(path) : 지정된 경로의 프로젝트에 대한 설정
- project(path, configureClosure) :
- absoluteProject : 절대 경로 반환하여 프로젝트 확인
- apply(closure) : 플러그인이나 스크립트 적용
- configure(object, configureClosure)
- subprojects(actions)
- task(name)
- afterEvaluate(action)
- beforeEvaluate(action)

```groovy
// 아래 스크립트를 build.gradle 에 넣으면 실행이 안된다.
// gradle 생애주기를 보면 프로젝트 설정 단계에서 평가가 이루어 진다. (Settings, settings.gradle)
// build.gradle (Proejct) 는 설정을 지나친 상태.
// 즉, settings.gradle 에 넣어줘야 실행 됌
gradle.allprojects {
	project ->
		project.beforeEvaluate {
			println project.name + ' : check start '
		}
		project.afterEvaluate {
			println project.name + ' : check end '
		}
}
```