package com.soongjamm.techlab.schedule;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class Scheduler {

    private final AtomicInteger testGauge;
    private final MultiGauge multiGauge;
    private final Counter testCounter;

    public Scheduler(MeterRegistry meterRegistry) {
        this.testGauge = meterRegistry.gauge("custom_gauge", new AtomicInteger(0));
        this.multiGauge = MultiGauge.builder("custom_multi_gauge")
                .description("Custom Multi Gauge Description")
                .register(meterRegistry);
        this.testCounter = meterRegistry.counter("custom_counter");
    }

    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    public void schedulingTask() {
        log.info("scheduling works");

        multiGauge.register(
                List.of(
                        MultiGauge.Row.of(
                                Tags.of(
                                        "status", "SUCCESS"),
                                getRandomNumberInRange(1000, 1500)),
                        MultiGauge.Row.of(
                                Tags.of(
                                        "status", "FAILURE"),
                                getRandomNumberInRange(0, 11))
                )
        , true);

        testGauge.set(Scheduler.getRandomNumberInRange(0, 100));

        testCounter.increment();
    }

    static Random r = new Random();

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return r.nextInt((max - min) + 1) + min;
    }
}
