package com.github.sdonkov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
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
    private static Set<String> treeSet;
    private static Set<String> hashSet;
    private final static Logger LOGGER = LogManager.getLogger(PrefixTree.class);

    @Setup
    public void setUp() throws URISyntaxException, IOException {
        prefixTree = new PrefixTree();
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        words.forEach(word -> prefixTree.add(word));
        Collections.shuffle(words);
        treeSet = new TreeSet<>(words);
        hashSet = new HashSet<>(words);
        LOGGER.debug(GraphLayout.parseInstance(prefixTree).totalSize() + " tree");
//        LOGGER.debug(GraphLayout.parseInstance(hashSet).totalSize() + " hashSet");
//        5377400 hashSet
//        LOGGER.debug(GraphLayout.parseInstance(treeSet).totalSize() + " treeSet");
//        5317968 treeSet
    }

    @Benchmark
    public void benchmarkPrefixTree(Blackhole bh) {
        words.forEach(word -> bh.consume(prefixTree.contains(word)));
    }

    //    @Benchmark
    public void benchmarkHashSet(Blackhole bh) {
        words.forEach(word -> bh.consume(hashSet.contains(word)));
    }

    //    @Benchmark
    public void benchmarkTreeSet(Blackhole bh) {
        words.forEach(word -> bh.consume(treeSet.contains(word)));
    }
}
