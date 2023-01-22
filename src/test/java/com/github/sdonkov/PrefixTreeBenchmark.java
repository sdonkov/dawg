package com.github.sdonkov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jol.info.GraphLayout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
@Warmup(iterations = 3)
@State(Scope.Benchmark)
public class PrefixTreeBenchmark {
    private static List<String> words;
    private static PrefixTree prefixTree;
    private static List<String> randomWords;


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PrefixTreeBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setUp() throws URISyntaxException, IOException {
        prefixTree = new PrefixTree();
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        words.forEach(word -> prefixTree.add(word));
        Collections.shuffle(words);
        randomWords = words.subList(0, 11);
    }

    @Benchmark
    public void benchmarkPrefixTree() {
        randomWords.forEach(word -> prefixTree.contains(word));
    }

    @Benchmark
    public void benchmarkHashSet() {
        Set<String> hashSet = new HashSet<>(words);
        randomWords.forEach(word -> hashSet.contains(word));
    }

    @Benchmark
    public void benchmarkTreeSet() {
        Set<String> treeSet = new TreeSet<>(words);
        randomWords.forEach(word -> treeSet.contains(word));
    }
}
