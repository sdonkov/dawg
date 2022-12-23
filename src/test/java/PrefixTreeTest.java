import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class PrefixTreeTest {

    @Test
    void addingWordsToTree() throws FileNotFoundException {
        PrefixTree prefixTree = new PrefixTree();
        File file = new File("src/test/resources/tests.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                prefixTree.add(currentWord);
            }
        }
        try(Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                String currentWord = sc.nextLine();
                assertTrue(prefixTree.contains(currentWord));
            }
        }
    }

    @Test
    void negativeTestPrefixTree() throws FileNotFoundException{
        PrefixTree prefixTree = new PrefixTree();
        File file = new File("src/test/resources/tests.txt");
        File fileNegative = new File("src/test/resources/negative_test.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                prefixTree.add(currentWord);
            }
        }
        try (Scanner sc = new Scanner(fileNegative)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                assertFalse(prefixTree.contains(currentWord));
            }
        }
    }
}