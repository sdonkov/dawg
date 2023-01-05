package com.github.sdonkov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class PrefixTreeTest {

    private static List<CharSequence> words;
    private static PrefixTree prefixTree;

    @BeforeAll
    static void setUp() throws IOException {
        words = new ArrayList<>();
        prefixTree = new PrefixTree();
        try (InputStream is = PrefixTreeTest.class.getResourceAsStream("/tests.txt")) {
            if (is != null) {
                Scanner sc = new Scanner(is);
                while (sc.hasNextLine()) {
                    CharSequence currentWord = sc.nextLine();
                    words.add(currentWord);
                    prefixTree.add(currentWord);
                }
            }
        }
    }

    @Test
    void testAddingWords() {
        words.forEach(s -> assertTrue(prefixTree.contains(s), (String) s));
    }

    @Test
    void testContainsNotAddedWords() throws IOException {
        try (InputStream is = PrefixTreeTest.class.getResourceAsStream("/negative_test.txt")) {
            if (is != null) {
                Scanner sc = new Scanner(is);
                while (sc.hasNextLine()) {
                    String currentWord = sc.nextLine();
                    assertFalse(prefixTree.contains(currentWord), currentWord);
                }
            }
        }
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