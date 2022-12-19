import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class PrefixTreeTest {

    @Test
    void addingWordsToTree() {
        PrefixTree prefixTree = new PrefixTree();
        File file = new File("tests.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                prefixTree.add(currentWord);
            }
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                assertTrue(prefixTree.contains(currentWord));
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void negativeTestPrefixTree() {
        PrefixTree prefixTree = new PrefixTree();
        File file = new File("tests.txt");
        File fileNegative = new File("negative_test.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                prefixTree.add(currentWord);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Scanner sc = new Scanner(fileNegative)) {
            while (sc.hasNextLine()) {
                String currentWord = sc.nextLine();
                assertTrue(prefixTree.contains(currentWord));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
