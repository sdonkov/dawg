import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class PrefixTreeTest {

    private static List<CharSequence> words;
    private static PrefixTree prefixTree;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        File file = new File("src/test/resources/tests.txt");
        words = new ArrayList<>();
        prefixTree = new PrefixTree();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                CharSequence currentWord = sc.nextLine();
                words.add(currentWord);
                prefixTree.add(currentWord);
            }
        }
    }

    @Test
    void addingWordsToTree() {
        words.forEach(s -> assertTrue(prefixTree.contains(s)));
    }

    @Test
    void negativeTestPrefixTree() throws FileNotFoundException {
        File fileNegative = new File("src/test/resources/negative_test.txt");
        try (Scanner sc = new Scanner(fileNegative)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                assertFalse(prefixTree.contains(currentWord));
            }
        }
    }
}