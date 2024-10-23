package com.soongjamm.timereminder;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimeReminder {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final int time;
    private final TimeUnit unit;

    public TimeReminder(int time, TimeUnit unit) {
        this.time = time;
        this.unit = unit;
    }

    public void remind() {
        Runnable task = () -> {
            try {
                log.info("{}", LocalDateTime.now());
            } finally {
                remind();
            }
        };
        executor.schedule(task, time, unit);
    }

    public void stop() {
        try {
            executor.shutdown();
            boolean terminated = executor.awaitTermination(time, unit);
            if (terminated) {
                log.info("terminated.");
            } else {
                throw new IllegalStateException();
            }
        } catch (IllegalStateException | InterruptedException e) {
            executor.shutdownNow();
            log.warn("not terminated. force shutdown now.");
            throw new RuntimeException(e);
        }
    }
}
