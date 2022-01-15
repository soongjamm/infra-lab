package com.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(value = Scope.Benchmark)
public class StreamPerformance {

    public List<Integer> ints;

    @Setup(Level.Invocation)
    public void setUp() {
        this.ints = new ArrayList<>();
        for (int i = 0; i < 500_000; i++) {
            ints.add(i);
        }
    }

    @Benchmark
    public void use_stream() {
        ints.stream()
                .filter(x -> x % 2 == 0)
                .map(this::calculate)
                .collect(Collectors.toList());
    }

    @Benchmark
    public void use_outer_iteration() {
        List<Integer> box = new ArrayList<>();
        for (Integer each : ints) {
            if (each % 2 == 0) { // filter
                int calculated = this.calculate(each); // map
                box.add(calculated); // collect
            }
        }
    }

    public int calculate(int input) {
        int result = 0;
        for (int i = 0; i < 100_000; i++) {
            result = Math.addExact(input, i);
        }
        return result;
    }
}
