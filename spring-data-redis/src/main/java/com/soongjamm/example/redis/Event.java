package com.soongjamm.example.redis;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo( // 추상클래스를 구현체로 JSON serialization/deserialization 하기 위한 설정
        use = JsonTypeInfo.Id.NAME, // 하위 타입 정보의 식별자. (serialize 할 때, 하위 타입 정보로 무엇을 넣을지? NAME = SimpleClassName을 넣는다.)
        property = "type" // type을 식별하기 위한 필드명. (serialize 할 때, json 'type' 필드에 타입을 저장한다.)
)
@JsonSubTypes({ // @JsonTypeInfo 와 사용된다.(단독으로는 X) 구현체를 명시하기 위해 사용된다.
        @JsonSubTypes.Type(value = CouponCreatedEvent.class),
        @JsonSubTypes.Type(value = CouponUsedEvent.class)
})
public abstract class Event {
}
