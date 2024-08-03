package com.soongjamm.example.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponUsedEvent extends Event {

    private Long id;
    private OffsetDateTime useAt;

}
