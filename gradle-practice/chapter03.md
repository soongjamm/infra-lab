## Chapter03 : 그레이들 기본

### 그레이들의 태스크-1

- 빌드를 수행하는 가장 기본, 중심이 되는 빌드 수행의 시작점

<< ( doLast 로 바뀐) 이 붙은것과 안붙은 것의 차이

- 실행단계에서 task 로 인식할 것 이라는 걸 표시함
- 두 개의 테스트를 동시에 실행하면
<< 가 붙은 것이 나중에 실행된다.
    - 안붙은 것은 설정단계에서 인식, 붙은 것은 실행단계에서 인식/수행 되기 때문

태스크의 기본

- 태스크 객체를 있는 속성을 이용해 지정가능

```groovy
task goodTask<<{
	println description // description 은 기본 속성임
}
goodTask.description = "something"
// something 출력
```

- 이렇게 쓰려면 task 가 먼저 선언되어야 함.
    - task 객체가 존재해야 속성을 이용하는게 당연하니까
    

### 그레이들의 태스크-2

- 그루비 기반이므로 그루비의 다양한 기능 사용 가능(메소드등)
    - {String}.toUpperCase()  (, lowerCase)
    - {Int}.times
        
        ```groovy
        10.times { println "$it" }
        
        3.times {counter ->
        				task "exeTask$counter"<<{
        						println "task counter: $counter"
        				}
        }
        exeTask1.dependsOn exeTask0,exeTask2
        // 1만 실행되도 0과 2가 실행된다. (dependsOn)
        ```
        
    
    doFirst
    
    - 지정된 테스크 실행 전에 실행
    
    doLast
    
    - 지정된 테스크 실행 완료 후 실행
    - task 에서 정의된 객체에 접근 가능. (다음 시간에)
    
    defaultTasks
    
    - 빌드 스크립트 수행시, 기본적으로 수행해야하는 테스크를 지정하는 키워드
    
    맵 형태
    
    ```groovy
    def confMap = [’imgConf’:’img.something’ ...]
    confMap.each { svdomain, domainAddr →
        task “exeTask${svDomain”<<{println domainAddr}
    }
    ```
    

### 3.조건에 따른 빌드

```groovy
{taskName} .onlyIf { buildType == ‘partial-build’ }
```

예외처리

```groovy
task exeTask<<{println 'exeTask Build SUCCESS}

exeTask << {
	if (process=='error'){ // process 는 입력값
		throw new StoopExecutionException()
	}
}
// error 면 아래가 출력되지 않는다.
exeTask<<{ println '-- Build END --' }
```

### 4.실행순서 제어

테스트 실행순서 제어 및 강제성 제어

- mustRunAfter
    
    ```groovy
    // 다음과 같이 사용
    {exeTaskAfter}.mustRunAfter {exeTaskBefore}
    ```
    
    - 순환참조 구조면 예외발생
- shouldRunAfter
    - mustRunAfter 처럼 테스크 실행 순서를 제어한다.
    - 차이는? 순환참조 구조 무시.
    - 의존관계가 실행되지 않더라도, shouldRun 순서따름

의존관계와 순서지정 방법 간의 차이

- must, shouldRun 은 지정된 테스크만 순서가 지정되어서 빌드 수행 대상에 올라간다.
    - 순서가 지정되었어도 의존관계가 없고 테스크 실행을 지정하지 않았으면 수행 대상으로 올라가지 않음

### 그레이들의 태스크 그래프

- 방향성 비순환 그래프 (Directed Acyclic Graph)
    - 만약 순환구조를 가지면 무한루프에 빠진다.
- 태스크간의 의존관계를 시작적으로 표현

```groovy
task FirstExeTask << { println 'First' }
task SecondExeTask(dependsOn: 'FirstExeTask') << { println 'First' }
task ThiredExeTask(dependsOn: 'SecondExeTask') << { println 'First' }

// gradle ThirExeTask <- 실행.
// 3은 2에 의존. 2를 실행하려 함.
// 2는 1에 의존. 1을 실행하려 함.
// 1이 수행
```

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8cb22838-ddd1-49df-93af-234f1410d6be/Untitled.png)

 

종료 테스크

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c73a7b07-9a6b-42d8-ab70-b787d58ce14d/Untitled.png)

- FirstExeTask 끝나면 종료 테스크로 지정된 finishTask 실행하도록 지정.
- 예외가 발생했음에도 불구하고 finishTask 는 실행.
    - 그러나 만약 세컨드에다가 달아놨으면, 실행안됌

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0bb95265-99ab-4e9a-b54d-4bd0ce7bdf87/Untitled.png)
