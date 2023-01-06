package com.github.sdonkov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PrefixTreeTest {

    private static List<String> words;
    private static PrefixTree prefixTree;

    @BeforeAll
    static void setUp() throws IOException, URISyntaxException {
        prefixTree = new PrefixTree();
        words = Files.readAllLines(Paths.get(PrefixTreeTest.class.getResource("/tests.txt").toURI()));
        words.forEach(word -> prefixTree.add(word));
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