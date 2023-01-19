package com.github.sdonkov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jol.info.GraphLayout;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 1)
@State(Scope.Benchmark)
public class PrefixTreeTest {

    private static List<String> words;
    private static PrefixTree prefixTree;
    private final static Logger LOGGER = LogManager.getLogger(PrefixTree.class);

    @BeforeAll
    static void setUp() throws IOException, URISyntaxException {
        prefixTree = new PrefixTree();
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        words.forEach(word -> prefixTree.add(word));
    }

    @Test
    public void launchBenchmark() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PrefixTreeTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void benchmarkPrefixTree() throws URISyntaxException, IOException {
        prefixTree = new PrefixTree();
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        words.forEach(word -> prefixTree.add(word));
        LOGGER.trace(GraphLayout.parseInstance(prefixTree).totalSize() + " prefixTree");
    }

    @Benchmark
    public void benchmarkHashSet() throws URISyntaxException, IOException {
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        Set<String> hashSet = new HashSet<>(words);
        LOGGER.trace(GraphLayout.parseInstance(hashSet).totalSize() + " hashSet");

    }


    @Benchmark
    public void benchmarkTreeSet() throws URISyntaxException, IOException {
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/dictionary.txt").toURI()));
        Set<String> treeSet = new TreeSet<>(words);
        LOGGER.trace(GraphLayout.parseInstance(treeSet).totalSize() + " treeSet");
    }

    @Test
    void testAddingWords() {
        words.forEach(s -> assertTrue(prefixTree.contains(s), s));
    }

    @Test
    void testContainsNotAddedWords() throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines
                (Paths.get(PrefixTreeTest.class.getResource("/negative_test.txt").toURI()));
        lines.forEach(word -> assertFalse(prefixTree.contains(word), word));
    }

    @Test
    void testContainsNull() {
        PrefixTree underTest = new PrefixTree();
        assertThrows(NullPointerException.class, () -> underTest.contains(null));
    }

    @Test
    void testAddNull() {
        PrefixTree underTest = new PrefixTree();
        assertThrows(NullPointerException.class, () -> underTest.add(null));
    }

    @Test
    void testAddEmptyString() {
        PrefixTree underTest = new PrefixTree();
        //todo assert false
        assertTrue(underTest.add(""));
    }

    @Test
    void testAddAlreadyAddedWord() {
        PrefixTree underTest = new PrefixTree();
        underTest.add("table");
        assertFalse(underTest.add("table"));
    }

    @Test
    void testContainsEmptyString() {
        PrefixTree underTest = new PrefixTree();
        assertFalse(underTest.contains(""));
    }
}