package com.github.sdonkov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jol.info.GraphLayout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
@Warmup(iterations = 3)
@State(Scope.Benchmark)
public class DataStructureBenchmark {
    private static List<String> words;
    private static PrefixTree prefixTree;
    private static Set<String> treeSet;
    private static Set<String> hashSet;
    private final static Logger LOGGER = LogManager.getLogger(DataStructureBenchmark.class);

    @Setup
    public void setUp() throws URISyntaxException, IOException {
        prefixTree = new PrefixTree();
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        words.forEach(word -> prefixTree.add(word));
        treeSet = new TreeSet<>(words);
        hashSet = new HashSet<>(words);
        sizeInfo(prefixTree);
        sizeInfo(treeSet);
        sizeInfo(hashSet);
        Collections.shuffle(words);
    }

    private void sizeInfo(Object object) {
        LOGGER.debug(GraphLayout.parseInstance(object).totalSize() + " " + object.getClass().getSimpleName());
    }

    @org.openjdk.jmh.annotations.Benchmark
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
