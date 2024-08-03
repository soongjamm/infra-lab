package com.soongjamm.example.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponCreatedEvent extends Event {

    private Long id;
    private String title;
    private String createBy;

}
