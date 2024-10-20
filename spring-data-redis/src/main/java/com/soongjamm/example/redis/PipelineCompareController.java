package com.soongjamm.example.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PipelineCompareController {

    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/redis/compare-pipeline-and-multiple-get")
    public String hello(
            @RequestParam(defaultValue = "100") final Integer repeatCount,
            @RequestParam(defaultValue = "30") final Integer cmdExecCount
    ) {
        long avgByPipeline = howLongDoesItTakes(
                () -> {
                    redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                        for (int cur = 0; cur < cmdExecCount; cur++) {
                            connection.set(("key" + cur).getBytes(), ("value" + cur).getBytes());
                        }
                        return null;
                    });
                }, repeatCount
        );
        long avgByMultipleGet = howLongDoesItTakes(
                () -> {
                    for (int cur = 0; cur < cmdExecCount; cur++) {
                        redisTemplate.opsForValue().get("test");
                    }
                }, repeatCount
        );
        return String.format("%s개의 명령 실행을 %s회 반복한 평균 소요 시간</br>pipeline - %s ms</br>multiple get - %s ms", avgByPipeline, avgByMultipleGet);
    }

    // return avg(time taken milliseconds)
    private long howLongDoesItTakes(Runnable command, int repeatCount) {
        long timeTaken = 0;

        for (int i = 0; i < repeatCount; i++) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            command.run();

            stopWatch.stop();
            timeTaken += stopWatch.getTotalTimeMillis();
        }

        return timeTaken / repeatCount;
    }

}
